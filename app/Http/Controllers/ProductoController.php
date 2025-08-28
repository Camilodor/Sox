<?php

namespace App\Http\Controllers;

use App\Models\Producto;
use Illuminate\Http\Request;

class ProductoController extends Controller
{/**
     * @OA\Get(
     *     path="/api/productos",
     *     summary="Listar todos los productos",
     *     tags={"Productos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Listado exitoso")
     * )
     */
    public function index()
    {
        $productos = Producto::all();
        return response()->json($productos, 200);
    }

    /**
     * @OA\Post(
     *     path="/api/productos",
     *     summary="Crear un nuevo producto",
     *     tags={"Productos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"proveedores_id","nombre"},
     *             @OA\Property(property="proveedores_id", type="integer", example=1),
     *             @OA\Property(property="nombre", type="string", example="Caja de cartón"),
     *             @OA\Property(property="descripcion", type="string", example="Caja resistente para embalaje")
     *         )
     *     ),
     *     @OA\Response(response=201, description="Producto creado"),
     *     @OA\Response(response=422, description="Errores de validación")
     * )
     */
    public function store(Request $request)
    {
        $request->validate([
            'proveedores_id' => 'required|exists:proveedores,id',
            'nombre' => 'required|string|max:255',
            'descripcion' => 'nullable|string',
        ]);

        $productos = Producto::create($request->all());

        return response()->json([
            'message' => 'Producto creado exitosamente.',
            'producto' => $productos
        ], 201);
    }

    /**
     * @OA\Get(
     *     path="/api/productos/{id}",
     *     summary="Obtener un producto por ID",
     *     tags={"Productos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del producto", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Producto encontrado"),
     *     @OA\Response(response=404, description="Producto no encontrado")
     * )
     */
    public function show($id)
    {
        $productos = Producto::findOrFail($id);
        return response()->json($productos, 200);
    }
 /**
     * @OA\Put(
     *     path="/api/productos/{id}",
     *     summary="Actualizar un producto",
     *     tags={"Productos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del producto", @OA\Schema(type="integer")),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             @OA\Property(property="proveedores_id", type="integer", example=1),
     *             @OA\Property(property="nombre", type="string", example="Caja modificada"),
     *             @OA\Property(property="descripcion", type="string", example="Caja con refuerzos adicionales")
     *         )
     *     ),
     *     @OA\Response(response=200, description="Producto actualizado"),
     *     @OA\Response(response=404, description="Producto no encontrado")
     * )
     */
    public function update(Request $request, $id)
    {
        $productos = Producto::findOrFail($id);
        $productos->update($request->all());

        return response()->json([
            'message' => 'Producto actualizado exitosamente.',
            'producto' => $productos
        ], 200);
    }

    /**
     * @OA\Delete(
     *     path="/api/productos/{id}",
     *     summary="Eliminar un producto",
     *     tags={"Productos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del producto", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Producto eliminado exitosamente"),
     *     @OA\Response(response=404, description="Producto no encontrado")
     * )
     */
    public function destroy($id)
    {
        $productos = Producto::findOrFail($id);
        $productos->delete();

        return response()->json([
            'message' => 'Producto eliminado exitosamente.'
        ], 200);
    }
}
