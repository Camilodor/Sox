<?php

namespace App\Http\Controllers;

use App\Models\R_personal;
use Illuminate\Http\Request;

class R_personalController extends Controller
{  /**
     * @OA\Get(
     *     path="/api/r_personales",
     *     summary="Listar todas las referencias personales",
     *     tags={"Referencias Personales"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Listado exitoso")
     * )
     */
    // Obtener todos los registros
    public function index()
    {
        $r_personales = R_personal::all();
        return response()->json($r_personales, 200);
    }

    /**
     * @OA\Post(
     *     path="/api/r_personales",
     *     summary="Crear una referencia personal",
     *     tags={"Referencias Personales"},
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"usuarios_id","nombre","apellido","parentezco","num_documento","tipo_documento_id","num_celular","num_direccion"},
     *             @OA\Property(property="usuarios_id", type="integer", example=1),
     *             @OA\Property(property="nombre", type="string", example="Camila"),
     *             @OA\Property(property="apellido", type="string", example="Díaz"),
     *             @OA\Property(property="parentezco", type="string", example="Amiga"),
     *             @OA\Property(property="num_documento", type="string", example="1020304050"),
     *             @OA\Property(property="tipo_documento_id", type="integer", example=1),
     *             @OA\Property(property="num_celular", type="string", example="3107654321"),
     *             @OA\Property(property="num_direccion", type="string", example="Cra 15 # 20-10")
     *         )
     *     ),
     *     @OA\Response(response=201, description="Referencia personal creada"),
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

        $r_personales = R_personal::create($request->all());

        return response()->json([
            'message' => 'Referencia personal creada exitosamente.',
            'r_personal' => $r_personales
        ], 201);
    }
 /**
     * @OA\Get(
     *     path="/api/r_personales/{id}",
     *     summary="Obtener una referencia personal por ID",
     *     tags={"Referencias Personales"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID de la referencia personal", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Referencia encontrada"),
     *     @OA\Response(response=404, description="Referencia no encontrada")
     * )
     */
    // Obtener un solo registro por ID
    public function show($id)
    {
        $r_personales = R_personal::findOrFail($id);
        return response()->json($r_personales, 200);
    }

    /**
     * @OA\Put(
     *     path="/api/r_personales/{id}",
     *     summary="Actualizar una referencia personal",
     *     tags={"Referencias Personales"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID de la referencia", @OA\Schema(type="integer")),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             @OA\Property(property="usuarios_id", type="integer", example=1),
     *             @OA\Property(property="nombre", type="string", example="Camila"),
     *             @OA\Property(property="apellido", type="string", example="Díaz"),
     *             @OA\Property(property="parentezco", type="string", example="Amiga"),
     *             @OA\Property(property="num_documento", type="string", example="1020304050"),
     *             @OA\Property(property="tipo_documento_id", type="integer", example=1),
     *             @OA\Property(property="num_celular", type="string", example="3107654321"),
     *             @OA\Property(property="num_direccion", type="string", example="Cra 15 # 20-10")
     *         )
     *     ),
     *     @OA\Response(response=200, description="Referencia actualizada"),
     *     @OA\Response(response=404, description="Referencia no encontrada")
     * )
     */
    // Actualizar un registro por ID
    public function update(Request $request, $id)
    {
        $r_personales = R_personal::findOrFail($id);
        
        if (!$r_personales) {
            return response()->json(['message' => 'Referencia no encontrada'], 404);
        }

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
        $r_personales->update($request->all());

        return response()->json([
            'message' => 'Referencia personal actualizada exitosamente.',
            'r_personal' => $r_personales
        ], 200);
    }

    /**
     * @OA\Delete(
     *     path="/api/r_personales/{id}",
     *     summary="Eliminar una referencia personal",
     *     tags={"Referencias Personales"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID de la referencia", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Referencia eliminada"),
     *     @OA\Response(response=404, description="Referencia no encontrada")
     * )
     */
    // Eliminar un registro por ID
    public function destroy($id)
    {
        $R_personales = R_personal::findOrFail($id);
        $R_personales->delete();

        return response()->json([
            'message' => 'Referencia personal eliminada exitosamente.'
        ], 200);
    }
}
