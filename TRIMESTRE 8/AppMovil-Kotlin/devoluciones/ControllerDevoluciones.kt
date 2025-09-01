package com.example.demo.devoluciones

import com.example.demo.devoluciones.Devoluciones
import com.example.demo.devoluciones.ServiceDevoluciones
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ControllerDevoluciones {


    @Autowired
    lateinit var serviceDevoluciones: ServiceDevoluciones


    @GetMapping("/devoluciones")
    fun Listardevoluciones(): List<Devoluciones> = serviceDevoluciones.listarDevoluciones()


    @GetMapping("/devoluciones/{id}")
    fun bbtenerdetalles(@PathVariable id: Int): Devoluciones? = serviceDevoluciones.Detalledevolucion(id)

    @PostMapping("/devoluciones")
    fun creardevoluciones(@RequestBody devoluciones: Devoluciones): String {
        val filas = serviceDevoluciones.Creardevoluciones(devoluciones)
        return if (filas > 0) "Devolucion creada con éxito" else "No se pudo crear la devolucion "
    }

    @PutMapping("/devoluciones/{id}")
    fun actualizarDevolucion(@PathVariable id: Int, @RequestBody devoluciones: Devoluciones): String {
        val filas = serviceDevoluciones.actualizarDevolucion(id, devoluciones)
        return if (filas > 0) "Devolucion actualizada con éxito" else "No se encontró la Devolucion con id $id"


    }
    @DeleteMapping("/devoluciones/{id}")
    fun eliminardevoluciones(@PathVariable id: Int): String{
        val filas = serviceDevoluciones.EliminarDevolucion(id)
        return if (filas > 0) "Devolucion eliminada con éxito" else "No se encontró la Devolucion con id $id"
    }
}