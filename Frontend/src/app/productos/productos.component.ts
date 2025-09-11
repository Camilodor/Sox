import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { forkJoin, BehaviorSubject, switchMap } from 'rxjs';
import { ApiService } from '../services/api.service';
import { AuthService } from '../services/auth.service';
import { NgxPaginationModule } from 'ngx-pagination';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import { Document, Packer, Paragraph, Table, TableRow, TableCell } from 'docx';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [FormsModule, CommonModule, NgxPaginationModule],
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css']
})
export class ProductosComponent implements OnInit {
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

  // 🔔 Alertas
  alertaVisible = false;
  alertaMensaje = '';
  alertaTipo: 'exito' | 'error' = 'exito';

  // 📌 estados
  cargando = false;
  productoForm: any = this.getEmptyForm();
  editando = false;
  editId: number | null = null;
  modalVisible = false;
  productoConsultado: any = null;
  filtroNombre = '';
  filtroProveedor = '';

  // 📌 paginación
  page = 1;
  pageSize = 8;

  // 📌 ordenamiento
  sortColumn: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  private refrescar$ = new BehaviorSubject<void>(undefined);

  data$ = this.refrescar$.pipe(
    switchMap(() =>
      forkJoin({
        proveedores: this.api.getProveedores(),
        productos: this.api.getProductos()
      })
    )
  );

  constructor(private api: ApiService, private auth: AuthService) {}

  // -------------------------------
  // Modales
  // -------------------------------
  abrirModal(p?: any) {
    if (p) {
      this.editando = true;
      this.editId = p.id;
      this.productoForm = {
        nombre: p.nombre,
        descripcion: p.descripcion,
        proveedores_id: p.proveedores?.id ?? '',
        usuario_id: p.usuario?.id ?? null
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

  // -------------------------------
  // Crear / Editar producto
  // -------------------------------
  guardarProducto() {
    if (!this.productoForm.nombre || !this.productoForm.proveedores_id) {
      return this.mostrarAlerta('⚠️ Complete los campos obligatorios.', 'error');
    }

    // asignar usuario creador desde token
    this.productoForm.usuario_id = this.auth.getUser()?.id ?? null;

    const accion = this.editando ? 'editar' : 'crear';
    if (!confirm(`¿Está seguro de ${accion} este producto?`)) return;

    const request = this.editando && this.editId
      ? this.api.actualizarProducto(this.editId, this.productoForm)
      : this.api.crearProducto(this.productoForm);

    request.subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta(`✅ Producto ${this.editando ? 'actualizado' : 'registrado'} con éxito.`, 'exito');
      },
      error: () => this.mostrarAlerta('❌ Información incorrecta, intente nuevamente.', 'error')
    });
  }

  // -------------------------------
  // Eliminar producto
  // -------------------------------
  eliminarProducto(id: number) {
    if (!confirm('¿Deseas eliminar este producto?')) return;

    this.api.eliminarProducto(id).subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta('🗑️ Producto eliminado con éxito.', 'exito');
      },
      error: () => this.mostrarAlerta('❌ Error al eliminar producto.', 'error')
    });
  }

  // -------------------------------
  // Consultar producto
  // -------------------------------
  consultarProducto(id: number) {
    this.api.getProducto(id).subscribe({
      next: res => this.productoConsultado = res,
      error: () => this.mostrarAlerta('❌ Error al consultar producto.', 'error')
    });
  }

  cerrarConsulta() {
    this.productoConsultado = null;
  }

  // -------------------------------
  // Helpers
  // -------------------------------
  private resetForm() {
    this.productoForm = this.getEmptyForm();
    this.editando = false;
    this.editId = null;
  }

  private getEmptyForm() {
    return {
      nombre: '',
      descripcion: '',
      proveedores_id: '',
      usuario_id: ''
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

  productosFiltrados(productos: any[], proveedores: any[]) {
    let lista = productos.filter(p => {
      const nombre = p.nombre?.toLowerCase() ?? '';
      const proveedorNombre = p.proveedores?.nombre?.toLowerCase() ?? '';
      return (!this.filtroNombre || nombre.includes(this.filtroNombre.toLowerCase()))
        && (!this.filtroProveedor || proveedorNombre.includes(this.filtroProveedor.toLowerCase()));
    });

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

  getNombreProveedor(id: number, proveedores: any[]) {
    return proveedores.find(p => p.id === id)?.nombre ?? '';
  }

  // -------------------------------
  // Exportar
  // -------------------------------
  exportarExcel(productos: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(productos);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Productos');
    XLSX.writeFile(workbook, 'productos.xlsx');
  }

  exportarCSV(productos: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(productos);
    const csv = XLSX.utils.sheet_to_csv(worksheet);
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    saveAs(blob, 'productos.csv');
  }

  exportarWord(productos: any[]) {
    const rows = productos.map(p =>
      new TableRow({
        children: [
          new TableCell({ children: [new Paragraph(p.id.toString())] }),
          new TableCell({ children: [new Paragraph(p.nombre)] }),
          new TableCell({ children: [new Paragraph(p.proveedores?.nombre || '')] }),
          new TableCell({ children: [new Paragraph(p.usuario?.nombre_completo || '')] }),
        ],
      })
    );

    const doc = new Document({
      sections: [
        {
          children: [
            new Paragraph("📦 Reporte de Productos"),
            new Table({
              rows: [
                new TableRow({
                  children: [
                    new TableCell({ children: [new Paragraph("ID")] }),
                    new TableCell({ children: [new Paragraph("Nombre")] }),
                    new TableCell({ children: [new Paragraph("Proveedor")] }),
                    new TableCell({ children: [new Paragraph("Usuario Asignado")] }),
                  ],
                }),
                ...rows,
              ],
            }),
          ],
        },
      ],
    });

    Packer.toBlob(doc).then(blob => saveAs(blob, 'productos.docx'));
  }

  async exportarPDF(productos: any[]) {
    const pdfMakeModule = await import('pdfmake/build/pdfmake');
    const pdfFonts = await import('pdfmake/build/vfs_fonts');
    (pdfMakeModule as any).vfs = (pdfFonts as any).vfs;

    const body = [
      ["ID", "Nombre", "Proveedor", "Usuario Asignado"],
      ...productos.map(p => [
        p.id,
        p.nombre,
        p.proveedores?.nombre || '',
        p.usuario?.nombre_completo || ''
      ]),
    ];

    const docDefinition = {
      content: [
        { text: "📦 Reporte de Productos", style: "header" },
        { table: { body } },
      ],
      styles: {
        header: { fontSize: 18, bold: true, margin: [0, 0, 0, 10] },
      },
    };

    (pdfMakeModule as any).createPdf(docDefinition).download("productos.pdf");
  }
}
