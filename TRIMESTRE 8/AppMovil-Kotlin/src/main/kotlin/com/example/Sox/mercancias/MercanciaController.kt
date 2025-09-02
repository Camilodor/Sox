package com.example.Sox.mercancias

import com.example.Sox.Modelos.Mercancia
import com.example.Sox.Service.MercanciaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class MercanciaController {

    @Autowired
    lateinit var mercanciaService: MercanciaService

    // Obtener todas las mercancías
    @GetMapping("/mercancias")
    fun obtenerMercancias(): List<Mercancia> {
        return mercanciaService.obtenerMercancias()
    }

    // Obtener mercancia por ID
    @GetMapping("/mercancias/{id}")
    fun obtenerMercanciaPorId(@PathVariable id: Int): Mercancia? {
        return mercanciaService.obtenerMercanciaPorId(id)
    }

    // Crear nueva mercancia
    @PostMapping("/mercancias")
    fun crearMercancia(@RequestBody mercancia: Mercancia): String {
        val filas = mercanciaService.crearMercancia(mercancia)
        return if (filas > 0) "Mercancia creada con éxito" else "Error al crear la mercancia"
    }

    // Actualizar mercancia
    @PutMapping("/mercancias/{id}")
    fun actualizarMercancia(@PathVariable id: Int, @RequestBody mercancia: Mercancia): String {
        val filas = mercanciaService.actualizarMercancia(id, mercancia)
        return if (filas > 0) "Mercancia actualizada con éxito" else "No se encontró la mercancia con id $id"
    }

    // Eliminar mercancia
    @DeleteMapping("/mercancias/{id}")
    fun eliminarMercancia(@PathVariable id: Int): String {
        val filas = mercanciaService.eliminarMercancia(id)
        return if (filas > 0) "Mercancia eliminada con éxito" else "No se encontró la mercancia con id $id"
    }
}
