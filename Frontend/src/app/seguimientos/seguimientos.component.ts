import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { forkJoin, BehaviorSubject, switchMap } from 'rxjs';
import { ApiService } from '../services/api.service';
import { NgxPaginationModule } from 'ngx-pagination';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import { Document, Packer, Paragraph, Table, TableRow, TableCell } from 'docx';

@Component({
  selector: 'app-seguimientos',
  standalone: true,
  imports: [FormsModule, CommonModule, NgxPaginationModule],
  templateUrl: './seguimientos.component.html',
  styleUrls: ['./seguimientos.component.css']
})
export class SeguimientosComponent implements OnInit {
  dropdownOpen = false;

  toggleDropdown(event: MouseEvent) {
    event.stopPropagation();
    this.dropdownOpen = !this.dropdownOpen;
  }

  ngOnInit() {
    document.addEventListener('click', () => {
      this.dropdownOpen = false;
    });
  }

  private refrescar$ = new BehaviorSubject<void>(undefined);

  data$ = this.refrescar$.pipe(
    switchMap(() =>
      forkJoin({
        seguimientos: this.api.getSeguimientos(),
        mercancias: this.api.getMercancias()
      })
    )
  );

  seguimientoForm: any = this.getEmptyForm();
  editando = false;
  editId: number | null = null;
  modalVisible = false;
  seguimientoConsultado: any = null;

  filtroRemesa = '';
  filtroEstado = '';
  page = 1;
  pageSize = 8;

  alertaVisible = false;
  alertaMensaje = '';
  alertaTipo: 'exito' | 'error' = 'exito';

  constructor(private api: ApiService) {}

  abrirModal(s?: any) {
    if (s) {
      this.editando = true;
      this.editId = s.id;
      this.seguimientoForm = { ...s, mercancias_id: s.mercancias_id };
    } else {
      this.resetForm();
    }
    this.modalVisible = true;
  }

  cerrarModal() {
    this.modalVisible = false;
    this.resetForm();
  }

  guardarSeguimiento() {
    if (!this.seguimientoForm.mercancias_id || !this.seguimientoForm.estado) {
      return this.mostrarAlerta('⚠️ Complete todos los campos obligatorios.', 'error');
    }

    const request = this.editando && this.editId
      ? this.api.actualizarSeguimiento(this.editId, this.seguimientoForm)
      : this.api.crearSeguimiento(this.seguimientoForm);

    request.subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta(`✅ Seguimiento ${this.editando ? 'actualizado' : 'registrado'} con éxito.`, 'exito');
      },
      error: err => {
        if (err.error?.mercancias_id) {
          this.mostrarAlerta('❌ La mercancía seleccionada no existe.', 'error');
        } else {
          this.mostrarAlerta('❌ Error al guardar seguimiento.', 'error');
        }
      }
    });
  }

  eliminarSeguimiento(id: number) {
    if (!confirm('¿Deseas eliminar este seguimiento?')) return;
    this.api.eliminarSeguimiento(id).subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta('🗑️ Seguimiento eliminado con éxito.', 'exito');
      },
      error: () => this.mostrarAlerta('❌ Error al eliminar seguimiento.', 'error')
    });
  }

  consultarSeguimiento(id: number) {
    this.api.getSeguimiento(id).subscribe({
      next: res => this.seguimientoConsultado = res,
      error: () => this.mostrarAlerta('❌ Error al consultar seguimiento.', 'error')
    });
  }

  cerrarConsulta() {
    this.seguimientoConsultado = null;
  }

  private resetForm() {
    this.seguimientoForm = this.getEmptyForm();
    this.editando = false;
    this.editId = null;
  }

  private getEmptyForm() {
    return {
      mercancias_id: '',
      estado: ''
    };
  }

  private refrescarDatos() {
    this.refrescar$.next();
    this.cerrarModal();
  }

  mostrarAlerta(mensaje: string, tipo: 'exito' | 'error') {
    this.alertaMensaje = mensaje;
    this.alertaTipo = tipo;
    this.alertaVisible = true;
    setTimeout(() => this.alertaVisible = false, 4000);
  }

  cerrarAlerta() {
    this.alertaVisible = false;
  }

  seguimientosFiltrados(seguimientos: any[]) {
    return seguimientos.filter(s =>
      (!this.filtroRemesa || s.mercancia?.numero_remesa?.includes(this.filtroRemesa)) &&
      (!this.filtroEstado || s.estado.includes(this.filtroEstado))
    );
  }

  // ------------------------------- EXPORTAR
  exportarExcel(seguimientos: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(seguimientos);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Seguimientos');
    XLSX.writeFile(workbook, 'seguimientos.xlsx');
  }

  exportarCSV(seguimientos: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(seguimientos);
    const csv = XLSX.utils.sheet_to_csv(worksheet);
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    saveAs(blob, 'seguimientos.csv');
  }

  exportarWord(seguimientos: any[]) {
    const rows = seguimientos.map(s =>
      new TableRow({
        children: [
          new TableCell({ children: [new Paragraph(s.id.toString())] }),
          new TableCell({ children: [new Paragraph(s.mercancia?.numero_remesa ?? '')] }),
          new TableCell({ children: [new Paragraph(s.estado)] }),
        ],
      })
    );

    const doc = new Document({
      sections: [
        {
          children: [
            new Paragraph("📡 Reporte de Seguimientos"),
            new Table({
              rows: [
                new TableRow({
                  children: [
                    new TableCell({ children: [new Paragraph("ID")] }),
                    new TableCell({ children: [new Paragraph("Remesa")] }),
                    new TableCell({ children: [new Paragraph("Estado")] }),
                  ],
                }),
                ...rows,
              ],
            }),
          ],
        },
      ],
    });

    Packer.toBlob(doc).then(blob => saveAs(blob, 'seguimientos.docx'));
  }

  async exportarPDF(seguimientos: any[]) {
    const pdfMakeModule = await import('pdfmake/build/pdfmake');
    const pdfFonts = await import('pdfmake/build/vfs_fonts');
    (pdfMakeModule as any).vfs = (pdfFonts as any).vfs;

    const body = [
      ["ID", "Remesa", "Estado"],
      ...seguimientos.map(s => [s.id, s.mercancia?.numero_remesa, s.estado]),
    ];

    const docDefinition = {
      content: [
        { text: "📡 Reporte de Seguimientos", style: "header" },
        { table: { body } },
      ],
      styles: { header: { fontSize: 18, bold: true, margin: [0, 0, 0, 10] } },
    };

    (pdfMakeModule as any).createPdf(docDefinition).download("seguimientos.pdf");
  }
}
