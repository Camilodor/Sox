<?php

namespace App\Http\Controllers;


use Illuminate\Support\Facades\Hash;
use App\Models\Usuario;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;



class UsuarioController extends Controller
{
     /**
     * @OA\Get(
     *     path="/api/usuarios",
     *     tags={"Usuarios"},
     *     summary="Obtener lista de usuarios",
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Lista de usuarios")
     * )
     */
    public function index()
    {
        $usuarios = Usuario::all();
        return response()->json($usuarios, 200);
    }
    
    /**
     * @OA\Post(
     *     path="/api/usuarios",
     *     tags={"Usuarios"},
     *     summary="Crear un nuevo usuario",
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/Usuario")
     *     ),
     *     @OA\Response(response=201, description="Usuario creado exitosamente"),
     *     @OA\Response(response=422, description="Errores de validación")
     * )
     */
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
        'email' => 'required|string|email|unique:usuarios',
        'contrasena' => 'required|string|min:6',
        'tiposrol_id' => 'required|exists:tiposrol,id'
    ]);

    // Crear el usuario
    $usuario = Usuario::create([
        'nombre_usuario' => $request->nombre_usuario,
        'nombres' => $request->nombres,
        'apellidos' => $request->apellidos,
        'tipo_documento_id' => $request->tipo_documento_id,
        'numero_documento' => $request->numero_documento,
        'telefono' => $request->telefono,
        'direccion' => $request->direccion,
        'ciudad' => $request->ciudad,
        'email' => $request->email,
        'contrasena' => Hash::make($request->contrasena),
        'tiposrol_id' => $request->tiposrol_id
    ]);

    return response()->json([
        'message' => 'Usuario creado exitosamente',
        'usuario' => $usuario
    ], 201);
}

 /**
     * @OA\Get(
     *     path="/api/usuarios/{id}",
     *     tags={"Usuarios"},
     *     summary="Obtener un usuario por ID",
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del usuario", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Usuario encontrado"),
     *     @OA\Response(response=404, description="Usuario no encontrado")
     * )
     */
    public function show($id)
    {
        $usuarios = Usuario::find($id);

        if (!$usuarios) {
            return response()->json(['message' => 'Usuario no encontrado'], 404);
        }

        return response()->json($usuarios, 200);
    }

    /**
     * @OA\Put(
     *     path="/api/usuarios/{id}",
     *     tags={"Usuarios"},
     *     summary="Actualizar usuario por ID",
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del usuario", @OA\Schema(type="integer")),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/Usuario")
     *     ),
     *     @OA\Response(response=200, description="Usuario actualizado exitosamente"),
     *     @OA\Response(response=404, description="Usuario no encontrado")
     * )
     */
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

    /**
     * @OA\Delete(
     *     path="/api/usuarios/{id}",
     *     tags={"Usuarios"},
     *     summary="Eliminar un usuario",
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del usuario", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Usuario eliminado exitosamente"),
     *     @OA\Response(response=404, description="Usuario no encontrado")
     * )
     */
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

/**
     * @OA\Patch(
     *     path="/api/usuarios/{id}",
     *     tags={"Usuarios"},
     *     summary="Actualizar parcialmente un usuario",
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del usuario", @OA\Schema(type="integer")),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             example={
     *                 "email": "nuevo@email.com",
     *                 "telefono": "3123456789"
     *             }
     *         )
     *     ),
     *     @OA\Response(response=200, description="Usuario actualizado parcialmente"),
     *     @OA\Response(response=404, description="Usuario no encontrado")
     * )
     */

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
