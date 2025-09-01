package com.example.demo.mercancias

import com.example.demo.mercancias.Mercancias
import com.example.demo.mercancias.ServiceMercancias
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ControllerMercancias {

    @Autowired
    lateinit var serviceMercancias: ServiceMercancias


    @GetMapping("/mercancias")
    fun listarmercancia(): List<Mercancias> = serviceMercancias.listarmercancias()




    @GetMapping("/mercancias/{id}")
    fun detallemercancia(@PathVariable id: Int): Mercancias? = serviceMercancias.Detallemercancia(id)
}