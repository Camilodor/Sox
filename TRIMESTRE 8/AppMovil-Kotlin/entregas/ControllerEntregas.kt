package com.example.demo.entregas

import com.example.demo.entregas.Entregas
import com.example.demo.entregas.ServiceEntregas
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ControllerEntregas {

    @Autowired
    lateinit var Serviceentregas: ServiceEntregas


    @GetMapping("/entregas")
    fun listarentregas(): List<Entregas> = Serviceentregas.Listarentregas()


    @GetMapping("/entregas/{id}")
    fun obtenerdetalle(@PathVariable id: Int): Entregas? = Serviceentregas.Detalleentrega(id)


    @PostMapping("/entregas")
    fun crearEntregas(@RequestBody entrega: Entregas): String{
            val filas = Serviceentregas.Crearentregas(entrega)
            return if (filas > 0) "Entrega realizada con éxito" else "No se pudo realizar la entrega"

    }
    @PutMapping("/entregas/{id}")
    fun actualizarEntrega(@PathVariable id: Int, @RequestBody entregas: Entregas): String {
        val filas = Serviceentregas.Actualizarentrega(id,entregas)
        return if (filas > 0) "Entrega actualizada con éxito" else "No se encontró la entrega con id $id"
    }

    @DeleteMapping("/entregas/{id}")
    fun eliminarEntrega(@PathVariable id: Int): String {
        val filas = Serviceentregas.Eliminarentrega(id)
        return if (filas > 0) "Entrega eliminada con éxito" else "No se encontró la entrega con id $id"
    }
}