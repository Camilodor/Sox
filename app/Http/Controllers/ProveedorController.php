<?php

namespace App\Http\Controllers;

use App\Models\Proveedor;
use Illuminate\Http\Request;

class ProveedorController extends Controller
{    
    /**
     * @OA\Get(
     *     path="/api/proveedores",
     *     summary="Listar todos los proveedores",
     *     tags={"Proveedores"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Listado exitoso")
     * )
     */
    public function index()
    {
        $proveedores = Proveedor::all();
        return response()->json($proveedores, 200);
    }

    /**
     * @OA\Post(
     *     path="/api/proveedores",
     *     summary="Crear un nuevo proveedor",
     *     tags={"Proveedores"},
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"nombre"},
     *             @OA\Property(property="nombre", type="string", example="Acme S.A."),
     *             @OA\Property(property="descripcion", type="string", example="Proveedor de cajas y embalajes")
     *         )
     *     ),
     *     @OA\Response(response=201, description="Proveedor creado"),
     *     @OA\Response(response=422, description="Errores de validación")
     * )
     */
    public function store(Request $request)
    {
        $request->validate([
            'nombre' => 'required|string|max:255',
            'descripcion' => 'nullable|string',
        ]);

        $proveedores = Proveedor::create($request->all());

        return response()->json([
            'message' => 'Proveedor creado exitosamente.',
            'proveedor' => $proveedores
        ], 201);
    }
 /**
     * @OA\Get(
     *     path="/api/proveedores/{id}",
     *     summary="Obtener un proveedor por ID",
     *     tags={"Proveedores"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del proveedor", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Proveedor encontrado"),
     *     @OA\Response(response=404, description="Proveedor no encontrado")
     * )
     */
    public function show($id)
    {
        $proveedor = Proveedor::findOrFail($id);
return response()->json($proveedor, 200);;
    }
  /**
     * @OA\Put(
     *     path="/api/proveedores/{id}",
     *     summary="Actualizar un proveedor",
     *     tags={"Proveedores"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del proveedor", @OA\Schema(type="integer")),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             @OA\Property(property="nombre", type="string", example="Nuevo proveedor"),
     *             @OA\Property(property="descripcion", type="string", example="Proveedor actualizado")
     *         )
     *     ),
     *     @OA\Response(response=200, description="Proveedor actualizado"),
     *     @OA\Response(response=404, description="Proveedor no encontrado")
     * )
     */
    public function update(Request $request, $id)
    {
        $proveedores = Proveedor::findOrFail($id);
        $proveedores->update($request->all());

        return response()->json([
            'message' => 'Proveedor actualizado exitosamente.',
            'proveedor' => $proveedores
        ], 200);
    }
 /**
     * @OA\Delete(
     *     path="/api/proveedores/{id}",
     *     summary="Eliminar un proveedor",
     *     tags={"Proveedores"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del proveedor", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Proveedor eliminado exitosamente"),
     *     @OA\Response(response=404, description="Proveedor no encontrado")
     * )
     */
    public function destroy($id)
    {
        $proveedores = Proveedor::findOrFail($id);
        $proveedores->delete();

        return response()->json([
            'message' => 'Proveedor eliminado exitosamente.'
        ], 200);
    }
}
