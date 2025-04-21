<?php

namespace App\Http\Controllers;

use App\Models\Usuario;
use Illuminate\Http\Request;

class UsuarioController extends Controller
{
    public function index()
    {
        $usuarios = Usuario::all();
        return response()->json($usuarios, 200);
    }

    public function store(Request $request)
    {
        $request->validate([
            'nombre_usuario' => 'required|unique:usuarios',
            'nombres' => 'required|string|max:255',
            'apellidos' => 'required|string|max:255',
            'tipodocumento_id' => 'required|integer|exists:tiposdocumento,id',
            'numero_documento' => 'required|integer|unique:usuarios',
            'telefono' => 'required|string|max:20',
            'direccion' => 'required|string|max:255',
            'ciudad' => 'required|string|max:100',
            'email' => 'required|email|unique:usuarios',
            'contraseña' => 'required|min:6',
            'tiporol_id' => 'required|integer|exists:tiposrol,id'
        ]);

        $usuario = Usuario::create($request->all());

        return response()->json([
            'message' => 'Usuario creado exitosamente',
            'usuario' => $usuario
        ], 201);
    }

    public function show($id)
    {
        $usuario = Usuario::find($id);

        if (!$usuario) {
            return response()->json(['message' => 'Usuario no encontrado'], 404);
        }

        return response()->json($usuario, 200);
    }

    public function update(Request $request, $id)
    {
        $usuario = Usuario::find($id);

        if (!$usuario) {
            return response()->json(['message' => 'Usuario no encontrado'], 404);
        }

        $request->validate([
            'nombre_usuario' => 'required|string|max:255|unique:usuarios,nombre_usuario,' . $id,
            'nombres' => 'required|string|max:255',
            'apellidos' => 'required|string|max:255',
            'tipodocumento_id' => 'required|integer|exists:tiposdocumento,id',
            'numero_documento' => 'required|integer|unique:usuarios,numero_documento,' . $id,
            'telefono' => 'required|string|max:20',
            'direccion' => 'required|string|max:255',
            'ciudad' => 'required|string|max:100',
            'email' => 'required|email|unique:usuarios,email,' . $id,
            'contraseña' => 'required|min:6',
            'tiporol_id' => 'required|integer|exists:tiposrol,id'
        ]);

        $usuario->update($request->all());

        return response()->json([
            'message' => 'Usuario actualizado exitosamente',
            'usuario' => $usuario
        ], 200);
    }

    public function destroy($id)
    {
        $usuario = Usuario::find($id);

        if (!$usuario) {
            return response()->json(['message' => 'Usuario no encontrado'], 404);
        }

        $usuario->delete();

        return response()->json([
            'message' => 'Usuario eliminado exitosamente'
        ], 200);
    }
}
