<?php
// app/Http/Controllers/SeguimientoController.php

namespace App\Http\Controllers;

use App\Models\Seguimiento;
use App\Models\Mercancia;
use App\Models\Despacho;
use App\Models\Entrega;
use App\Models\Devolucion;
use Illuminate\Http\Request;

class SeguimientoController extends Controller
{
    // Obtener todos los seguimientos
    public function index()
    {
        $seguimientos = Seguimiento::with(['mercancia', 'despacho', 'entrega', 'devolucion'])->get();
        return response()->json($seguimientos, 200);
    }

    // Obtener un seguimiento específico por ID
    public function show($id)
    {
        $seguimientos = Seguimiento::with(['mercancia', 'despacho', 'entrega', 'devolucion'])->find($id);

        if (!$seguimientos) {
            return response()->json(['message' => 'Seguimiento no encontrado'], 404);
        }

        return response()->json($seguimientos, 200);
    }

    // Crear un seguimiento (Esto lo podrías llamar en el controlador correspondiente cuando se realice un cambio)
    public function store(Request $request)
    {
        $request->validate([
            'mercancias_id' => 'required|exists:mercancias,id',
            'evento' => 'required|string',
            'estado' => 'required|string',
            'usuario_id' => 'required|exists:usuarios,id',
            'despacho_id' => 'nullable|exists:despachos,id',
            'entrega_id' => 'nullable|exists:entregas,id',
            'devolucion_id' => 'nullable|exists:devoluciones,id',
        ]);

        $seguimientos = Seguimiento::create($request->all());

        return response()->json([
            'message' => 'Seguimiento creado exitosamente',
            'seguimiento' => $seguimientos
        ], 201);
    }

    // Actualizar un seguimiento
    public function update(Request $request, $id)
    {
        $request->validate([
            'mercancias_id' => 'required|exists:mercancias,id',
            'evento' => 'required|string',
            'estado' => 'required|string',
            'usuario_id' => 'required|exists:usuarios,id',
            'despacho_id' => 'nullable|exists:despachos,id',
            'entrega_id' => 'nullable|exists:entregas,id',
            'devolucion_id' => 'nullable|exists:devoluciones,id',
        ]);

        $seguimientos = Seguimiento::find($id);

        if (!$seguimientos) {
            return response()->json(['message' => 'Seguimiento no encontrado'], 404);
        }

        $seguimientos->update($request->all());

        return response()->json([
            'message' => 'Seguimiento actualizado exitosamente',
            'seguimiento' => $seguimientos
        ], 200);
    }

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
