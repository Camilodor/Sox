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
  selector: 'app-devoluciones',
  standalone: true,
  imports: [FormsModule, CommonModule, NgxPaginationModule],
  templateUrl: './devoluciones.component.html',
  styleUrls: ['./devoluciones.component.css']
})
export class DevolucionesComponent implements OnInit {
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
        devoluciones: this.api.getDevoluciones(),
        despachos: this.api.getDespachos(),
        mercancias: this.api.getMercancias()
      })
    )
  );

  devolucionForm: any = this.getEmptyForm();
  editando = false;
  editId: number | null = null;
  modalVisible = false;
  devolucionConsultada: any = null;

  filtroRemesa = '';
  filtroEstado = '';
  page = 1;
  pageSize = 8;

  alertaVisible = false;
  alertaMensaje = '';
  alertaTipo: 'exito' | 'error' = 'exito';

  constructor(private api: ApiService) {}

  abrirModal(d?: any) {
    if (d) {
      this.editando = true;
      this.editId = d.id;
      this.devolucionForm = { ...d, numero_remesa: d.mercancias?.numero_remesa };
    } else {
      this.resetForm();
    }
    this.modalVisible = true;
  }

  cerrarModal() {
    this.modalVisible = false;
    this.resetForm();
  }

  guardarDevolucion() {
    if (!this.devolucionForm.numero_remesa || !this.devolucionForm.despachos_id || !this.devolucionForm.motivo_devolucion) {
      return this.mostrarAlerta('⚠️ Complete todos los campos obligatorios.', 'error');
    }

    const request = this.editando && this.editId
      ? this.api.actualizarDevolucion(this.editId, this.devolucionForm)
      : this.api.crearDevolucion(this.devolucionForm);

    request.subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta(`✅ Devolución ${this.editando ? 'actualizada' : 'registrada'} con éxito.`, 'exito');
      },
      error: err => {
        if (err.error?.numero_remesa) {
          this.mostrarAlerta('❌ Número de remesa no existe en mercancías.', 'error');
        } else {
          this.mostrarAlerta('❌ Error al guardar devolución.', 'error');
        }
      }
    });
  }

  eliminarDevolucion(id: number) {
    if (!confirm('¿Deseas eliminar esta devolución?')) return;
    this.api.eliminarDevolucion(id).subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta('🗑️ Devolución eliminada con éxito.', 'exito');
      },
      error: () => this.mostrarAlerta('❌ Error al eliminar devolución.', 'error')
    });
  }

  consultarDevolucion(id: number) {
    this.api.getDevolucion(id).subscribe({
      next: res => this.devolucionConsultada = res,
      error: () => this.mostrarAlerta('❌ Error al consultar devolución.', 'error')
    });
  }

  cerrarConsulta() {
    this.devolucionConsultada = null;
  }

  private resetForm() {
    this.devolucionForm = this.getEmptyForm();
    this.editando = false;
    this.editId = null;
  }

  private getEmptyForm() {
    return {
      numero_remesa: '',
      despachos_id: '',
      fecha_devolucion: '',
      motivo_devolucion: '',
      estado_devolucion: 'Pendiente',
      observaciones: ''
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

  devolucionesFiltradas(devoluciones: any[]) {
    return devoluciones.filter(d =>
      (!this.filtroRemesa || d.mercancias?.numero_remesa.includes(this.filtroRemesa)) &&
      (!this.filtroEstado || d.estado_devolucion.includes(this.filtroEstado))
    );
  }

  // ------------------------------- EXPORTAR
  exportarExcel(devoluciones: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(devoluciones);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Devoluciones');
    XLSX.writeFile(workbook, 'devoluciones.xlsx');
  }

  exportarCSV(devoluciones: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(devoluciones);
    const csv = XLSX.utils.sheet_to_csv(worksheet);
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    saveAs(blob, 'devoluciones.csv');
  }

  exportarWord(devoluciones: any[]) {
    const rows = devoluciones.map(d =>
      new TableRow({
        children: [
          new TableCell({ children: [new Paragraph(d.id.toString())] }),
          new TableCell({ children: [new Paragraph(d.mercancias?.numero_remesa ?? '')] }),
          new TableCell({ children: [new Paragraph(d.motivo_devolucion)] }),
          new TableCell({ children: [new Paragraph(d.estado_devolucion)] }),
        ],
      })
    );

    const doc = new Document({
      sections: [
        {
          children: [
            new Paragraph("↩️ Reporte de Devoluciones"),
            new Table({
              rows: [
                new TableRow({
                  children: [
                    new TableCell({ children: [new Paragraph("ID")] }),
                    new TableCell({ children: [new Paragraph("Remesa")] }),
                    new TableCell({ children: [new Paragraph("Motivo")] }),
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

    Packer.toBlob(doc).then(blob => saveAs(blob, 'devoluciones.docx'));
  }

  async exportarPDF(devoluciones: any[]) {
    const pdfMakeModule = await import('pdfmake/build/pdfmake');
    const pdfFonts = await import('pdfmake/build/vfs_fonts');
    (pdfMakeModule as any).vfs = (pdfFonts as any).vfs;

    const body = [
      ["ID", "Remesa", "Motivo", "Estado"],
      ...devoluciones.map(d => [d.id, d.mercancias?.numero_remesa, d.motivo_devolucion, d.estado_devolucion]),
    ];

    const docDefinition = {
      content: [
        { text: "↩️ Reporte de Devoluciones", style: "header" },
        { table: { body } },
      ],
      styles: { header: { fontSize: 18, bold: true, margin: [0, 0, 0, 10] } },
    };

    (pdfMakeModule as any).createPdf(docDefinition).download("devoluciones.pdf");
  }
}
