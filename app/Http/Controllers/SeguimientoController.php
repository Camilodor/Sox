<?php

namespace App\Http\Controllers;

use App\Models\Seguimiento;
use Illuminate\Http\Request;

class SeguimientoController extends Controller
{  
    /**
     * @OA\Get(
     *     path="/api/seguimientos",
     *     summary="Listar todos los seguimientos",
     *     tags={"Seguimientos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Listado exitoso")
     * )
     */
    // Obtener todos los seguimientos
    public function index()
    {
        $seguimientos = Seguimiento::with([
            'mercancia:id,num_remesa,origen_mer,destino_mer,peso,unidades',
            'despacho:id,fecha_despacho,vehiculos_id',
            'entrega:id,fecha_entrega,nombre_recibe',
            'devolucion:id,fecha_devolucion,estado_devolucion',
            'usuario:id,nombre_usuario'
        ])->get();

        return response()->json($seguimientos, 200);
    }
/**
     * @OA\Get(
     *     path="/api/seguimientos/{id}",
     *     summary="Obtener un seguimiento por ID",
     *     tags={"Seguimientos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del seguimiento", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Seguimiento encontrado"),
     *     @OA\Response(response=404, description="Seguimiento no encontrado")
     * )
     */
    // Obtener un seguimiento específico por ID
    public function show($id)
    {
        $seguimientos = Seguimiento::with([
            'mercancia:id,num_remesa,origen_mer,destino_mer,peso,unidades',
            'despacho:id,fecha_despacho,vehiculos_id',
            'entrega:id,fecha_entrega,nombre_recibe',
            'devolucion:id,fecha_devolucion,motivo',
            'usuario:id,nombre_usuario'
        ])->find($id);

        if (!$seguimientos) {
            return response()->json(['message' => 'Seguimiento no encontrado'], 404);
        }

        return response()->json($seguimientos, 200);
    }
  /**
     * @OA\Post(
     *     path="/api/seguimientos",
     *     summary="Crear un nuevo seguimiento",
     *     tags={"Seguimientos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"mercancias_id", "evento", "estado", "usuario_id"},
     *             @OA\Property(property="mercancias_id", type="integer", example=1),
     *             @OA\Property(property="evento", type="string", example="Mercancía entregada"),
     *             @OA\Property(property="estado", type="string", example="Entregada"),
     *             @OA\Property(property="usuario_id", type="integer", example=3),
     *             @OA\Property(property="despacho_id", type="integer", nullable=true, example=2),
     *             @OA\Property(property="entrega_id", type="integer", nullable=true, example=5),
     *             @OA\Property(property="devolucion_id", type="integer", nullable=true, example=null)
     *         )
     *     ),
     *     @OA\Response(response=201, description="Seguimiento creado exitosamente"),
     *     @OA\Response(response=422, description="Errores de validación")
     * )
     */
    // Crear un seguimiento
    public function store(Request $request)
    {
        $request->validate([
            'mercancias_id' => 'required|exists:mercancias,id',
            'evento'        => 'required|string',
            'estado'        => 'required|string',
            'usuario_id'    => 'required|exists:usuarios,id',
            'despacho_id'   => 'nullable|exists:despachos,id',
            'entrega_id'    => 'nullable|exists:entregas,id',
            'devolucion_id' => 'nullable|exists:devoluciones,id',
        ]);

        $seguimientos = Seguimiento::create($request->all());

        return response()->json([
            'message'     => 'Seguimiento creado exitosamente',
            'seguimiento' => $seguimientos
        ], 201);
    }
 /**
     * @OA\Put(
     *     path="/api/seguimientos/{id}",
     *     summary="Actualizar un seguimiento",
     *     tags={"Seguimientos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del seguimiento", @OA\Schema(type="integer")),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             @OA\Property(property="mercancias_id", type="integer", example=1),
     *             @OA\Property(property="evento", type="string", example="Actualización de entrega"),
     *             @OA\Property(property="estado", type="string", example="Entregada"),
     *             @OA\Property(property="usuario_id", type="integer", example=3),
     *             @OA\Property(property="despacho_id", type="integer", nullable=true, example=2),
     *             @OA\Property(property="entrega_id", type="integer", nullable=true, example=5),
     *             @OA\Property(property="devolucion_id", type="integer", nullable=true, example=null)
     *         )
     *     ),
     *     @OA\Response(response=200, description="Seguimiento actualizado"),
     *     @OA\Response(response=404, description="Seguimiento no encontrado")
     * )
     */
    // Actualizar un seguimiento
    public function update(Request $request, $id)
    {
        $request->validate([
            'mercancias_id' => 'required|exists:mercancias,id',
            'evento'        => 'required|string',
            'estado'        => 'required|string',
            'usuario_id'    => 'required|exists:usuarios,id',
            'despacho_id'   => 'nullable|exists:despachos,id',
            'entrega_id'    => 'nullable|exists:entregas,id',
            'devolucion_id' => 'nullable|exists:devoluciones,id',
        ]);

        $seguimientos = Seguimiento::find($id);

        if (!$seguimientos) {
            return response()->json(['message' => 'Seguimiento no encontrado'], 404);
        }

        $seguimientos->update($request->all());

        return response()->json([
            'message'     => 'Seguimiento actualizado exitosamente',
            'seguimiento' => $seguimientos
        ], 200);
    }

    /**
     * @OA\Delete(
     *     path="/api/seguimientos/{id}",
     *     summary="Eliminar un seguimiento",
     *     tags={"Seguimientos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del seguimiento", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Seguimiento eliminado"),
     *     @OA\Response(response=404, description="Seguimiento no encontrado")
     * )
     */
    // Eliminar un seguimiento
    public function destroy($id)
    {
        $seguimientos = Seguimiento::find($id);

        if (!$seguimientos) {
            return response()->json(['message' => 'Seguimiento no encontrado'], 404);
        }

        $seguimientos->delete();

        return response()->json(['message' => 'Seguimiento eliminado exitosamente'], 200);
    }
}
