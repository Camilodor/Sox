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
  selector: 'app-mercancias',
  standalone: true,
  imports: [FormsModule, CommonModule, NgxPaginationModule],
  templateUrl: './mercancias.component.html',
  styleUrls: ['./mercancias.component.css']
})
export class MercanciasComponent {
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

  // -------------------------------
  // Exportar a Word
  // -------------------------------
  exportarWord(mercancias: any[]) {
    const rows = mercancias.map(m =>
      new TableRow({
        children: [
          new TableCell({ children: [new Paragraph(m.id.toString())] }),
          new TableCell({ children: [new Paragraph(m.numero_remesa)] }),
          new TableCell({ children: [new Paragraph(m.origen_mercancia)] }),
          new TableCell({ children: [new Paragraph(m.destino_mercancia)] }),
          new TableCell({ children: [new Paragraph(m.peso?.toString() ?? '')] }),
          new TableCell({ children: [new Paragraph(m.unidades?.toString() ?? '')] }),
        ],
      })
    );

    const doc = new Document({
      sections: [
        {
          children: [
            new Paragraph("📦 Reporte de Mercancías"),
            new Table({
              rows: [
                new TableRow({
                  children: [
                    new TableCell({ children: [new Paragraph("ID")] }),
                    new TableCell({ children: [new Paragraph("Remesa")] }),
                    new TableCell({ children: [new Paragraph("Origen")] }),
                    new TableCell({ children: [new Paragraph("Destino")] }),
                    new TableCell({ children: [new Paragraph("Peso")] }),
                    new TableCell({ children: [new Paragraph("Unidades")] }),
                  ],
                }),
                ...rows,
              ],
            }),
          ],
        },
      ],
    });

    Packer.toBlob(doc).then(blob => {
      saveAs(blob, 'mercancias.docx');
    });
  }

  // -------------------------------
  // Exportar a PDF
  // -------------------------------
  async exportarPDF(mercancias: any[]) {
    const pdfMakeModule = await import('pdfmake/build/pdfmake');
    const pdfFonts = await import('pdfmake/build/vfs_fonts');
    (pdfMakeModule as any).vfs = (pdfFonts as any).vfs;

    const body = [
      ["ID", "Remesa", "Origen", "Destino", "Peso", "Unidades"],
      ...mercancias.map(m => [m.id, m.numero_remesa, m.origen_mercancia, m.destino_mercancia, m.peso, m.unidades]),
    ];

    const docDefinition = {
      content: [
        { text: "📦 Reporte de Mercancías", style: "header" },
        { table: { body } },
      ],
      styles: {
        header: { fontSize: 18, bold: true, margin: [0, 0, 0, 10] },
      },
    };

    (pdfMakeModule as any).createPdf(docDefinition).download("mercancias.pdf");
  }

  // -------------------------------
  // Observables y datos
  // -------------------------------
  private refrescar$ = new BehaviorSubject<void>(undefined);

  data$ = this.refrescar$.pipe(
    switchMap(() =>
      forkJoin({
        proveedores: this.api.getProveedores(),
        tiposPago: this.api.getTiposPago(),
        mercancias: this.api.getMercancias()
      })
    )
  );

  // 📌 estados de UI
  cargando = false;
  mercanciaForm: any = this.getEmptyForm();
  editando = false;
  editId: number | null = null;
  filtroRemesa = '';
  filtroOrigen = '';
  filtroDestino = '';
  modalVisible = false;
  mercanciaConsultada: any = null;

  // 📌 paginación
  page = 1;
  pageSize = 8;

  // 📌 ordenamiento
  sortColumn: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  // 🔔 sistema de alertas
  alertaVisible = false;
  alertaMensaje = '';
  alertaTipo: 'exito' | 'error' = 'exito';

  constructor(private api: ApiService) {}

