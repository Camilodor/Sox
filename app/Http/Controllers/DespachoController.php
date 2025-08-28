<?php
// app/Http/Controllers/DespachoController.php

namespace App\Http\Controllers;

use App\Models\Despacho;
use App\Models\Seguimiento;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class DespachoController extends Controller
{
    
    /**
     * @OA\Get(
     *     path="/api/despachos",
     *     summary="Listar todos los despachos",
     *     tags={"Despachos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Lista de despachos")
     * )
     */
    // Obtener todos los despachos
    public function index()
    {
        $despachos = Despacho::with(['mercancia', 'vehiculo', 'usuario', 'tipopago'])->get();
        return response()->json($despachos, 200);
    }
     
    /**
     * @OA\Post(
     *     path="/api/despachos",
     *     summary="Crear un nuevo despacho",
     *     tags={"Despachos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"mercancias_id","vehiculos_id","usuarios_id","tipo_pago_id","fecha_despacho","negociacion","anticipo","saldo"},
     *             @OA\Property(property="mercancias_id", type="integer", example=1),
     *             @OA\Property(property="vehiculos_id", type="integer", example=2),
     *             @OA\Property(property="usuarios_id", type="integer", example=3),
     *             @OA\Property(property="tipo_pago_id", type="integer", example=1),
     *             @OA\Property(property="fecha_despacho", type="string", format="date", example="2025-07-09"),
     *             @OA\Property(property="negociacion", type="number", example=200000),
     *             @OA\Property(property="anticipo", type="number", example=50000),
     *             @OA\Property(property="saldo", type="number", example=150000),
     *             @OA\Property(property="observaciones_mer", type="string", example="Sin novedades")
     *         )
     *     ),
     *     @OA\Response(response=201, description="Despacho creado exitosamente"),
     *     @OA\Response(response=400, description="Errores de validación")
     * )
     */
    // Crear un nuevo despacho
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'mercancias_id' => 'required|exists:mercancias,id',
            'vehiculos_id' => 'required|exists:vehiculos,id',
            'usuarios_id' => 'required|exists:usuarios,id',
            'tipo_pago_id' => 'required|exists:tipospago,id',
            'fecha_despacho' => 'required|date',
            'negociacion' => 'required|numeric',
            'anticipo' => 'required|numeric',
            'saldo' => 'required|numeric',
            'observaciones_mer' => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $despachos = Despacho::create($request->all());

        // Crear seguimiento
        Seguimiento::create([
            'mercancias_id' => $despachos->mercancias_id,
            'evento' => 'Despacho creado',
            'estado' => 'Despachado',
            'usuario_id' => $despachos->usuarios_id,
            'despacho_id' => $despachos->id
        ]);

        return response()->json([
            'message' => 'Despacho creado exitosamente',
            'despacho' => $despachos
        ], 201);
    }

    /**
     * @OA\Get(
     *     path="/api/despachos/{id}",
     *     summary="Mostrar un despacho específico",
     *     tags={"Despachos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID del despacho",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=200, description="Despacho encontrado"),
     *     @OA\Response(response=404, description="Despacho no encontrado")
     * )
     */
    // Obtener un despacho por ID
    public function show($id)
    {
        $despachos = Despacho::with(['mercancia', 'vehiculo', 'usuario', 'tipopago'])->find($id);

        if (!$despachos) {
            return response()->json(['message' => 'Despacho no encontrado'], 404);
        }

        return response()->json($despachos, 200);
    }

    /**
     * @OA\Put(
     *     path="/api/despachos/{id}",
     *     summary="Actualizar un despacho",
     *     tags={"Despachos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID del despacho a actualizar",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"mercancias_id","vehiculos_id","usuarios_id","tipo_pago_id","fecha_despacho","negociacion","anticipo","saldo"},
     *             @OA\Property(property="mercancias_id", type="integer", example=1),
     *             @OA\Property(property="vehiculos_id", type="integer", example=2),
     *             @OA\Property(property="usuarios_id", type="integer", example=3),
     *             @OA\Property(property="tipo_pago_id", type="integer", example=1),
     *             @OA\Property(property="fecha_despacho", type="string", format="date", example="2025-07-09"),
     *             @OA\Property(property="negociacion", type="number", example=200000),
     *             @OA\Property(property="anticipo", type="number", example=50000),
     *             @OA\Property(property="saldo", type="number", example=150000),
     *             @OA\Property(property="observaciones_mer", type="string", example="Actualización de despacho")
     *         )
     *     ),
     *     @OA\Response(response=200, description="Despacho actualizado exitosamente"),
     *     @OA\Response(response=400, description="Errores de validación"),
     *     @OA\Response(response=404, description="Despacho no encontrado")
     * )
     */
    // Actualizar un despacho
    public function update(Request $request, $id)
    {
        $validator = Validator::make($request->all(), [
            'mercancias_id' => 'required|exists:mercancias,id',
            'vehiculos_id' => 'required|exists:vehiculos,id',
            'usuarios_id' => 'required|exists:usuarios,id',
            'tipo_pago_id' => 'required|exists:tipospago,id',
            'fecha_despacho' => 'required|date',
            'negociacion' => 'required|numeric',
            'anticipo' => 'required|numeric',
            'saldo' => 'required|numeric',
            'observaciones_mer' => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $despachos = Despacho::find($id);

        if (!$despachos) {
            return response()->json(['message' => 'Despacho no encontrado'], 404);
        }

        $despachos->update($request->all());

        // Crear seguimiento para actualización
        Seguimiento::create([
            'mercancias_id' => $despachos->mercancias_id,
            'evento' => 'Despacho actualizado',
            'estado' => 'En tránsito',
            'usuario_id' => $despachos->usuarios_id,
            'despacho_id' => $despachos->id
        ]);

        return response()->json([
            'message' => 'Despacho actualizado exitosamente',
            'despacho' => $despachos
        ], 200);
    }
    /**
     * @OA\Delete(
     *     path="/api/despachos/{id}",
     *     summary="Eliminar un despacho",
     *     tags={"Despachos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID del despacho a eliminar",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=200, description="Despacho eliminado exitosamente"),
     *     @OA\Response(response=404, description="Despacho no encontrado")
     * )
     */

    // Eliminar un despacho
    public function destroy($id)
    {
        $despachos = Despacho::find($id);

        if (!$despachos) {
            return response()->json(['message' => 'Despacho no encontrado'], 404);
        }

        // Crear seguimiento para eliminación
        Seguimiento::create([
            'mercancias_id' => $despachos->mercancias_id,
            'evento' => 'Despacho eliminado',
            'estado' => 'Eliminado',
            'usuario_id' => $despachos->usuarios_id,
            'despacho_id' => $despachos->id
        ]);

        $despachos->delete();

        return response()->json(['message' => 'Despacho eliminado exitosamente'], 200);
    }
}
