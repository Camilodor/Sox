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
  selector: 'app-proveedores',
  standalone: true,
  imports: [FormsModule, CommonModule, NgxPaginationModule],
  templateUrl: './proveedores.component.html',
  styleUrls: ['./proveedores.component.css']
})
export class ProveedoresComponent {
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
  // Exportaciones
  // -------------------------------
  exportarWord(proveedores: any[]) {
    const rows = proveedores.map(p =>
      new TableRow({
        children: [
          new TableCell({ children: [new Paragraph(p.id.toString())] }),
          new TableCell({ children: [new Paragraph(p.nombre)] }),
          new TableCell({ children: [new Paragraph(p.descripcion ?? '')] }),
          new TableCell({ children: [new Paragraph(p.usuario?.numero_documento ?? '')] }),
        ],
      })
    );

    const doc = new Document({
      sections: [
        {
          children: [
            new Paragraph("🏢 Reporte de Proveedores"),
            new Table({
              rows: [
                new TableRow({
                  children: [
                    new TableCell({ children: [new Paragraph("ID")] }),
                    new TableCell({ children: [new Paragraph("Nombre")] }),
                    new TableCell({ children: [new Paragraph("Descripción")] }),
                    new TableCell({ children: [new Paragraph("Documento Usuario")] }),
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
      saveAs(blob, 'proveedores.docx');
    });
  }

  async exportarPDF(proveedores: any[]) {
    const pdfMakeModule = await import('pdfmake/build/pdfmake');
    const pdfFonts = await import('pdfmake/build/vfs_fonts');
    (pdfMakeModule as any).vfs = (pdfFonts as any).vfs;

    const body = [
      ["ID", "Nombre", "Descripción", "Documento Usuario"],
      ...proveedores.map(p => [p.id, p.nombre, p.descripcion, p.usuario?.numero_documento ?? '']),
    ];

    const docDefinition = {
      content: [
        { text: "🏢 Reporte de Proveedores", style: "header" },
        { table: { body } },
      ],
      styles: {
        header: { fontSize: 18, bold: true, margin: [0, 0, 0, 10] },
      },
    };

    (pdfMakeModule as any).createPdf(docDefinition).download("proveedores.pdf");
  }

  private refrescar$ = new BehaviorSubject<void>(undefined);

  data$ = this.refrescar$.pipe(
    switchMap(() =>
      forkJoin({
        proveedores: this.api.getProveedores()
      })
    )
  );

  // 📌 estados UI
  cargando = false;
  proveedorForm: any = this.getEmptyForm();
  editando = false;
  editId: number | null = null;
  filtroNombre = '';
  filtroDocumento = '';
  filtroId = '';
  modalVisible = false;
  proveedorConsultado: any = null;

  // 📌 paginación
  page = 1;
  pageSize = 8;

  // 📌 ordenamiento
  sortColumn: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  // 🔔 alertas
  alertaVisible = false;
  alertaMensaje = '';
  alertaTipo: 'exito' | 'error' = 'exito';

  constructor(private api: ApiService) {}

  // -------------------------------
  // Modales
  // -------------------------------
  abrirModal(p?: any) {
    if (p) {
      this.editando = true;
      this.editId = p.id;
      this.proveedorForm = { ...p, numero_documento: p.usuario?.numero_documento ?? '' };
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
  // Crear / Editar proveedor
  // -------------------------------
  guardarProveedor() {
    if (!this.proveedorForm.nombre || !this.proveedorForm.numero_documento) {
      return this.mostrarAlerta('⚠️ Complete los campos obligatorios.', 'error');
    }

    const accion = this.editando ? 'editar' : 'crear';
    if (!confirm(`¿Está seguro de ${accion} este proveedor?`)) return;

    const request = this.editando && this.editId
      ? this.api.actualizarProveedor(this.editId, this.proveedorForm)
      : this.api.crearProveedor(this.proveedorForm);

    request.subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta(`✅ Proveedor ${this.editando ? 'actualizado' : 'registrado'} con éxito.`, 'exito');
      },
      error: () => this.mostrarAlerta('❌ Número de documento incorrecto o datos inválidos.', 'error')
    });
  }

  // -------------------------------
  // Eliminar proveedor
  // -------------------------------
  eliminarProveedor(id: number) {
    if (!confirm('¿Deseas eliminar este proveedor?')) return;

    this.api.eliminarProveedor(id).subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta('🗑️ Proveedor eliminado con éxito.', 'exito');
      },
      error: () => this.mostrarAlerta('❌ Error al eliminar proveedor.', 'error')
    });
  }

  // -------------------------------
  // Consultar proveedor
  // -------------------------------
  consultarProveedor(id: number) {
    this.api.getProveedor(id).subscribe({
      next: res => this.proveedorConsultado = res,
      error: () => this.mostrarAlerta('❌ Error al consultar proveedor.', 'error')
    });
  }

  cerrarConsulta() {
    this.proveedorConsultado = null;
  }

  // -------------------------------
  // Helpers
  // -------------------------------
  private resetForm() {
    this.proveedorForm = this.getEmptyForm();
    this.editando = false;
    this.editId = null;
  }

  private getEmptyForm() {
    return {
      nombre: '',
      descripcion: '',
      numero_documento: ''
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
  proveedoresFiltrados(proveedores: any[]) {
    let lista = proveedores.filter(p => {
      const nombre = p.nombre?.toLowerCase() ?? '';
      const numeroDoc = p.usuario?.numero_documento?.toString() ?? '';

      return (!this.filtroNombre || nombre.includes(this.filtroNombre.toLowerCase()))
        && (!this.filtroDocumento || numeroDoc.includes(this.filtroDocumento))
        && (!this.filtroId || p.id.toString().includes(this.filtroId));
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
  // Exportar
  // -------------------------------
  exportarExcel(proveedores: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(proveedores);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Proveedores');
    XLSX.writeFile(workbook, 'proveedores.xlsx');
  }

  exportarCSV(proveedores: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(proveedores);
    const csv = XLSX.utils.sheet_to_csv(worksheet);
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    saveAs(blob, 'proveedores.csv');
  }
}
