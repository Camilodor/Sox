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
  selector: 'app-entregas',
  standalone: true,
  imports: [FormsModule, CommonModule, NgxPaginationModule],
  templateUrl: './entregas.component.html',
  styleUrls: ['./entregas.component.css']
})
export class EntregasComponent implements OnInit {
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
        entregas: this.api.getEntregas(),
        despachos: this.api.getDespachos(),
        mercancias: this.api.getMercancias()
      })
    )
  );

  // 📌 estados UI
  entregaForm: any = this.getEmptyForm();
  editando = false;
  editId: number | null = null;
  modalVisible = false;
  entregaConsultada: any = null;

  // filtros y paginación
  filtroRemesa = '';
  filtroEstado = '';
  page = 1;
  pageSize = 8;

  // alertas
  alertaVisible = false;
  alertaMensaje = '';
  alertaTipo: 'exito' | 'error' = 'exito';

  constructor(private api: ApiService) {}

  // -------------------------------
  // Modales
  // -------------------------------
  abrirModal(e?: any) {
    if (e) {
      this.editando = true;
      this.editId = e.id;
      this.entregaForm = { ...e, numero_remesa: e.mercancias?.numero_remesa };
    } else {
      this.resetForm();
    }
    this.modalVisible = true;
  }

  cerrarModal() {
    this.modalVisible = false;
    this.resetForm();
  }

  // -------------------------------
  // Guardar (crear/editar)
  // -------------------------------
  guardarEntrega() {
    if (!this.entregaForm.numero_remesa || !this.entregaForm.despachos_id || !this.entregaForm.nombre_recibe) {
      return this.mostrarAlerta('⚠️ Complete todos los campos obligatorios.', 'error');
    }

    const request = this.editando && this.editId
      ? this.api.actualizarEntrega(this.editId, this.entregaForm)
      : this.api.crearEntrega(this.entregaForm);

    request.subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta(`✅ Entrega ${this.editando ? 'actualizada' : 'registrada'} con éxito.`, 'exito');
      },
      error: err => {
        if (err.error?.numero_remesa) {
          this.mostrarAlerta('❌ Número de remesa no existe en mercancías.', 'error');
        } else {
          this.mostrarAlerta('❌ Error al guardar entrega.', 'error');
        }
      }
    });
  }

  // -------------------------------
  // Eliminar
  // -------------------------------
  eliminarEntrega(id: number) {
    if (!confirm('¿Deseas eliminar esta entrega?')) return;
    this.api.eliminarEntrega(id).subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta('🗑️ Entrega eliminada con éxito.', 'exito');
      },
      error: () => this.mostrarAlerta('❌ Error al eliminar entrega.', 'error')
    });
  }

  // -------------------------------
  // Consultar
  // -------------------------------
  consultarEntrega(id: number) {
    this.api.getEntrega(id).subscribe({
      next: res => this.entregaConsultada = res,
      error: () => this.mostrarAlerta('❌ Error al consultar entrega.', 'error')
    });
  }

  cerrarConsulta() {
    this.entregaConsultada = null;
  }

  // -------------------------------
  // Helpers
  // -------------------------------
  private resetForm() {
    this.entregaForm = this.getEmptyForm();
    this.editando = false;
    this.editId = null;
  }

  private getEmptyForm() {
    return {
      numero_remesa: '',
      despachos_id: '',
      nombre_recibe: '',
      numero_celular_recibe: '',
      fecha_entrega: '',
      estado_entrega: 'Pendiente',
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

  entregasFiltradas(entregas: any[]) {
    return entregas.filter(e =>
      (!this.filtroRemesa || e.mercancias?.numero_remesa.includes(this.filtroRemesa)) &&
      (!this.filtroEstado || e.estado_entrega.includes(this.filtroEstado))
    );
  }

  // -------------------------------
  // Exportar (igual a usuarios)
  // -------------------------------
  exportarExcel(entregas: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(entregas);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Entregas');
    XLSX.writeFile(workbook, 'entregas.xlsx');
  }

  exportarCSV(entregas: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(entregas);
    const csv = XLSX.utils.sheet_to_csv(worksheet);
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    saveAs(blob, 'entregas.csv');
  }

  exportarWord(entregas: any[]) {
    const rows = entregas.map(e =>
      new TableRow({
        children: [
          new TableCell({ children: [new Paragraph(e.id.toString())] }),
          new TableCell({ children: [new Paragraph(e.mercancias?.numero_remesa ?? '')] }),
          new TableCell({ children: [new Paragraph(e.nombre_recibe)] }),
          new TableCell({ children: [new Paragraph(e.estado_entrega)] }),
        ],
      })
    );

    const doc = new Document({
      sections: [
        {
          children: [
            new Paragraph("📦 Reporte de Entregas"),
            new Table({
              rows: [
                new TableRow({
                  children: [
                    new TableCell({ children: [new Paragraph("ID")] }),
                    new TableCell({ children: [new Paragraph("Remesa")] }),
                    new TableCell({ children: [new Paragraph("Recibe")] }),
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

    Packer.toBlob(doc).then(blob => saveAs(blob, 'entregas.docx'));
  }

  async exportarPDF(entregas: any[]) {
    const pdfMakeModule = await import('pdfmake/build/pdfmake');
    const pdfFonts = await import('pdfmake/build/vfs_fonts');
    (pdfMakeModule as any).vfs = (pdfFonts as any).vfs;

    const body = [
      ["ID", "Remesa", "Recibe", "Estado"],
      ...entregas.map(e => [e.id, e.mercancias?.numero_remesa, e.nombre_recibe, e.estado_entrega]),
    ];

    const docDefinition = {
      content: [
        { text: "📦 Reporte de Entregas", style: "header" },
        { table: { body } },
      ],
      styles: { header: { fontSize: 18, bold: true, margin: [0, 0, 0, 10] } },
    };

    (pdfMakeModule as any).createPdf(docDefinition).download("entregas.pdf");
  }
}
