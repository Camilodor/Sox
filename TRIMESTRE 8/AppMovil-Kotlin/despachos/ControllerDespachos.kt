package com.example.demo.despachos

import com.example.demo.despachos.Despachos
import com.example.demo.despachos.ServiceDespachos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ControllerDespachos {



    @Autowired
    lateinit var serviceDespachos: ServiceDespachos

    @GetMapping("/despachos")
    fun Listardepacho(): List<Despachos> = serviceDespachos.Listardespachos()


    @GetMapping("/despachos/{id}")
    fun detalledespacho(@PathVariable id: Int): Despachos? = serviceDespachos.Detalleddespachos(id)



}