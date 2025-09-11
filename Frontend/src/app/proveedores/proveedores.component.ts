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
export class ProveedoresComponent implements OnInit {
  dropdownOpen = false;

  constructor(private api: ApiService) {}

  ngOnInit() {
    document.addEventListener('click', () => this.dropdownOpen = false);
    this.refrescarDatos();
  }

  toggleDropdown(event: MouseEvent) {
    event.stopPropagation();
    this.dropdownOpen = !this.dropdownOpen;
  }

  // 🔹 Reactive refresher
  private refrescar$ = new BehaviorSubject<void>(undefined);
  data$ = this.refrescar$.pipe(
    switchMap(() => forkJoin({
      proveedores: this.api.getProveedores()
    }))
  );

  // 📌 Estados y UI
  proveedoresList: any[] = [];
  proveedorForm: any = this.getEmptyForm();
  proveedorConsultado: any = null;
  editando = false;
  editId: number | null = null;
  modalVisible = false;
  filtroNombre = '';
  filtroDocumento = '';

  page = 1;
  pageSize = 8;
  sortColumn = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  alertaVisible = false;
  alertaMensaje = '';
  alertaTipo: 'exito' | 'error' = 'exito';

  // -------------------------------
  // CRUD Proveedor
  // -------------------------------
  abrirModal(p?: any) {
    if (p) {
      this.editando = true;
      this.editId = p.id;
      this.proveedorForm = { ...p, numero_documento: p.usuario?.numero_documento };
    } else {
      this.resetForm();
    }
    this.modalVisible = true;
  }

  cerrarModal() {
    this.modalVisible = false;
    this.resetForm();
  }

 guardarProveedor() {
  // 1️⃣ Validación de campos obligatorios
  if (!this.proveedorForm.nombre || !this.proveedorForm.numero_documento) {
    return this.mostrarAlerta('⚠️ Complete los campos obligatorios.', 'error');
  }

  // 2️⃣ Confirmación de acción
  const accion = this.editando ? 'editar' : 'crear';
  if (!confirm(`¿Está seguro de ${accion} este proveedor?`)) return;

  // 3️⃣ Llamada al API
  const request = this.editando && this.editId
    ? this.api.actualizarProveedor(this.editId, this.proveedorForm)
    : this.api.crearProveedor(this.proveedorForm);

  // 4️⃣ Suscripción al resultado
  request.subscribe({
    next: () => {
      // Primero mostrar alerta
      this.mostrarAlerta(`✅ Proveedor ${this.editando ? 'actualizado' : 'registrado'} con éxito.`, 'exito');

      // Luego, cerrar modal y refrescar datos con un pequeño retraso para que la alerta se renderice
      setTimeout(() => {
        this.cerrarModal();        // cierra el modal
        this.refrescarDatos();     // refresca la lista
      }, 100); // 100ms es suficiente
    },
    error: () => {
      this.mostrarAlerta('❌ Información incorrecta, intente nuevamente.', 'error');
    }
  });
}



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
    return { nombre: '', descripcion: '', numero_documento: '' };
  }

    private refrescarDatos() {
    this.refrescar$.next();
    
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

  // -------------------------------
  // Filtros y ordenamiento
  // -------------------------------
  proveedoresFiltrados(proveedores: any[]) {
    let lista = proveedores.filter(p => {
      const nombre = p.nombre?.toLowerCase() ?? '';
      const numeroDoc = p.usuario?.numero_documento?.toString() ?? '';
      return (!this.filtroNombre || nombre.includes(this.filtroNombre.toLowerCase()))
          && (!this.filtroDocumento || numeroDoc.includes(this.filtroDocumento));
    });

    if (this.sortColumn) {
      lista = lista.sort((a, b) => {
        const valA = (a[this.sortColumn] ?? '').toString().toLowerCase();
        const valB = (b[this.sortColumn] ?? '').toString().toLowerCase();
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
  // Exportaciones
  // -------------------------------
  exportarExcel(proveedores: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(proveedores || []);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Proveedores');
    XLSX.writeFile(workbook, 'proveedores.xlsx');
  }

  exportarCSV(proveedores: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(proveedores || []);
    const csv = XLSX.utils.sheet_to_csv(worksheet);
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    saveAs(blob, 'proveedores.csv');
  }

  exportarWord(proveedores: any[]) {
    proveedores = proveedores || [];
    const rows = proveedores.map(p =>
      new TableRow({
        children: [
          new TableCell({ children: [new Paragraph(p.id.toString())] }),
          new TableCell({ children: [new Paragraph(p.nombre)] }),
          new TableCell({ children: [new Paragraph(p.descripcion || '')] }),
          new TableCell({ children: [new Paragraph(p.usuario?.numero_documento || '')] }),
          new TableCell({ children: [new Paragraph(p.usuario?.nombre_completo || '')] })
        ]
      })
    );

    const doc = new Document({
      sections: [{
        children: [
          new Paragraph("📦 Reporte de Proveedores"),
          new Table({
            rows: [
              new TableRow({
                children: [
                  new TableCell({ children:[new Paragraph('ID')] }),
                  new TableCell({ children:[new Paragraph('Nombre')] }),
                  new TableCell({ children:[new Paragraph('Descripción')] }),
                  new TableCell({ children:[new Paragraph('Cédula Usuario')] }),
                  new TableCell({ children:[new Paragraph('Nombre Usuario')] })
                ]
              }),
              ...rows
            ]
          })
        ]
      }]
    });

    Packer.toBlob(doc).then(blob => saveAs(blob, 'proveedores.docx'));
  }

  async exportarPDF(proveedores: any[]) {
    proveedores = proveedores || [];
    const pdfMakeModule = await import('pdfmake/build/pdfmake');
    const pdfFonts = await import('pdfmake/build/vfs_fonts');
    (pdfMakeModule as any).vfs = (pdfFonts as any).vfs;

    const body = [
      ["ID", "Nombre", "Descripción", "Cédula Usuario", "Nombre Usuario"],
      ...proveedores.map(p => [
        p.id,
        p.nombre,
        p.descripcion || '',
        p.usuario?.numero_documento || '',
        p.usuario?.nombre_completo || ''
      ])
    ];

    const docDefinition = {
      content: [{ text: "📦 Reporte de Proveedores", style: "header" }, { table: { body } }],
      styles: { header: { fontSize: 18, bold: true, margin: [0, 0, 0, 10] } }
    };

    (pdfMakeModule as any).createPdf(docDefinition).download("proveedores.pdf");
  }
}
