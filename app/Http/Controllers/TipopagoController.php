<?php

// app/Http/Controllers/TipopagoController.php
namespace App\Http\Controllers;

use App\Models\Tipopago;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class TipopagoController extends Controller
{ /**
     * @OA\Get(
     *     path="/api/tipospago",
     *     summary="Listar todos los tipos de pago",
     *     tags={"Tipos de Pago"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Listado exitoso")
     * )
     */
    public function index()
    {
        $tipospago = Tipopago::all();
        return response()->json($tipospago, 200);
    }

    /**
     * @OA\Post(
     *     path="/api/tipospago",
     *     summary="Crear un nuevo tipo de pago",
     *     tags={"Tipos de Pago"},
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"nombre"},
     *             @OA\Property(property="nombre", type="string", maxLength=50, example="Contado")
     *         )
     *     ),
     *     @OA\Response(response=201, description="Tipo de pago creado"),
     *     @OA\Response(response=400, description="Errores de validación")
     * )
     */
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'nombre' => 'required|string|max:50|unique:tipospago',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $tipopago = Tipopago::create($request->all());

        return response()->json([
            'message' => 'Tipo de pago creado exitosamente',
            'tipopago' => $tipopago
        ], 201);
    }
 /**
     * @OA\Get(
     *     path="/api/tipospago/{id}",
     *     summary="Obtener un tipo de pago por ID",
     *     tags={"Tipos de Pago"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID del tipo de pago",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=200, description="Tipo de pago encontrado"),
     *     @OA\Response(response=404, description="No encontrado")
     * )
     */
    public function show($id)
    {
        $tipopago = Tipopago::find($id);

        if (!$tipopago) {
            return response()->json(['message' => 'Tipo de pago no encontrado'], 404);
        }

        return response()->json($tipopago, 200);
    }
 /**
     * @OA\Put(
     *     path="/api/tipospago/{id}",
     *     summary="Actualizar un tipo de pago",
     *     tags={"Tipos de Pago"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID del tipo de pago",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"nombre"},
     *             @OA\Property(property="nombre", type="string", maxLength=50, example="Crédito")
     *         )
     *     ),
     *     @OA\Response(response=200, description="Actualizado exitosamente"),
     *     @OA\Response(response=404, description="No encontrado"),
     *     @OA\Response(response=400, description="Errores de validación")
     * )
     */
    public function update(Request $request, $id)
    {
        $tipopago = Tipopago::find($id);

        if (!$tipopago) {
            return response()->json(['message' => 'Tipo de pago no encontrado'], 404);
        }

        $validator = Validator::make($request->all(), [
            'nombre' => 'required|string|max:50|unique:tipospago,nombre,' . $id,
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $tipopago->update($request->all());

        return response()->json([
            'message' => 'Tipo de pago actualizado exitosamente',
            'tipopago' => $tipopago
        ], 200);
    }

    /**
     * @OA\Delete(
     *     path="/api/tipospago/{id}",
     *     summary="Eliminar un tipo de pago",
     *     tags={"Tipos de Pago"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID del tipo de pago",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=200, description="Eliminado exitosamente"),
     *     @OA\Response(response=404, description="No encontrado")
     * )
     */
    public function destroy($id)
    {
        $tipopago = Tipopago::find($id);

        if (!$tipopago) {
            return response()->json(['message' => 'Tipo de pago no encontrado'], 404);
        }

        $tipopago->delete();

        return response()->json(['message' => 'Tipo de pago eliminado exitosamente'], 200);
    }
}