  // -------------------------------
  // Modales
  // -------------------------------
  abrirModal(m?: any) {
    if (m) {
      this.editando = true;
      this.editId = m.id;
      this.mercanciaForm = { ...m };
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
  // Crear / Editar mercancia
  // -------------------------------
  guardarMercancia() {
    if (!this.mercanciaForm.numero_remesa || !this.mercanciaForm.origen_mercancia || !this.mercanciaForm.destino_mercancia) {
      return this.mostrarAlerta('⚠️ Complete los campos obligatorios.', 'error');
    }

    const accion = this.editando ? 'editar' : 'crear';
    if (!confirm(`¿Está seguro de ${accion} esta mercancía?`)) return;

    const request = this.editando && this.editId
      ? this.api.actualizarMercancia(this.editId, this.mercanciaForm)
      : this.api.crearMercancia(this.mercanciaForm);

    request.subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta(`✅ Mercancía ${this.editando ? 'actualizada' : 'registrada'} con éxito.`, 'exito');
      },
      error: () => this.mostrarAlerta('❌ Información incorrecta, intente nuevamente.', 'error')
    });
  }

  // -------------------------------
  // Eliminar mercancia
  // -------------------------------
  eliminarMercancia(id: number) {
    if (!confirm('¿Deseas eliminar esta mercancía?')) return;

    this.api.eliminarMercancia(id).subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta('🗑️ Mercancía eliminada con éxito.', 'exito');
      },
      error: () => this.mostrarAlerta('❌ Error al eliminar mercancía.', 'error')
    });
  }

  // -------------------------------
  // Consultar mercancia
  // -------------------------------
  consultarMercancia(id: number) {
    this.api.getMercancia(id).subscribe({
      next: res => this.mercanciaConsultada = res,
      error: () => this.mostrarAlerta('❌ Error al consultar mercancía.', 'error')
    });
  }

  cerrarConsulta() {
    this.mercanciaConsultada = null;
  }

  // -------------------------------
  // Helpers
  // -------------------------------
  private resetForm() {
    this.mercanciaForm = this.getEmptyForm();
    this.editando = false;
    this.editId = null;
  }

  private getEmptyForm() {
    return {
      proveedores_id: '',
      usuarios_id: '',
      fecha_ingreso: '',
      numero_remesa: '',
      origen_mercancia: '',
      destino_mercancia: '',
      nombre_remitente: '',
      documento_remitente: '',
      direccion_remitente: '',
      celular_remitente: '',
      nombre_destinatario: '',
      documento_destinatario: '',
      direccion_destinatario: '',
      celular_destinatario: '',
      valor_declarado: '',
      valor_flete: '',
      valor_total: '',
      peso: '',
      unidades: '',
      observaciones: '',
      tipo_pago_id: ''
    };
  }

  private refrescarDatos() {
    this.refrescar$.next();
    this.cerrarModal();
  }

  // -------------------------------
  // Alertas
  // -------------------------------
  mostrarAlerta(mensaje: string, tipo: 'exito' | 'error') {
    this.alertaMensaje = mensaje;
    this.alertaTipo = tipo;
    this.alertaVisible = true;
    setTimeout(() => this.alertaVisible = false, 4000);
  }

  cerrarAlerta() {
    this.alertaVisible = false;
  }

  // -------------------------------
  // Helpers de visualización
  // -------------------------------
  getNombreProveedor(id: number, proveedores: any[]) {
    return proveedores.find(p => p.id === id)?.nombre ?? '';
  }

  getNombreTipoPago(id: number, tiposPago: any[]) {
    return tiposPago.find(t => t.id === id)?.nombre ?? '';
  }

  mercanciasFiltradas(mercancias: any[]) {
    let lista = mercancias.filter(m => {
      const remesa = m.numero_remesa?.toLowerCase() ?? '';
      const origen = m.origen_mercancia?.toLowerCase() ?? '';
      const destino = m.destino_mercancia?.toLowerCase() ?? '';

      return (!this.filtroRemesa || remesa.includes(this.filtroRemesa.toLowerCase()))
        && (!this.filtroOrigen || origen.includes(this.filtroOrigen.toLowerCase()))
        && (!this.filtroDestino || destino.includes(this.filtroDestino.toLowerCase()));
    });

    // aplicar orden
    if (this.sortColumn) {
      lista = lista.sort((a, b) => {
        const valA = a[this.sortColumn]?.toString().toLowerCase() ?? '';
        const valB = b[this.sortColumn]?.toString().toLowerCase() ?? '';
        if (valA < valB) return this.sortDirection === 'asc' ? -1 : 1;
        if (valA > valB) return this.sortDirection === 'asc' ? 1 : -1;
        return 0;
      });
    }

    return lista;
  }

  ordenar(columna: string) {
    if (this.sortColumn === columna) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = columna;
      this.sortDirection = 'asc';
    }
  }

  // -------------------------------
  // Exportar Excel / CSV
  // -------------------------------
  exportarExcel(mercancias: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(mercancias);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Mercancías');
    XLSX.writeFile(workbook, 'mercancias.xlsx');
  }

  exportarCSV(mercancias: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(mercancias);
    const csv = XLSX.utils.sheet_to_csv(worksheet);
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    saveAs(blob, 'mercancias.csv');
  }
}
