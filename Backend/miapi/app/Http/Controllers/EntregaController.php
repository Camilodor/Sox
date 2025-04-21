<?php

// app/Http/Controllers/EntregaController.php

namespace App\Http\Controllers;

use App\Models\Entrega;
use App\Models\Seguimiento;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class EntregaController extends Controller
{
    // Obtener todas las entregas
    public function index()
    {
        $entregas = Entrega::with(['mercancia', 'usuario', 'entregador'])->get();
        return response()->json($entregas, 200);
    }

    // Crear una nueva entrega
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'mercancias_id' => 'required|exists:mercancias,id',
            'usuario_id' => 'required|exists:usuarios,id',
            'entregador_id' => 'required|exists:entregadores,id',
            'fecha_entrega' => 'required|date',
            'estado_entrega' => 'required|string',
            'observaciones' => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $entrega = Entrega::create($request->all());

        // Crear seguimiento
        Seguimiento::create([
            'mercancias_id' => $entrega->mercancias_id,
            'evento' => 'Mercancía entregada',
            'estado' => 'Entregada',
            'usuario_id' => $entrega->usuario_id,
            'entrega_id' => $entrega->id
        ]);

        return response()->json([
            'message' => 'Entrega registrada exitosamente',
            'entrega' => $entrega
        ], 201);
    }

    // Obtener una entrega específica por ID
    public function show($id)
    {
        $entrega = Entrega::with(['mercancia', 'usuario', 'entregador'])->find($id);

        if (!$entrega) {
            return response()->json(['message' => 'Entrega no encontrada'], 404);
        }

        return response()->json($entrega, 200);
    }

    // Actualizar una entrega
    public function update(Request $request, $id)
    {
        $validator = Validator::make($request->all(), [
            'mercancias_id' => 'required|exists:mercancias,id',
            'usuario_id' => 'required|exists:usuarios,id',
            'entregador_id' => 'required|exists:entregadores,id',
            'fecha_entrega' => 'required|date',
            'estado_entrega' => 'required|string',
            'observaciones' => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $entrega = Entrega::find($id);

        if (!$entrega) {
            return response()->json(['message' => 'Entrega no encontrada'], 404);
        }

        $entrega->update($request->all());

        // Crear seguimiento para la actualización
        Seguimiento::create([
            'mercancias_id' => $entrega->mercancias_id,
            'evento' => 'Entrega actualizada',
            'estado' => 'Entregada',
            'usuario_id' => $entrega->usuario_id,
            'entrega_id' => $entrega->id
        ]);

        return response()->json([
            'message' => 'Entrega actualizada exitosamente',
            'entrega' => $entrega
        ], 200);
    }

    // Eliminar una entrega
    public function destroy($id)
    {
        $entrega = Entrega::find($id);

        if (!$entrega) {
            return response()->json(['message' => 'Entrega no encontrada'], 404);
        }

        // Crear seguimiento para la eliminación
        Seguimiento::create([
            'mercancias_id' => $entrega->mercancias_id,
            'evento' => 'Entrega eliminada',
            'estado' => 'Eliminada',
            'usuario_id' => $entrega->usuario_id,
            'entrega_id' => $entrega->id
        ]);

        $entrega->delete();

        return response()->json(['message' => 'Entrega eliminada exitosamente'], 200);
    }
}

