import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { forkJoin, BehaviorSubject, switchMap } from 'rxjs';
import { ApiService } from '../services/api.service';
import { NgxPaginationModule } from 'ngx-pagination';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import { Document, Packer, Paragraph, Table, TableRow, TableCell } from 'docx';

@Component({
  selector: 'app-despachos',
  standalone: true,
  imports: [FormsModule, CommonModule, NgxPaginationModule],
  templateUrl: './despachos.component.html',
  styleUrls: ['./despachos.component.css']
})
export class DespachosComponent {
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

  // -------------------- EXPORTAR --------------------
  exportarWord(despachos: any[]) {
    const rows = despachos.map(d => new TableRow({
      children: [
        new TableCell({ children: [new Paragraph(d.id.toString())] }),
        new TableCell({ children: [new Paragraph(d.mercancias?.numero_remesa ?? '')] }),
        new TableCell({ children: [new Paragraph(d.vehiculos?.numero_placas ?? '')] }),
        new TableCell({ children: [new Paragraph(d.tipopago?.nombre ?? '')] }),
      ]
    }));

    const doc = new Document({
      sections: [{
        children: [
          new Paragraph("🚚 Reporte de Despachos"),
          new Table({
            rows: [
              new TableRow({
                children: [
                  new TableCell({ children: [new Paragraph("ID")] }),
                  new TableCell({ children: [new Paragraph("Remesa")] }),
                  new TableCell({ children: [new Paragraph("Placa Vehículo")] }),
                  new TableCell({ children: [new Paragraph("Tipo Pago")] }),
                ]
              }),
              ...rows
            ]
          })
        ]
      }]
    });

    Packer.toBlob(doc).then(blob => {
      saveAs(blob, 'despachos.docx');
    });
  }

  async exportarPDF(despachos: any[]) {
    const pdfMakeModule = await import('pdfmake/build/pdfmake');
    const pdfFonts = await import('pdfmake/build/vfs_fonts');
    (pdfMakeModule as any).vfs = (pdfFonts as any).vfs;

    const body = [
      ["ID", "Remesa", "Placa Vehículo", "Tipo Pago"],
      ...despachos.map(d => [
        d.id,
        d.mercancias?.numero_remesa,
        d.vehiculos?.numero_placas,
        d.tipopago?.nombre
      ])
    ];

    const docDefinition = {
      content: [
        { text: "🚚 Reporte de Despachos", style: "header" },
        { table: { body } }
      ],
      styles: {
        header: { fontSize: 18, bold: true, margin: [0, 0, 0, 10] }
      }
    };

    (pdfMakeModule as any).createPdf(docDefinition).download("despachos.pdf");
  }

  exportarExcel(despachos: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(
      despachos.map(d => ({
        ID: d.id,
        Remesa: d.mercancias?.numero_remesa,
        Placa: d.vehiculos?.numero_placas,
        TipoPago: d.tipopago?.nombre
      }))
    );
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Despachos');
    XLSX.writeFile(workbook, 'despachos.xlsx');
  }

  exportarCSV(despachos: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(
      despachos.map(d => ({
        ID: d.id,
        Remesa: d.mercancias?.numero_remesa,
        Placa: d.vehiculos?.numero_placas,
        TipoPago: d.tipopago?.nombre
      }))
    );
    const csv = XLSX.utils.sheet_to_csv(worksheet);
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    saveAs(blob, 'despachos.csv');
  }

  // -------------------- ESTADO --------------------
  private refrescar$ = new BehaviorSubject<void>(undefined);
  data$ = this.refrescar$.pipe(
    switchMap(() =>
      forkJoin({
        pagos: this.api.getTiposPago(),
        despachos: this.api.getDespachos()
      })
    )
  );

  cargando = false;
  despachoForm: any = this.getEmptyForm();
  editando = false;
  editId: number | null = null;
  filtroRemesa = '';
  filtroPlaca = '';
  modalVisible = false;
  despachoConsultado: any = null;

  // paginación
  page = 1;
  pageSize = 8;

  // alertas
  alertaVisible = false;
  alertaMensaje = '';
  alertaTipo: 'exito' | 'error' = 'exito';

  constructor(private api: ApiService) {}

  // -------------------- MODALES --------------------
  abrirModal(d?: any) {
    if (d) {
      this.editando = true;
      this.editId = d.id;
      this.despachoForm = {
        numero_remesa: d.mercancias?.numero_remesa ?? '',
        numero_placas: d.vehiculos?.numero_placas ?? '',
        tipo_pago_id: d.tipopago?.id ?? '',
        fecha_despacho: d.fecha_despacho,
        negociacion: d.negociacion,
        anticipo: d.anticipo,
        saldo: d.saldo,
        observaciones_mer: d.observaciones_mer ?? ''
      };
    } else {
      this.resetForm();
    }
    this.modalVisible = true;
  }

  cerrarModal() {
    this.modalVisible = false;
    this.resetForm();
  }

  // -------------------- CRUD --------------------
  guardarDespacho() {
    if (!this.despachoForm.numero_remesa || !this.despachoForm.numero_placas || !this.despachoForm.tipo_pago_id) {
      return this.mostrarAlerta('⚠️ Complete los campos obligatorios.', 'error');
    }

    const accion = this.editando ? 'editar' : 'crear';
    if (!confirm(`¿Está seguro de ${accion} este despacho?`)) return;

    const request = this.editando && this.editId
      ? this.api.actualizarDespacho(this.editId, this.despachoForm)
      : this.api.crearDespacho(this.despachoForm);

    request.subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta(`✅ Despacho ${this.editando ? 'actualizado' : 'registrado'} con éxito.`, 'exito');
      },
      error: (err) => {
        console.error(err);
        this.mostrarAlerta('❌ Información incorrecta, intente nuevamente.', 'error');
      }
    });
  }

  eliminarDespacho(id: number) {
    if (!confirm('¿Deseas eliminar este despacho?')) return;

    this.api.eliminarDespacho(id).subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta('🗑️ Despacho eliminado con éxito.', 'exito');
      },
      error: () => this.mostrarAlerta('❌ Error al eliminar despacho.', 'error')
    });
  }

  consultarDespacho(id: number) {
    this.api.getDespacho(id).subscribe({
      next: res => this.despachoConsultado = res,
      error: () => this.mostrarAlerta('❌ Error al consultar despacho.', 'error')
    });
  }

  cerrarConsulta() {
    this.despachoConsultado = null;
  }

  // -------------------- HELPERS --------------------
  private resetForm() {
    this.despachoForm = this.getEmptyForm();
    this.editando = false;
    this.editId = null;
  }

  private getEmptyForm() {
    return {
      numero_remesa: '',
      numero_placas: '',
      tipo_pago_id: '',
      fecha_despacho: '',
      negociacion: '',
      anticipo: '',
      saldo: '',
      observaciones_mer: ''
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

  // filtros
  despachosFiltrados(despachos: any[]) {
    return despachos.filter(d =>
      (!this.filtroRemesa || d.mercancias?.numero_remesa?.toLowerCase().includes(this.filtroRemesa.toLowerCase())) &&
      (!this.filtroPlaca || d.vehiculos?.numero_placas?.toLowerCase().includes(this.filtroPlaca.toLowerCase()))
    );
  }
}