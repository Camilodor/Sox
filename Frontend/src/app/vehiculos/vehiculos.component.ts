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
  selector: 'app-vehiculos',
  standalone: true,
  imports: [FormsModule, CommonModule, NgxPaginationModule],
  templateUrl: './vehiculos.component.html',
  styleUrls: ['./vehiculos.component.css']
})
export class VehiculosComponent {
  // UI
  dropdownOpen = false;
  alertaVisible = false;
  alertaMensaje = '';
  alertaTipo: 'exito' | 'error' = 'exito';

  // datos / estado
  private refrescar$ = new BehaviorSubject<void>(undefined);
  data$ = this.refrescar$.pipe(
    switchMap(() =>
      forkJoin({
        usuarios: this.api.getUsuarios(), // usado para validación local de numero_documento
        vehiculos: this.api.getVehiculos()
      })
    )
  );

  vehiculoForm: any = this.getEmptyForm();
  editando = false;
  editId: number | null = null;
  modalVisible = false;
  vehiculoConsultado: any = null;

  // filtros/paginación/orden
  filtroPlaca = '';
  filtroConductor = '';
  page = 1;
  pageSize = 8;
  sortColumn: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  constructor(private api: ApiService) {}

  // dropdown
  toggleDropdown(event: MouseEvent) {
    event.stopPropagation();
    this.dropdownOpen = !this.dropdownOpen;
  }

  // -------------------------------
  // Exportaciones
  // -------------------------------
  exportarExcel(vehiculos: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(vehiculos);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Vehículos');
    XLSX.writeFile(workbook, 'vehiculos.xlsx');
  }

  exportarCSV(vehiculos: any[]) {
    const worksheet = XLSX.utils.json_to_sheet(vehiculos);
    const csv = XLSX.utils.sheet_to_csv(worksheet);
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    saveAs(blob, 'vehiculos.csv');
  }

  exportarWord(vehiculos: any[]) {
    const rows = vehiculos.map(v =>
      new TableRow({
        children: [
          new TableCell({ children: [new Paragraph(v.id?.toString() ?? '')] }),
          new TableCell({ children: [new Paragraph(v.numero_placas ?? '')] }),
          new TableCell({ children: [new Paragraph(v.nombre_marca_vehiculo ?? '')] }),
          new TableCell({ children: [new Paragraph(v.nombre_propietario_vehiculo ?? '')] }),
          new TableCell({ children: [new Paragraph(v.usuario?.nombre_completo ?? '')] }),
        ],
      })
    );

    const doc = new Document({
      sections: [
        {
          children: [
            new Paragraph("🚚 Reporte de Vehículos"),
            new Table({
              rows: [
                new TableRow({
                  children: [
                    new TableCell({ children: [new Paragraph("ID")] }),
                    new TableCell({ children: [new Paragraph("Placa")] }),
                    new TableCell({ children: [new Paragraph("Marca")] }),
                    new TableCell({ children: [new Paragraph("Propietario")] }),
                    new TableCell({ children: [new Paragraph("Conductor")] }),
                  ],
                }),
                ...rows,
              ],
            }),
          ],
        },
      ],
    });

    Packer.toBlob(doc).then(blob => saveAs(blob, 'vehiculos.docx'));
  }

  async exportarPDF(vehiculos: any[]) {
    const pdfMakeModule = await import('pdfmake/build/pdfmake');
    const pdfFonts = await import('pdfmake/build/vfs_fonts');
    (pdfMakeModule as any).vfs = (pdfFonts as any).vfs;

    const body = [
      ["ID", "Placa", "Marca", "Propietario", "Conductor"],
      ...vehiculos.map(v => [
        v.id,
        v.numero_placas,
        v.nombre_marca_vehiculo,
        v.nombre_propietario_vehiculo,
        v.usuario?.nombre_completo ?? ''
      ]),
    ];

    const docDefinition = {
      content: [
        { text: "🚚 Reporte de Vehículos", style: "header" },
        { table: { body } },
      ],
      styles: {
        header: { fontSize: 18, bold: true, margin: [0, 0, 0, 10] },
      },
    };

    (pdfMakeModule as any).createPdf(docDefinition).download("vehiculos.pdf");
  }

