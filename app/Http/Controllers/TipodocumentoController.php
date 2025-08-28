<?php
namespace App\Http\Controllers;

use App\Models\Tipodocumento;
use Illuminate\Http\Request;

class TipodocumentoController extends Controller
{ 
    /**
     * @OA\Get(
     *     path="/api/tipodocumentos",
     *     summary="Listar todos los tipos de documento",
     *     tags={"Tipos Documento"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Listado exitoso")
     * )
     */
    public function index()
    {
        return response()->json(Tipodocumento::all(), 200);
    }
/**
     * @OA\Post(
     *     path="/api/tipodocumentos",
     *     summary="Crear un nuevo tipo de documento",
     *     tags={"Tipos Documento"},
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"nombre"},
     *             @OA\Property(property="nombre", type="string", maxLength=50, example="Cédula de Ciudadanía")
     *         )
     *     ),
     *     @OA\Response(response=201, description="Tipo de documento creado"),
     *     @OA\Response(response=422, description="Errores de validación")
     * )
     */
    public function store(Request $request)
    {
        $request->validate([
            'nombre' => 'required|string|max:50',
        ]);
        $tipodocumento = Tipodocumento::create($request->all());
        return response()->json([
            'message' => 'Tipo de documento creado exitosamente',
            'tipodocumento' => $tipodocumento
        ], 201);
    }

    /**
     * @OA\Get(
     *     path="/api/tipodocumentos/{id}",
     *     summary="Obtener un tipo de documento por ID",
     *     tags={"Tipos Documento"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del tipo de documento", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Tipo de documento encontrado"),
     *     @OA\Response(response=404, description="No encontrado")
     * )
     */
    public function show($id)
    {
        $tipodocumento = Tipodocumento::find($id);
        if (!$tipodocumento) {
            return response()->json(['message' => 'No encontrado'], 404);
        }
        return response()->json($tipodocumento, 200);
    }

    /**
     * @OA\Put(
     *     path="/api/tipodocumentos/{id}",
     *     summary="Actualizar un tipo de documento",
     *     tags={"Tipos Documento"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del tipo de documento", @OA\Schema(type="integer")),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"nombre"},
     *             @OA\Property(property="nombre", type="string", maxLength=50, example="Pasaporte")
     *         )
     *     ),
     *     @OA\Response(response=200, description="Actualizado con éxito"),
     *     @OA\Response(response=404, description="No encontrado")
     * )
     */
    public function update(Request $request, $id)
    {
        $tipodocumento = Tipodocumento::find($id);
        if (!$tipodocumento) {
            return response()->json(['message' => 'No encontrado'], 404);
        }
        $request->validate([
            'nombre' => 'required|string|max:50',
        ]);
        $tipodocumento->update($request->all());
        return response()->json([
            'message' => 'Actualizado con éxito',
            'tipodocumento' => $tipodocumento
        ], 200);
    }
/**
     * @OA\Delete(
     *     path="/api/tipodocumentos/{id}",
     *     summary="Eliminar un tipo de documento",
     *     tags={"Tipos Documento"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID del tipo de documento", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Eliminado con éxito"),
     *     @OA\Response(response=404, description="No encontrado")
     * )
     */
    public function destroy($id)
    {
        $tipodocumento = Tipodocumento::find($id);
        if (!$tipodocumento) {
            return response()->json(['message' => 'No encontrado'], 404);
        }
        $tipodocumento->delete();
        return response()->json(['message' => 'Eliminado con éxito'], 200);
    }
}
