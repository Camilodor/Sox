package com.example.Sox.Controllers

import com.example.Sox.Modelos.Proveedor
import com.example.Sox.Service.ProveedorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class ProveedorController {

    @Autowired
    lateinit var proveedorService: ProveedorService

    // GET /proveedores → obtener todos los proveedores
    @GetMapping("/proveedores")
    fun obtenerProveedores(): List<Proveedor> = proveedorService.obtenerProveedores()

    // GET /proveedores/{id} → obtener proveedor por ID
    @GetMapping("/proveedores/{id}")
    fun obtenerProveedor(@PathVariable id: Int): Proveedor? =
        proveedorService.obtenerProveedorPorId(id)

    // POST /proveedores → crear nuevo proveedor
    @PostMapping("/proveedores")
    fun crearProveedor(@RequestBody proveedor: Proveedor): String {
        val filas = proveedorService.crearProveedor(proveedor)
        return if (filas > 0) "Proveedor creado con éxito" else "No se pudo crear proveedor"
    }

    // PUT /proveedores/{id} → actualizar proveedor por ID
    @PutMapping("/proveedores/{id}")
    fun actualizarProveedor(@PathVariable id: Int, @RequestBody proveedor: Proveedor): String {
        val filas = proveedorService.actualizarProveedor(id, proveedor)
        return if (filas > 0) "Proveedor actualizado con éxito" else "No se encontró proveedor con id $id"
    }

    // DELETE /proveedores/{id} → eliminar proveedor por ID
    @DeleteMapping("/proveedores/{id}")
    fun eliminarProveedor(@PathVariable id: Int): String {
        val filas = proveedorService.eliminarProveedor(id)
        return if (filas > 0) "Proveedor eliminado con éxito" else "No se encontró proveedor con id $id"
    }
}
