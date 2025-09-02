package com.example.Sox.Controllers.UsuarioController


import com.example.Sox.Modelos.Usuarios
import com.example.Sox.Service.UsuarioService.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping

@RestController
class UsuarioController {


    @Autowired
    lateinit var UsuarioService: UsuarioService

    @GetMapping("/usuarios")
    fun obtenerUsuarios(): List<Usuarios> = UsuarioService.obtenerUsuarios()

    // SHOW → GET /usuarios/{id}
    @GetMapping("/usuarios/{id}")
    fun obtenerUsuario(@PathVariable id: Int): Usuarios? =
        UsuarioService.obtenerUsuarioPorId(id)

    // STORE → POST /usuarios
    @PostMapping("/usuarios")
    fun crearUsuario(@RequestBody usuario: Usuarios): String {
        val filas = UsuarioService.crearUsuario(usuario)
        return if (filas > 0) "Usuario creado con éxito" else "No se pudo crear usuario"
    }

    // UPDATE → PUT /usuarios/{id}
    @PutMapping("/usuarios/{id}")
    fun actualizarUsuario(@PathVariable id: Int, @RequestBody usuario: Usuarios): String {
        val filas = UsuarioService.actualizarUsuario(id, usuario)
        return if (filas > 0) "Usuario actualizado con éxito" else "No se encontró usuario con id $id"
    }

    // DELETE → DELETE /usuarios/{id}
    @DeleteMapping("/usuarios/{id}")
    fun eliminarUsuario(@PathVariable id: Int): String {
        val filas = UsuarioService.eliminarUsuario(id)
        return if (filas > 0) "Usuario eliminado con éxito" else "No se encontró usuario con id $id"
    }
}