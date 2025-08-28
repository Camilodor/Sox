<?php
// app/Http/Controllers/MercanciaController.php

namespace App\Http\Controllers;

use App\Models\Mercancia;
use App\Models\Seguimiento;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class MercanciaController extends Controller
{ 
    /**
     * @OA\Get(
     *     path="/api/mercancias",
     *     summary="Listar todas las mercancías",
     *     tags={"Mercancías"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Listado exitoso")
     * )
     */
    // Obtener todas las mercancías
    public function index()
    {
        $mercancias = Mercancia::all();
        return response()->json($mercancias, 200);
    }

    /**
     * @OA\Post(
     *     path="/api/mercancias",
     *     summary="Registrar nueva mercancía",
     *     tags={"Mercancías"},
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"proveedor_id","usuario_id","fecha_ingreso","num_remesa","origen_mer","destino_mer","nom_remitente","doc_remitente","direccion_remitente","cel_remitente","destin_nom","destin_doc","destin_direccion","destin_celular","val_declarado","val_flete","val_total","peso","unidades","tipopago_id"},
     *             @OA\Property(property="proveedor_id", type="integer", example=1),
     *             @OA\Property(property="usuario_id", type="integer", example=2),
     *             @OA\Property(property="fecha_ingreso", type="string", format="date", example="2025-07-09"),
     *             @OA\Property(property="num_remesa", type="string", example="RM-9999"),
     *             @OA\Property(property="origen_mer", type="string", example="Bogotá"),
     *             @OA\Property(property="destino_mer", type="string", example="Medellín"),
     *             @OA\Property(property="nom_remitente", type="string", example="Juan Pérez"),
     *             @OA\Property(property="doc_remitente", type="string", example="123456789"),
     *             @OA\Property(property="direccion_remitente", type="string", example="Calle 123"),
     *             @OA\Property(property="cel_remitente", type="string", example="3001234567"),
     *             @OA\Property(property="destin_nom", type="string", example="Ana Gómez"),
     *             @OA\Property(property="destin_doc", type="string", example="987654321"),
     *             @OA\Property(property="destin_direccion", type="string", example="Carrera 45"),
     *             @OA\Property(property="destin_celular", type="string", example="3207654321"),
     *             @OA\Property(property="val_declarado", type="number", format="float", example=150000),
     *             @OA\Property(property="val_flete", type="number", format="float", example=20000),
     *             @OA\Property(property="val_total", type="number", format="float", example=170000),
     *             @OA\Property(property="peso", type="number", example=30.5),
     *             @OA\Property(property="unidades", type="number", example=10),
     *             @OA\Property(property="observaciones", type="string", example="Frágil"),
     *             @OA\Property(property="tipopago_id", type="integer", example=1)
     *         )
     *     ),
     *     @OA\Response(response=201, description="Mercancía registrada"),
     *     @OA\Response(response=400, description="Errores de validación")
     * )
     */
    // Crear una nueva mercancía
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'proveedor_id' => 'required|exists:proveedores,id',
            'usuario_id'      => 'required|exists:usuarios,id',      // ← nuevo
            'fecha_ingreso' => 'required|date',
            'num_remesa' => 'required|string',
            'origen_mer' => 'required|string',
            'destino_mer' => 'required|string',
            'nom_remitente' => 'required|string',
            'doc_remitente' => 'required|string',
            'direccion_remitente' => 'required|string',
            'cel_remitente' => 'required|string',
            'destin_nom' => 'required|string',
            'destin_doc' => 'required|string',
            'destin_direccion' => 'required|string',
            'destin_celular' => 'required|string',
            'val_declarado' => 'required|numeric',
            'val_flete' => 'required|numeric',
            'val_total' => 'required|numeric',
            'peso' => 'required|numeric',
            'unidades' => 'required|numeric',
            'observaciones' => 'nullable|string',
            'tipopago_id' => 'required|exists:tipospago,id',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $mercancias = Mercancia::create($request->all());

        // Crear seguimiento para la mercancía
        Seguimiento::create([
            'mercancias_id' => $mercancias->id,
            'evento' => 'Mercancía ingresada',
            'estado' => 'Ingresada en bodega',
            'usuario_id' => $mercancias->usuario_id, // Asumiendo que el controlador tiene acceso a los datos de usuario
            'mercancia_id' => $mercancias->id
        ]);

        return response()->json([
            'message' => 'Mercancía ingresada exitosamente',
            'mercancia' => $mercancias
        ], 201);
    }

    /**
     * @OA\Get(
     *     path="/api/mercancias/{id}",
     *     summary="Obtener mercancía por ID",
     *     tags={"Mercancías"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID de la mercancía", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Mercancía encontrada"),
     *     @OA\Response(response=404, description="No encontrada")
     * )
     */
    // Obtener una mercancía por ID
    public function show($id)
    {
        $mercancias = Mercancia::find($id);

        if (!$mercancias) {
            return response()->json(['message' => 'Mercancía no encontrada'], 404);
        }

        return response()->json($mercancias, 200);
    }

    /**
     * @OA\Put(
     *     path="/api/mercancias/{id}",
     *     summary="Actualizar una mercancía",
     *     tags={"Mercancías"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID de la mercancía", @OA\Schema(type="integer")),
     *     @OA\RequestBody(ref="#/components/requestBodies/MercanciaUpdateBody"),
     *     @OA\Response(response=200, description="Mercancía actualizada"),
     *     @OA\Response(response=400, description="Errores de validación"),
     *     @OA\Response(response=404, description="No encontrada")
     * )
     */
    // Actualizar una mercancía
    public function update(Request $request, $id)
    {
        $validator = Validator::make($request->all(), [
            'proveedor_id' => 'required|exists:proveedores,id',
            'fecha_ingreso' => 'required|date',
            'num_remesa' => 'required|string',
            'origen_mer' => 'required|string',
            'destino_mer' => 'required|string',
            'nom_remitente' => 'required|string',
            'doc_remitente' => 'required|string',
            'direccion_remitente' => 'required|string',
            'cel_remitente' => 'required|string',
            'destin_nom' => 'required|string',
            'destin_doc' => 'required|string',
            'destin_direccion' => 'required|string',
            'destin_celular' => 'required|string',
            'val_declarado' => 'required|numeric',
            'val_flete' => 'required|numeric',
            'val_total' => 'required|numeric',
            'peso' => 'required|numeric',
            'unidades' => 'required|numeric',
            'observaciones' => 'nullable|string',
            'tipopago_id' => 'required|exists:tipospago,id',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $mercancias = Mercancia::find($id);

        if (!$mercancias) {
            return response()->json(['message' => 'Mercancía no encontrada'], 404);
        }

        $mercancias->update($request->all());

        // Crear seguimiento para la actualización
        Seguimiento::create([
            'mercancias_id' => $mercancias->id,
            'evento' => 'Mercancía actualizada',
            'estado' => 'En proceso de despacho',
            'usuario_id' => $mercancias->usuario_id, // Suponiendo que el controlador tiene acceso a los datos de usuario
            'mercancia_id' => $mercancias->id
        ]);

        return response()->json([
            'message' => 'Mercancía actualizada exitosamente',
            'mercancia' => $mercancias
        ], 200);
    }
  /**
     * @OA\Delete(
     *     path="/api/mercancias/{id}",
     *     summary="Eliminar mercancía",
     *     tags={"Mercancías"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, description="ID de la mercancía", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Eliminación exitosa"),
     *     @OA\Response(response=404, description="No encontrada")
     * )
     */
    // Eliminar una mercancía
    public function destroy($id)
    {
        $mercancias = Mercancia::find($id);

        if (!$mercancias) {
            return response()->json(['message' => 'Mercancía no encontrada'], 404);
        }

        // Crear seguimiento para eliminación
        Seguimiento::create([
            'mercancias_id' => $mercancias->id,
            'evento' => 'Mercancía eliminada',
            'estado' => 'Eliminada',
            'usuario_id' => $mercancias->usuario_id, // Suponiendo que el controlador tiene acceso a los datos de usuario
            'mercancia_id' => $mercancias->id
        ]);

        $mercancias->delete();

        return response()->json(['message' => 'Mercancía eliminada exitosamente'], 200);
    }
}