  // -------------------------------
  // Modales / CRUD
  // -------------------------------
  abrirModal(v?: any) {
    if (v) {
      this.editando = true;
      this.editId = v.id;
      // mapear campos; traer numero_documento desde v.usuario si existe
      this.vehiculoForm = {
        usuarios_id: v.usuarios_id ?? '',
        numero_placas: v.numero_placas ?? '',
        nombre_marca_vehiculo: v.nombre_marca_vehiculo ?? '',
        nombre_propietario_vehiculo: v.nombre_propietario_vehiculo ?? '',
        documento_propietario_vehiculo: v.documento_propietario_vehiculo ?? '',
        numero_celular_propietario: v.numero_celular_propietario ?? '',
        direccion_propietario: v.direccion_propietario ?? '',
        ciudad_propietario: v.ciudad_propietario ?? '',
        numero_modelo_año: v.numero_modelo_anio ?? '',
        color_vehiculo: v.color_vehiculo ?? '',
        fecha_vencimiento_soat: v.fecha_vencimiento_soat ?? '',
        fecha_vencimiento_tecno: v.fecha_vencimiento_tecno ?? '',
        nombre_satelital: v.nombre_satelital ?? '',
        usuario_satelital: v.usuario_satelital ?? '',
        contrasena_satelital: v.contrasena_satelital ?? '',
        capacidad_carga: v.capacidad_carga ?? '',
        numero_documento: v.usuario?.numero_documento ?? '' // campo usado para crear/actualizar en backend
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

  guardarVehiculo() {
    // campos mínimos que validamos en frontend
    if (!this.vehiculoForm.numero_placas || !this.vehiculoForm.nombre_marca_vehiculo || !this.vehiculoForm.nombre_propietario_vehiculo || !this.vehiculoForm.numero_documento) {
      return this.mostrarAlerta('⚠️ Complete los campos obligatorios (placas, marca, propietario, número documento conductor).', 'error');
    }

    // validar formato básico numero_documento (solo dígitos)
    const doc = (this.vehiculoForm.numero_documento ?? '').toString().trim();
    if (!/^\d{4,15}$/.test(doc)) {
      return this.mostrarAlerta('⚠️ Número de documento inválido.', 'error');
    }

    // validar existencia del usuario POR CLIENTE (buscar en lista de usuarios recuperada)
    this.api.getUsuarios().subscribe({
      next: users => {
        const encontrado = users.find((u: any) => String(u.numero_documento) === String(doc));
        if (!encontrado) {
          // si no existe, detener y mostrar error (backend también valida)
          return this.mostrarAlerta('❌ No existe un usuario con ese número de documento. No se puede asignar conductor.', 'error');
        }

        // prepara payload: enviar numero_documento (backend lo convierte a usuarios_id)
        const payload = { ...this.vehiculoForm };

        const request$ = (this.editando && this.editId)
          ? this.api.actualizarVehiculo(this.editId, payload)
          : this.api.crearVehiculo(payload);

        request$.subscribe({
          next: () => {
            this.refrescarDatos();
            this.mostrarAlerta(`✅ Vehículo ${this.editando ? 'actualizado' : 'registrado'} con éxito.`, 'exito');
          },
          error: (err: any) => {
            // mostrar errores devueltos por backend cuando corresponda
            const msg = err?.error ? JSON.stringify(err.error) : 'Error al guardar vehículo';
            this.mostrarAlerta(`❌ ${msg}`, 'error');
          }
        });
      },
      error: () => {
        this.mostrarAlerta('❌ Error al validar número de documento (no se pudieron obtener usuarios).', 'error');
      }
    });
  }

  eliminarVehiculo(id: number) {
    if (!confirm('¿Deseas eliminar este vehículo?')) return;

    this.api.eliminarVehiculo(id).subscribe({
      next: () => {
        this.refrescarDatos();
        this.mostrarAlerta('🗑️ Vehículo eliminado con éxito.', 'exito');
      },
      error: () => this.mostrarAlerta('❌ Error al eliminar vehículo.', 'error')
    });
  }

  consultarVehiculo(id: number) {
    this.api.getVehiculo(id).subscribe({
      next: res => this.vehiculoConsultado = res,
      error: () => this.mostrarAlerta('❌ Error al consultar vehículo.', 'error')
    });
  }

  cerrarConsulta() {
    this.vehiculoConsultado = null;
  }

  // -------------------------------
  // Helpers
  // -------------------------------
  private resetForm() {
    this.vehiculoForm = this.getEmptyForm();
    this.editando = false;
    this.editId = null;
  }

  private getEmptyForm() {
    return {
      usuarios_id: '',
      numero_placas: '',
      nombre_marca_vehiculo: '',
      nombre_propietario_vehiculo: '',
      documento_propietario_vehiculo: '',
      numero_celular_propietario: '',
      direccion_propietario: '',
      ciudad_propietario: '',
      numero_modelo_anio: '',
      color_vehiculo: '',
      fecha_vencimiento_soat: '',
      fecha_vencimiento_tecno: '',
      nombre_satelital: '',
      usuario_satelital: '',
      contrasena_satelital: '',
      capacidad_carga: '',
      numero_documento: '' // documento del conductor (para enviar al backend)
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

  // -------------------------------
  // Visual helpers / filtros / orden
  // -------------------------------
  vehiculosFiltrados(vehiculos: any[]) {
    let lista = vehiculos.filter(v => {
      const placa = (v.numero_placas ?? '').toString().toLowerCase();
      const conductorDoc = (v.usuario?.numero_documento ?? '').toString().toLowerCase();

      return (!this.filtroPlaca || placa.includes(this.filtroPlaca.toLowerCase()))
        && (!this.filtroConductor || conductorDoc.includes(this.filtroConductor.toLowerCase()));
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
}
