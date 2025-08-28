<?php

namespace App\Http\Controllers;

use App\Models\Devolucion;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class DevolucionController extends Controller
{
    
    /**
     * @OA\Get(
     *     path="/api/devoluciones",
     *     summary="Listar todas las devoluciones",
     *     tags={"Devoluciones"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Lista de devoluciones")
     * )
     */
    // ✅ Listar devoluciones
    public function index()
    {
        $devoluciones = Devolucion::with(['mercancia', 'usuario', 'proveedor'])->get();
        return response()->json($devoluciones, 200);
    }

    /**
     * @OA\Post(
     *     path="/api/devoluciones",
     *     summary="Crear una nueva devolución",
     *     tags={"Devoluciones"},
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"mercancias_id","usuarios_id","proveedores_id","fecha_devolucion","motivo_devolucion","estado_devolucion"},
     *             @OA\Property(property="mercancias_id", type="integer", example=1),
     *             @OA\Property(property="usuarios_id", type="integer", example=2),
     *             @OA\Property(property="proveedores_id", type="integer", example=3),
     *             @OA\Property(property="fecha_devolucion", type="string", format="date", example="2025-07-09"),
     *             @OA\Property(property="motivo_devolucion", type="string", example="Producto en mal estado"),
     *             @OA\Property(property="estado_devolucion", type="string", example="Pendiente"),
     *             @OA\Property(property="observaciones", type="string", example="Ninguna")
     *         )
     *     ),
     *     @OA\Response(response=201, description="Devolución creada exitosamente"),
     *     @OA\Response(response=422, description="Errores de validación")
     * )
     */
    // ✅ Crear devolución
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'mercancias_id'      => 'required|exists:mercancias,id',
            'usuarios_id'        => 'required|exists:usuarios,id',
            'proveedores_id'     => 'required|exists:proveedores,id',
            'fecha_devolucion'   => 'required|date',
            'motivo_devolucion'  => 'required|string|max:255',
            'estado_devolucion'  => 'required|string|max:100',
            'observaciones'      => 'nullable|string|max:500',
        ]);

        if ($validator->fails()) {
            return response()->json([
                'message' => 'Errores de validación',
                'errors'  => $validator->errors()
            ], 422);
        }

        $devolucion = Devolucion::create($request->all());

        return response()->json([
            'message'    => 'Devolución creada exitosamente',
            'devolucion' => $devolucion
        ], 201);
    }

    /**
     * @OA\Get(
     *     path="/api/devoluciones/{id}",
     *     summary="Obtener detalles de una devolución por ID",
     *     tags={"Devoluciones"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id", in="path", required=true, description="ID de la devolución",
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=200, description="Devolución encontrada"),
     *     @OA\Response(response=404, description="Devolución no encontrada")
     * )
     */
    // ✅ Mostrar devolución
    public function show($id)
    {
        $devolucion = Devolucion::with(['mercancia', 'usuario', 'proveedor'])->find($id);

        if (!$devolucion) {
            return response()->json(['message' => 'Devolución no encontrada'], 404);
        }

        return response()->json($devolucion, 200);
    }

    /**
     * @OA\Put(
     *     path="/api/devoluciones/{id}",
     *     summary="Actualizar una devolución completamente",
     *     tags={"Devoluciones"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id", in="path", required=true, description="ID de la devolución a actualizar",
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"mercancias_id","usuarios_id","proveedores_id","fecha_devolucion","motivo_devolucion","estado_devolucion"},
     *             @OA\Property(property="mercancias_id", type="integer", example=1),
     *             @OA\Property(property="usuarios_id", type="integer", example=2),
     *             @OA\Property(property="proveedores_id", type="integer", example=3),
     *             @OA\Property(property="fecha_devolucion", type="string", format="date", example="2025-07-09"),
     *             @OA\Property(property="motivo_devolucion", type="string", example="Producto defectuoso"),
     *             @OA\Property(property="estado_devolucion", type="string", example="Aceptada"),
     *             @OA\Property(property="observaciones", type="string", example="Requiere reenvío")
     *         )
     *     ),
     *     @OA\Response(response=200, description="Devolución actualizada exitosamente"),
     *     @OA\Response(response=422, description="Errores de validación"),
     *     @OA\Response(response=404, description="Devolución no encontrada")
     * )
     */
    // ✅ Actualizar devolución (PUT completo)
    public function update(Request $request, $id)
    {
        $devolucion = Devolucion::find($id);

        if (!$devolucion) {
            return response()->json(['message' => 'Devolución no encontrada'], 404);
        }

        $validator = Validator::make($request->all(), [
            'mercancias_id'      => 'required|exists:mercancias,id',
            'usuarios_id'        => 'required|exists:usuarios,id',
            'proveedores_id'     => 'required|exists:proveedores,id',
            'fecha_devolucion'   => 'required|date',
            'motivo_devolucion'  => 'required|string|max:255',
            'estado_devolucion'  => 'required|string|max:100',
            'observaciones'      => 'nullable|string|max:500',
        ]);

        if ($validator->fails()) {
            return response()->json([
                'message' => 'Errores de validación',
                'errors'  => $validator->errors()
            ], 422);
        }

        $devolucion->update($request->all());

        return response()->json([
            'message'    => 'Devolución actualizada exitosamente',
            'devolucion' => $devolucion
        ], 200);
    }

    /**
     * @OA\Delete(
     *     path="/api/devoluciones/{id}",
     *     summary="Eliminar una devolución",
     *     tags={"Devoluciones"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id", in="path", required=true, description="ID de la devolución a eliminar",
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=200, description="Devolución eliminada exitosamente"),
     *     @OA\Response(response=404, description="Devolución no encontrada")
     * )
     */
    // ✅ Eliminar devolución
    public function destroy($id)
    {
        $devolucion = Devolucion::find($id);

        if (!$devolucion) {
            return response()->json(['message' => 'Devolución no encontrada'], 404);
        }

        $devolucion->delete();

        return response()->json([
            'message' => 'Devolución eliminada exitosamente'
        ], 200);
    }

    /**
     * @OA\Patch(
     *     path="/api/devoluciones/{id}",
     *     summary="Actualizar parcialmente una devolución",
     *     tags={"Devoluciones"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id", in="path", required=true, description="ID de la devolución",
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(
     *         @OA\JsonContent(
     *             @OA\Property(property="motivo_devolucion", type="string", example="Motivo corregido"),
     *             @OA\Property(property="estado_devolucion", type="string", example="Revisado")
     *         )
     *     ),
     *     @OA\Response(response=200, description="Devolución actualizada parcialmente"),
     *     @OA\Response(response=422, description="Errores de validación"),
     *     @OA\Response(response=404, description="Devolución no encontrada")
     * )
     */
    // ✅ Actualizar parcial (PATCH)
    public function patch(Request $request, $id)
    {
        $devolucion = Devolucion::find($id);

        if (!$devolucion) {
            return response()->json(['message' => 'Devolución no encontrada'], 404);
        }

        $rules = [
            'mercancias_id'      => 'sometimes|exists:mercancias,id',
            'usuarios_id'        => 'sometimes|exists:usuarios,id',
            'proveedores_id'     => 'sometimes|exists:proveedores,id',
            'fecha_devolucion'   => 'sometimes|date',
            'motivo_devolucion'  => 'sometimes|string|max:255',
            'estado_devolucion'  => 'sometimes|string|max:100',
            'observaciones'      => 'sometimes|nullable|string|max:500',
        ];

        $validator = Validator::make($request->all(), $rules);

        if ($validator->fails()) {
            return response()->json([
                'message' => 'Errores de validación',
                'errors'  => $validator->errors()
            ], 422);
        }

        $devolucion->update($request->only(array_keys($rules)));

        return response()->json([
            'message'    => 'Devolución actualizada parcialmente',
            'devolucion' => $devolucion->fresh()
        ], 200);
    }
}
