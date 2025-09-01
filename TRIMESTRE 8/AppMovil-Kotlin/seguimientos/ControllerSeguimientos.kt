package com.example.demo.seguimientos

import com.example.demo.seguimientos.Seguimientos
import com.example.demo.seguimientos.ServiceSeguimientos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ControllerSeguimientos {


    @Autowired
    lateinit var serviceSeguimientos: ServiceSeguimientos

    @GetMapping("/seguimientos")
    fun listarseguimientos(): List<Seguimientos> = serviceSeguimientos.ListarSeguimientos()



    @GetMapping("/seguimientos/{id}")
    fun detaleseguimientos(@PathVariable id: Int): Seguimientos?= serviceSeguimientos.Detalleseguimientos(id)
}