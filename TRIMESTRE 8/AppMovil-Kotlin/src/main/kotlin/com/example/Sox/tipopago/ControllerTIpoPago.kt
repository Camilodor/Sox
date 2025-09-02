package com.example.Sox.tipopago

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ControllerTIpoPago {



    @Autowired
    lateinit var serviceTipoPago: ServiceTipoPago



    @GetMapping("/tipospago")
    fun listartipopago(): List<TipoPago> = serviceTipoPago.listartipospago()




    @GetMapping("/tipospago/id")
    fun detalletipopago(@PathVariable id: Int): TipoPago? = serviceTipoPago.Detalletipospago(id)
}