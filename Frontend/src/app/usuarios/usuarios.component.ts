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
  selector: 'app-usuarios',
  standalone: true,
  imports: [FormsModule, CommonModule, NgxPaginationModule],
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent {
 dropdownOpen = false;

toggleDropdown(event: MouseEvent) {
  event.stopPropagation(); // evita que el click cierre inmediatamente
  this.dropdownOpen = !this.dropdownOpen;
}
   
ngOnInit() {
  document.addEventListener('click', () => {
    this.dropdownOpen = false; // cierra si se hace click fuera
  });
}

   exportarWord(usuarios: any[]) {
    const rows = usuarios.map(u =>
      new TableRow({
        children: [
          new TableCell({ children: [new Paragraph(u.id.toString())] }),
          new TableCell({ children: [new Paragraph(u.nombre_usuario)] }),
          new TableCell({ children: [new Paragraph(u.nombres)] }),
          new TableCell({ children: [new Paragraph(u.numero_documento)] }),
        ],
      })
    );

    const doc = new Document({
      sections: [
        {
          children: [
            new Paragraph("👥 Reporte de Usuarios"),
            new Table({
              rows: [
                new TableRow({
                  children: [
                    new TableCell({ children: [new Paragraph("ID")] }),
                    new TableCell({ children: [new Paragraph("Usuario")] }),
                    new TableCell({ children: [new Paragraph("Nombres")] }),
                    new TableCell({ children: [new Paragraph("Cédula")] }),
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
      saveAs(blob, 'usuarios.docx');
    });
  }

  async exportarPDF(usuarios: any[]) {
  const pdfMakeModule = await import('pdfmake/build/pdfmake');
  const pdfFonts = await import('pdfmake/build/vfs_fonts');
  (pdfMakeModule as any).vfs = (pdfFonts as any).vfs;

  const body = [
    ["ID", "Usuario", "Nombres", "Cédula"],
    ...usuarios.map(u => [u.id, u.nombre_usuario, u.nombres, u.numero_documento]),
  ];

  const docDefinition = {
    content: [
      { text: "👥 Reporte de Usuarios", style: "header" },
      { table: { body } },
    ],
    styles: {
      header: { fontSize: 18, bold: true, margin: [0, 0, 0, 10] },
    },
  };

  (pdfMakeModule as any).createPdf(docDefinition).download("usuarios.pdf");
}

  private refrescar$ = new BehaviorSubject<void>(undefined);

  data$ = this.refrescar$.pipe(
    switchMap(() =>
      forkJoin({
        roles: this.api.getTiposRol(),
        documentos: this.api.getTiposDocumento(),
        usuarios: this.api.getUsuarios()
      })
    )
  );

  // 📌 estados de UI
  cargando = false;
  usuarioForm: any = this.getEmptyForm();
  editando = false;
  editId: number | null = null;
  filtroNombre = '';
  filtroCedula = '';
  filtroId = '';
  modalVisible = false;
  usuarioConsultado: any = null;

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
  abrirModal(u?: any) {
    if (u) {
      this.editando = true;
      this.editId = u.id;
      this.usuarioForm = { ...u };
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
  // Crear / Editar usuario
  // -------------------------------
  guardarUsuario() {
    if (!this.usuarioForm.nombre_usuario || !this.usuarioForm.nombres || !this.usuarioForm.email) {
      return this.mostrarAlerta('⚠️ Complete los campos obligatorios.', 'error');
    }

    const accion = this.editando ? 'editar' : 'crear';
    if (!confirm(`¿Está seguro de ${accion} este usuario?`)) return;

    const request = this.editando && this.editId
      ? this.api.actualizarUsuario(this.editId, this.usuarioForm)
      : this.api.crearUsuario(this.usuarioForm);

    request.subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta(`✅ Usuario ${this.editando ? 'actualizado' : 'registrado'} con éxito.`, 'exito');
      },
      error: () => this.mostrarAlerta('❌ Información incorrecta, intente nuevamente.', 'error')
    });
  }

  // -------------------------------
  // Eliminar usuario
  // -------------------------------
  eliminarUsuario(id: number) {
    if (!confirm('¿Deseas eliminar este usuario?')) return;

    this.api.eliminarUsuario(id).subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta('🗑️ Usuario eliminado con éxito.', 'exito');
      },
      error: () => this.mostrarAlerta('❌ Error al eliminar usuario.', 'error')
    });
  }

  // -------------------------------
  // Consultar usuario
  // -------------------------------
  consultarUsuario(id: number) {
    this.api.getUsuario(id).subscribe({
      next: res => this.usuarioConsultado = res,
      error: () => this.mostrarAlerta('❌ Error al consultar usuario.', 'error')
    });
  }

  cerrarConsulta() {
    this.usuarioConsultado = null;
  }

  // -------------------------------
  // Helpers
  // -------------------------------
  private resetForm() {
    this.usuarioForm = this.getEmptyForm();
    this.editando = false;
    this.editId = null;
  }

  private getEmptyForm() {
    return {
      nombre_usuario: '',
      nombres: '',
      apellidos: '',
      tipo_documento_id: '',
      numero_documento: '',
      celular: '',
      direccion: '',
      ciudad: '',
      email: '',
      contrasena: '',
      tipo_rol_id: ''
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
  getNombreDocumento(id: number, documentos: any[]) {
    return documentos.find(d => d.id === id)?.nombre ?? '';
  }

  getNombreRol(id: number, roles: any[]) {
    return roles.find(r => r.id === id)?.nombre ?? '';
  }

  usuariosFiltrados(usuarios: any[]) {
    let lista = usuarios.filter(u => {
      const nombres = u.nombres?.toLowerCase() ?? '';
      const apellidos = u.apellidos?.toLowerCase() ?? '';
      const numeroDoc = u.numero_documento?.toString() ?? '';

      return (!this.filtroNombre || nombres.includes(this.filtroNombre.toLowerCase()) || apellidos.includes(this.filtroNombre.toLowerCase()))
        && (!this.filtroCedula || numeroDoc.includes(this.filtroCedula))
        && (!this.filtroId || u.id.toString().includes(this.filtroId));
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
  exportarExcel(usuarios: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(usuarios);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Usuarios');
    XLSX.writeFile(workbook, 'usuarios.xlsx');
  }

  exportarCSV(usuarios: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(usuarios);
    const csv = XLSX.utils.sheet_to_csv(worksheet);
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    saveAs(blob, 'usuarios.csv');
  }
}
