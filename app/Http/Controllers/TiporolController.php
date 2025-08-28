<?php

namespace App\Http\Controllers;

use App\Models\Tiporol;
use Illuminate\Http\Request;

class TiporolController extends Controller
{
    /**
     * @OA\Get(
     *     path="/api/tiposrol",
     *     summary="Listar todos los tipos de rol",
     *     tags={"Tipos de Rol"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Listado exitoso")
     * )
     */
    public function index()
    {
        $tiposrol = Tiporol::all();
        return response()->json($tiposrol, 200);
    }
/**
     * @OA\Post(
     *     path="/api/tiposrol",
     *     summary="Crear un nuevo tipo de rol",
     *     tags={"Tipos de Rol"},
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"nombre"},
     *             @OA\Property(property="nombre", type="string", maxLength=50, example="Administrador")
     *         )
     *     ),
     *     @OA\Response(response=201, description="Tipo de rol creado exitosamente"),
     *     @OA\Response(response=422, description="Errores de validación")
     * )
     */
    public function store(Request $request)
    {
        $request->validate([
            'nombre' => 'required|string|max:50',
        ]);

        $tiporol = Tiporol::create($request->all());

        return response()->json([
            'message' => 'Tipo de rol creado exitosamente',
            'tiporol' => $tiporol
        ], 201);
    }

    /**
     * @OA\Get(
     *     path="/api/tiposrol/{id}",
     *     summary="Obtener tipo de rol por ID",
     *     tags={"Tipos de Rol"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         required=true,
     *         description="ID del tipo de rol",
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=200, description="Tipo de rol encontrado"),
     *     @OA\Response(response=404, description="Tipo de rol no encontrado")
     * )
     */
    public function show($id)
    {
        $tiporol = Tiporol::find($id);

        if (!$tiporol) {
            return response()->json(['message' => 'Tipo de rol no encontrado'], 404);
        }

        return response()->json($tiporol, 200);
    }
 /**
     * @OA\Put(
     *     path="/api/tiposrol/{id}",
     *     summary="Actualizar tipo de rol",
     *     tags={"Tipos de Rol"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         required=true,
     *         description="ID del tipo de rol",
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"nombre"},
     *             @OA\Property(property="nombre", type="string", maxLength=50, example="Bodeguero")
     *         )
     *     ),
     *     @OA\Response(response=200, description="Actualizado exitosamente"),
     *     @OA\Response(response=404, description="No encontrado"),
     *     @OA\Response(response=422, description="Errores de validación")
     * )
     */
    public function update(Request $request, $id)
    {
        $tiporol = Tiporol::find($id);

        if (!$tiporol) {
            return response()->json(['message' => 'Tipo de rol no encontrado'], 404);
        }

        $request->validate([
            'nombre' => 'required|string|max:50',
        ]);

        $tiporol->update($request->all());

        return response()->json([
            'message' => 'Tipo de rol actualizado exitosamente',
            'tiporol' => $tiporol
        ], 200);
    }
/**
     * @OA\Delete(
     *     path="/api/tiposrol/{id}",
     *     summary="Eliminar tipo de rol",
     *     tags={"Tipos de Rol"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         required=true,
     *         description="ID del tipo de rol",
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=200, description="Tipo de rol eliminado"),
     *     @OA\Response(response=404, description="Tipo de rol no encontrado")
     * )
     */
    public function destroy($id)
    {
        $tiporol = Tiporol::find($id);

        if (!$tiporol) {
            return response()->json(['message' => 'Tipo de rol no encontrado'], 404);
        }

        $tiporol->delete();

        return response()->json([
            'message' => 'Tipo de rol eliminado exitosamente'
        ], 200);
    }
}
