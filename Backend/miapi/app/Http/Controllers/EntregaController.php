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
        $entregas = Entrega::with(['mercancia', 'usuario', 'despacho'])->get();
        return response()->json($entregas, 200);
    }

    // Crear una nueva entrega
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'mercancias_id'       => 'required|exists:mercancias,id',
            'despacho_id'         => 'required|exists:despachos,id',   // cambiado
            'usuario_id'          => 'required|exists:usuarios,id',    // cambiado
            'nombre_recibe'       => 'required|string',
            'num_celular_recibe'  => 'required|string',
            'fecha_entrega'       => 'required|date',
            'estado_entrega'      => 'required|string',
            'observaciones'       => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $entregas = Entrega::create($request->all());

        // Crear seguimiento
        Seguimiento::create([
            'mercancias_id' => $entregas->mercancias_id,
            'evento' => 'Mercancía entregada',
            'estado' => 'Entregada',
            'usuario_id' => $entregas->usuario_id,
            'entrega_id' => $entregas->id
        ]);

        return response()->json([
            'message' => 'Entrega registrada exitosamente',
            'entrega' => $entregas
        ], 201);
    }

    // Obtener una entrega específica por ID
    public function show($id)
    {
        $entregas = Entrega::with(['mercancia', 'usuario','despacho'])->find($id);

        if (!$entregas) {
            return response()->json(['message' => 'Entrega no encontrada'], 404);
        }

        return response()->json($entregas, 200);
    }

    // Actualizar una entrega
    public function update(Request $request, $id)
    {
        $validator = Validator::make($request->all(), [
            'mercancias_id'       => 'required|exists:mercancias,id',
            'despacho_id'         => 'required|exists:despachos,id',   // cambiado
            'usuario_id'          => 'required|exists:usuarios,id',    // cambiado
            'nombre_recibe'       => 'required|string',
            'num_celular_recibe'  => 'required|string',
            'fecha_entrega'       => 'required|date',
            'estado_entrega'      => 'required|string',
            'observaciones'       => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $entregas = Entrega::find($id);

        if (!$entregas) {
            return response()->json(['message' => 'Entrega no encontrada'], 404);
        }

        $entregas->update($request->all());

        // Crear seguimiento para la actualización
        Seguimiento::create([
            'mercancias_id' => $entregas->mercancias_id,
            'evento' => 'Entrega actualizada',
            'estado' => 'Entregada',
            'usuario_id' => $entregas->usuario_id,
            'entrega_id' => $entregas->id
        ]);

        return response()->json([
            'message' => 'Entrega actualizada exitosamente',
            'entrega' => $entregas
        ], 200);
    }

    // Eliminar una entrega
    public function destroy($id)
    {
        $entregas = Entrega::find($id);

        if (!$entregas) {
            return response()->json(['message' => 'Entrega no encontrada'], 404);
        }

        // Crear seguimiento para la eliminación
        Seguimiento::create([
            'mercancias_id' => $entregas->mercancias_id,
            'evento' => 'Entrega eliminada',
            'estado' => 'Eliminada',
            'usuario_id' => $entregas->usuario_id,
            'entrega_id' => $entregas->id
        ]);

        $entregas->delete();

        return response()->json(['message' => 'Entrega eliminada exitosamente'], 200);
    }
}

