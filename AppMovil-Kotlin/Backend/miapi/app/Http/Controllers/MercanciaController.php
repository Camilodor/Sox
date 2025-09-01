<?php
// app/Http/Controllers/MercanciaController.php

namespace App\Http\Controllers;

use App\Models\Mercancia;
use App\Models\Seguimiento;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class MercanciaController extends Controller
{
    // Obtener todas las mercancías
    public function index()
    {
        $mercancias = Mercancia::all();
        return response()->json($mercancias, 200);
    }

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

    // Obtener una mercancía por ID
    public function show($id)
    {
        $mercancias = Mercancia::find($id);

        if (!$mercancias) {
            return response()->json(['message' => 'Mercancía no encontrada'], 404);
        }

        return response()->json($mercancias, 200);
    }

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
