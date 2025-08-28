<?php

namespace App\Http\Controllers;


use App\Models\Usuario;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;
use PHPOpenSourceSaver\JWTAuth\Facades\JWTAuth;
/**
 * @OA\Info(
 *     title="Nombre de tu API",
 *     version="1.0",
 *     description="Descripción de tu API"
 * )
 */

class AuthController extends Controller
{
      /**
     * @OA\Post(
     *     path="/api/login",
     *     summary="Iniciar sesión (Login)",
     *     tags={"Autenticación"},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"nombre_usuario", "contrasena"},
     *             @OA\Property(property="nombre_usuario", type="string", example="admin123"),
     *             @OA\Property(property="contrasena", type="string", example="12345678")
     *         )
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Inicio de sesión exitoso",
     *         @OA\JsonContent(
     *             @OA\Property(property="token", type="string", example="eyJ0eXAiOiJKV1QiLCJh..."),
     *             @OA\Property(property="usuario", type="object")
     *         )
     *     ),
     *     @OA\Response(response=401, description="Credenciales inválidas"),
     *     @OA\Response(response=403, description="Rol no autorizado"),
     *     @OA\Response(response=422, description="Errores de validación")
     * )
     */
    // LOGIN
    public function login(Request $request)
    {
        $credentials = $request->only('nombre_usuario', 'contrasena');

        $validator = Validator::make($credentials, [
            'nombre_usuario' => 'required|string',
            'contrasena' => 'required|string',

        ]);

        if ($validator->fails()) {
            return response()->json(['error' => $validator->errors()], 422);
        }

        // Buscar usuario por nombre_usuario
        $usuario = Usuario::where('nombre_usuario', $credentials['nombre_usuario'])->first();

        if (!$usuario || !Hash::check($credentials['contrasena'], $usuario->contrasena)) {
            return response()->json(['error' => 'Credenciales inválidas'], 401);
        }

        // Aquí puedes validar si el rol es permitido (ejemplo: 1=admin, 2=bodeguero, 3=conductor)
        if (!in_array($usuario->tiposrol_id, [1, 2, 3])) {
            return response()->json(['error' => 'Rol no autorizado'], 403);
        }

        $token = JWTAuth::fromUser($usuario);

        return response()->json([
            'token' => $token,
            'usuario' => $usuario
        ]);
    }
    /**
     * @OA\Get(
     *     path="/api/me",
     *     summary="Obtener perfil del usuario autenticado",
     *     tags={"Autenticación"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(
     *         response=200,
     *         description="Usuario autenticado",
     *         @OA\JsonContent(ref="#/components/schemas/Usuario")
     *     ),
     *     @OA\Response(response=401, description="Token inválido o no enviado")
     * )
     */

    // PERFIL (token requerido)
    public function me()
    {
        return response()->json(auth()->user());
    }
 /**
     * @OA\Post(
     *     path="/api/logout",
     *     summary="Cerrar sesión",
     *     tags={"Autenticación"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(
     *         response=200,
     *         description="Cierre de sesión exitoso",
     *         @OA\JsonContent(
     *             @OA\Property(property="message", type="string", example="Cierre de sesión exitoso")
     *         )
     *     )
     * )
     */
    // LOGOUT
    public function logout()
    {
        auth()->logout();
        return response()->json(['message' => 'Cierre de sesión exitoso']);
    }
}
