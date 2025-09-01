package com.example.demo.proveedores

import com.example.demo.proveedores.Provedores
import com.example.demo.proveedores.ServiceProveedores
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ControllerProveedores {


    @Autowired
    lateinit var serviceProveedores: ServiceProveedores

    @GetMapping("/proveedores")
    fun listarproveedores(): List<Provedores> = serviceProveedores.Listarproveedores()


    @GetMapping("/proveedores/{id}")
    fun detalleproveedor(@PathVariable id: Int): Provedores?= serviceProveedores.DetalleProveedor(id)
}