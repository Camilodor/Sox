<?php

// app/Http/Controllers/EntregaController.php

namespace App\Http\Controllers;

use App\Models\Entrega;
use App\Models\Seguimiento;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class EntregaController extends Controller
{ /**
     * @OA\Get(
     *     path="/api/entregas",
     *     summary="Obtener todas las entregas",
     *     tags={"Entregas"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Lista de entregas")
     * )
     */
    // Obtener todas las entregas
    public function index()
    {
        $entregas = Entrega::with(['mercancia', 'usuario', 'despacho'])->get();
        return response()->json($entregas, 200);
    }

    /**
     * @OA\Post(
     *     path="/api/entregas",
     *     summary="Registrar una nueva entrega",
     *     tags={"Entregas"},
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"mercancias_id","despacho_id","usuario_id","nombre_recibe","num_celular_recibe","fecha_entrega","estado_entrega"},
     *             @OA\Property(property="mercancias_id", type="integer", example=1),
     *             @OA\Property(property="despacho_id", type="integer", example=2),
     *             @OA\Property(property="usuario_id", type="integer", example=3),
     *             @OA\Property(property="nombre_recibe", type="string", example="Juan Pérez"),
     *             @OA\Property(property="num_celular_recibe", type="string", example="3201234567"),
     *             @OA\Property(property="fecha_entrega", type="string", format="date", example="2025-07-09"),
     *             @OA\Property(property="estado_entrega", type="string", example="Entregado"),
     *             @OA\Property(property="observaciones", type="string", example="Entrega sin novedades")
     *         )
     *     ),
     *     @OA\Response(response=201, description="Entrega registrada exitosamente"),
     *     @OA\Response(response=400, description="Errores de validación")
     * )
     */
    // Crear una nueva entrega
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'mercancias_id'       => 'required|exists:mercancias,id',
            'despacho_id'         => 'required|exists:despachos,id',   // cambiado
            'usuario_id'          => 'required|exists:usuarios,id',    // cambiado
            'nombre_recibe'       => 'required|string',
            'num_celular_recibe'  => 'required|string',
            'fecha_entrega'       => 'required|date',
            'estado_entrega'      => 'required|string',
            'observaciones'       => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $entregas = Entrega::create($request->all());

        // Crear seguimiento
        Seguimiento::create([
            'mercancias_id' => $entregas->mercancias_id,
            'evento' => 'Mercancía entregada',
            'estado' => 'Entregada',
            'usuario_id' => $entregas->usuario_id,
            'entrega_id' => $entregas->id
        ]);

        return response()->json([
            'message' => 'Entrega registrada exitosamente',
            'entrega' => $entregas
        ], 201);
    }
 /**
     * @OA\Get(
     *     path="/api/entregas/{id}",
     *     summary="Obtener entrega por ID",
     *     tags={"Entregas"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID de la entrega", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Entrega encontrada"),
     *     @OA\Response(response=404, description="Entrega no encontrada")
     * )
     */
    // Obtener una entrega específica por ID
    public function show($id)
    {
        $entregas = Entrega::with(['mercancia', 'usuario','despacho'])->find($id);

        if (!$entregas) {
            return response()->json(['message' => 'Entrega no encontrada'], 404);
        }

        return response()->json($entregas, 200);
    }

    /**
     * @OA\Put(
     *     path="/api/entregas/{id}",
     *     summary="Actualizar una entrega",
     *     tags={"Entregas"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID de la entrega", @OA\Schema(type="integer")),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"mercancias_id","despacho_id","usuario_id","nombre_recibe","num_celular_recibe","fecha_entrega","estado_entrega"},
     *             @OA\Property(property="mercancias_id", type="integer", example=1),
     *             @OA\Property(property="despacho_id", type="integer", example=2),
     *             @OA\Property(property="usuario_id", type="integer", example=3),
     *             @OA\Property(property="nombre_recibe", type="string", example="Ana Gómez"),
     *             @OA\Property(property="num_celular_recibe", type="string", example="3007654321"),
     *             @OA\Property(property="fecha_entrega", type="string", format="date", example="2025-07-10"),
     *             @OA\Property(property="estado_entrega", type="string", example="Entregado"),
     *             @OA\Property(property="observaciones", type="string", example="Observación adicional")
     *         )
     *     ),
     *     @OA\Response(response=200, description="Entrega actualizada"),
     *     @OA\Response(response=400, description="Errores de validación"),
     *     @OA\Response(response=404, description="Entrega no encontrada")
     * )
     */
    // Actualizar una entrega
    public function update(Request $request, $id)
    {
        $validator = Validator::make($request->all(), [
            'mercancias_id'       => 'required|exists:mercancias,id',
            'despacho_id'         => 'required|exists:despachos,id',   // cambiado
            'usuario_id'          => 'required|exists:usuarios,id',    // cambiado
            'nombre_recibe'       => 'required|string',
            'num_celular_recibe'  => 'required|string',
            'fecha_entrega'       => 'required|date',
            'estado_entrega'      => 'required|string',
            'observaciones'       => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $entregas = Entrega::find($id);

        if (!$entregas) {
            return response()->json(['message' => 'Entrega no encontrada'], 404);
        }

        $entregas->update($request->all());

        // Crear seguimiento para la actualización
        Seguimiento::create([
            'mercancias_id' => $entregas->mercancias_id,
            'evento' => 'Entrega actualizada',
            'estado' => 'Entregada',
            'usuario_id' => $entregas->usuario_id,
            'entrega_id' => $entregas->id
        ]);

        return response()->json([
            'message' => 'Entrega actualizada exitosamente',
            'entrega' => $entregas
        ], 200);
    }

    /**
     * @OA\Delete(
     *     path="/api/entregas/{id}",
     *     summary="Eliminar una entrega",
     *     tags={"Entregas"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID de la entrega", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Entrega eliminada exitosamente"),
     *     @OA\Response(response=404, description="Entrega no encontrada")
     * )
     */
    // Eliminar una entrega
    public function destroy($id)
    {
        $entregas = Entrega::find($id);

        if (!$entregas) {
            return response()->json(['message' => 'Entrega no encontrada'], 404);
        }

        // Crear seguimiento para la eliminación
        Seguimiento::create([
            'mercancias_id' => $entregas->mercancias_id,
            'evento' => 'Entrega eliminada',
            'estado' => 'Eliminada',
            'usuario_id' => $entregas->usuario_id,
            'entrega_id' => $entregas->id
        ]);

        $entregas->delete();

        return response()->json(['message' => 'Entrega eliminada exitosamente'], 200);
    }
}

