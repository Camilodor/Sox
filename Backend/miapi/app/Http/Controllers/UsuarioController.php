<?php

namespace App\Http\Controllers;

use App\Models\Usuario;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

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
            'tipo_documento_id' => 'required|integer|exists:tipos_documento,id',
            'numero_documento' => 'required|integer|unique:usuarios',
            'telefono' => 'required|string|max:20',
            'direccion' => 'required|string|max:255',
            'ciudad' => 'required|string|max:100',
            'email' => 'required|email|unique:usuarios',
            'contrasena' => 'required|min:6',
            'tiposrol_id' => 'required|integer|exists:tiposrol,id'
        ]);

        $usuarios = Usuario::create($request->all());

        return response()->json([
            'message' => 'Usuario creado exitosamente',
            'usuario' => $usuarios
        ], 201);
    }

    public function show($id)
    {
        $usuarios = Usuario::find($id);

        if (!$usuarios) {
            return response()->json(['message' => 'Usuario no encontrado'], 404);
        }

        return response()->json($usuarios, 200);
    }

    public function update(Request $request, $id)
    {
        $usuarios = Usuario::find($id);

        if (!$usuarios) {
            return response()->json(['message' => 'Usuario no encontrado'], 404);
        }

        $request->validate([
            'nombre_usuario' => 'required|string|max:255|unique:usuarios,nombre_usuario,' . $id,
            'nombres' => 'required|string|max:255',
            'apellidos' => 'required|string|max:255',
            'tipo_documento_id' => 'required|integer|exists:tipos_documento,id',
            'numero_documento' => 'required|integer|unique:usuarios,numero_documento,' . $id,
            'telefono' => 'required|string|max:20',
            'direccion' => 'required|string|max:255',
            'ciudad' => 'required|string|max:100',
            'email' => 'required|email|unique:usuarios,email,' . $id,
            'contrasena' => 'required|min:6',
            'tiposrol_id' => 'required|integer|exists:tiposrol,id'
        ]);

        $usuarios->update($request->all());

        return response()->json([
            'message' => 'Usuario actualizado exitosamente',
            'usuario' => $usuarios
        ], 200);
    }

    public function destroy($id)
    {
        $usuarios = Usuario::find($id);

        if (!$usuarios) {
            return response()->json(['message' => 'Usuario no encontrado'], 404);
        }

        $usuarios->delete();

        return response()->json([
            'message' => 'Usuario eliminado exitosamente'
        ], 200);
    }



    public function patch(Request $request, $id)
    {
        // 1) Buscar el usuario
        $usuarios = Usuario::find($id);
        if (!$usuarios) {
            return response()->json(['message' => 'Usuario no encontrado'], 404);
        }

        // 2) Definir reglas "sometimes" para los campos que puedan venir
        $rules = [
            'nombre_usuario'     => 'sometimes|unique:usuarios,nombre_usuario,' . $id,
            'nombres'            => 'sometimes|string|max:255',
            'apellidos'          => 'sometimes|string|max:255',
            'tipo_documento_id'  => 'sometimes|integer|exists:tipos_documento,id',
            'numero_documento'   => 'sometimes|string|unique:usuarios,numero_documento,' . $id,
            'telefono'           => 'sometimes|string|max:20',
            'direccion'          => 'sometimes|string|max:255',
            'ciudad'             => 'sometimes|string|max:100',
            'email'              => 'sometimes|email|unique:usuarios,email,' . $id,
            'contrasena'         => 'sometimes|min:6',
            'tiposrol_id'         => 'sometimes|integer|exists:tiposrol,id',
        ];

        // 3) Validar solo los campos presentes
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'message' => 'Errores de validación',
                'errors' => $validator->errors()
            ], 422);
        }

        // 4) Actualizar solo los campos enviados
        $usuarios->update($request->only(array_keys($rules)));

        // 5) Responder con el usuario actualizado
        return response()->json([
            'message' => 'Usuario actualizado parcialmente',
            'usuario' => $usuarios->fresh()
        ], 200);
    }

}
