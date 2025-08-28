<?php

namespace App\Http\Controllers;

use App\Models\R_laboral;
use Illuminate\Http\Request;

class R_laboralController extends Controller
{
    /**
     * @OA\Get(
     *     path="/api/r_laborales",
     *     summary="Listar todas las referencias laborales",
     *     tags={"Referencias Laborales"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Listado exitoso")
     * )
     */
    // Obtener todos los registros
    public function index()
    {
        $r_laborales = R_laboral::all();
        return response()->json($r_laborales, 200);
    }

    /**
     * @OA\Post(
     *     path="/api/r_laborales",
     *     summary="Crear una referencia laboral",
     *     tags={"Referencias Laborales"},
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"usuarios_id","nombre","apellido","parentezco","num_documento","tipo_documento_id","num_celular","num_direccion"},
     *             @OA\Property(property="usuarios_id", type="integer", example=1),
     *             @OA\Property(property="nombre", type="string", example="Carlos"),
     *             @OA\Property(property="apellido", type="string", example="Ramírez"),
     *             @OA\Property(property="parentezco", type="string", example="Jefe anterior"),
     *             @OA\Property(property="num_documento", type="string", example="123456789"),
     *             @OA\Property(property="tipo_documento_id", type="integer", example=1),
     *             @OA\Property(property="num_celular", type="string", example="3101234567"),
     *             @OA\Property(property="num_direccion", type="string", example="Cra 10 #20-30")
     *         )
     *     ),
     *     @OA\Response(response=201, description="Referencia laboral creada"),
     *     @OA\Response(response=422, description="Errores de validación")
     * )
     */
    // Crear un nuevo registro
    public function store(Request $request)
    {
        $request->validate([
            'usuarios_id' => 'required|exists:usuarios,id',
            'nombre' => 'required|string|max:255',
            'apellido' => 'required|string|max:255',
            'parentezco' => 'required|string|max:255',
            'num_documento' => 'required|string|max:20',
            'tipo_documento_id' => 'required|exists:tipos_documento,id',
            'num_celular' => 'required|string|max:20',
            'num_direccion' => 'required|string|max:255',
        ]);

        $r_laborales = R_laboral::create($request->all());

        return response()->json([
            'message' => 'Referencia laboral creada exitosamente.',
            'r_laboral' => $r_laborales
        ], 201);
    }
/**
     * @OA\Get(
     *     path="/api/r_laborales/{id}",
     *     summary="Obtener una referencia laboral por ID",
     *     tags={"Referencias Laborales"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID de la referencia laboral", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Referencia laboral encontrada"),
     *     @OA\Response(response=404, description="Referencia laboral no encontrada")
     * )
     */
    // Obtener un solo registro por ID
    public function show($id)
    {
        $r_laborales = R_laboral::findOrFail($id);
        return response()->json($r_laborales, 200);
    }
 /**
     * @OA\Put(
     *     path="/api/r_laborales/{id}",
     *     summary="Actualizar una referencia laboral",
     *     tags={"Referencias Laborales"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID de la referencia laboral", @OA\Schema(type="integer")),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             @OA\Property(property="nombre", type="string", example="Nombre actualizado"),
     *             @OA\Property(property="apellido", type="string", example="Apellido actualizado"),
     *             @OA\Property(property="parentezco", type="string", example="Compañero"),
     *             @OA\Property(property="num_documento", type="string", example="999999999"),
     *             @OA\Property(property="tipo_documento_id", type="integer", example=2),
     *             @OA\Property(property="num_celular", type="string", example="3200000000"),
     *             @OA\Property(property="num_direccion", type="string", example="Av 30 #50-40")
     *         )
     *     ),
     *     @OA\Response(response=200, description="Referencia laboral actualizada"),
     *     @OA\Response(response=404, description="Referencia laboral no encontrada")
     * )
     */
    // Actualizar un registro por ID
    public function update(Request $request, $id)
    {
        $r_laborales = R_laboral::findOrFail($id);
        $r_laborales->update($request->all());

        return response()->json([
            'message' => 'Referencia laboral actualizada exitosamente.',
            'r_laboral' => $r_laborales
        ], 200);
    }
 /**
     * @OA\Delete(
     *     path="/api/r_laborales/{id}",
     *     summary="Eliminar una referencia laboral",
     *     tags={"Referencias Laborales"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID de la referencia laboral", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Referencia laboral eliminada exitosamente"),
     *     @OA\Response(response=404, description="Referencia laboral no encontrada")
     * )
     */
    // Eliminar un registro por ID
    public function destroy($id)
    {
        $r_laborales = R_laboral::findOrFail($id);
        $r_laborales->delete();

        return response()->json([
            'message' => 'Referencia laboral eliminada exitosamente.'
        ], 200);
    }
}
