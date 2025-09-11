<?php

namespace App\Http\Controllers;

use App\Models\Devolucion;
use App\Models\Mercancia;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class DevolucionController extends Controller
{
    // 📌 Listar devoluciones
    public function index()
    {
        $devoluciones = Devolucion::with(['mercancias', 'usuarios', 'despachos'])->get();
        return response()->json($devoluciones, 200);
    }

    // 📌 Crear devolución
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'numero_remesa'     => 'required|exists:mercancias,numero_remesa',
            'despachos_id'      => 'required|exists:despachos,id',
            'fecha_devolucion'  => 'required|date',
            'motivo_devolucion' => 'required|string',
            'estado_devolucion' => 'required|string',
            'observaciones'     => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        // 🔹 Buscar mercancia por numero_remesa
        $mercancia = Mercancia::where('numero_remesa', $request->numero_remesa)->first();

        if (!$mercancia) {
            return response()->json(['message' => 'Mercancía no encontrada'], 404);
        }

        // 🔹 Usuario del token
        $usuario = $request->user();

        $devolucion = Devolucion::create([
            'mercancias_id'     => $mercancia->id,
            'despachos_id'      => $request->despachos_id,
            'usuarios_id'       => $usuario->id,
            'fecha_devolucion'  => $request->fecha_devolucion,
            'motivo_devolucion' => $request->motivo_devolucion,
            'estado_devolucion' => $request->estado_devolucion,
            'observaciones'     => $request->observaciones,
        ]);

        $devolucion->load(['mercancias', 'usuarios', 'despachos']);

        return response()->json([
            'message' => 'Devolución registrada exitosamente',
            'devolucion' => $devolucion
        ], 201);
    }

    // 📌 Mostrar devolución
    public function show($id)
    {
        $devolucion = Devolucion::with(['mercancias', 'usuarios', 'despachos'])->find($id);

        if (!$devolucion) {
            return response()->json(['message' => 'Devolución no encontrada'], 404);
        }

        return response()->json($devolucion, 200);
    }

    // 📌 Actualizar devolución
    public function update(Request $request, $id)
    {
        $validator = Validator::make($request->all(), [
            'numero_remesa'     => 'required|exists:mercancias,numero_remesa',
            'despachos_id'      => 'required|exists:despachos,id',
            'fecha_devolucion'  => 'required|date',
            'motivo_devolucion' => 'required|string',
            'estado_devolucion' => 'required|string',
            'observaciones'     => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $devolucion = Devolucion::find($id);

        if (!$devolucion) {
            return response()->json(['message' => 'Devolución no encontrada'], 404);
        }

        // 🔹 Buscar mercancia por numero_remesa
        $mercancia = Mercancia::where('numero_remesa', $request->numero_remesa)->first();

        if (!$mercancia) {
            return response()->json(['message' => 'Mercancía no encontrada'], 404);
        }

        // 🔹 Usuario del token
        $usuario = $request->user();

        $devolucion->update([
            'mercancias_id'     => $mercancia->id,
            'despachos_id'      => $request->despachos_id,
            'usuarios_id'       => $usuario->id,
            'fecha_devolucion'  => $request->fecha_devolucion,
            'motivo_devolucion' => $request->motivo_devolucion,
            'estado_devolucion' => $request->estado_devolucion,
            'observaciones'     => $request->observaciones,
        ]);

        $devolucion->load(['mercancias', 'usuarios', 'despachos']);

        return response()->json([
            'message' => 'Devolución actualizada exitosamente',
            'devolucion' => $devolucion
        ], 200);
    }

    // 📌 Eliminar devolución
    public function destroy($id)
    {
        $devolucion = Devolucion::find($id);

        if (!$devolucion) {
            return response()->json(['message' => 'Devolución no encontrada'], 404);
        }

        $devolucion->delete();

        return response()->json(['message' => 'Devolución eliminada exitosamente'], 200);
    }
}
