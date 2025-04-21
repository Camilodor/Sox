<?php
// app/Http/Controllers/DespachoController.php

namespace App\Http\Controllers;

use App\Models\Despacho;
use App\Models\Seguimiento;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class DespachoController extends Controller
{
    // Obtener todos los despachos
    public function index()
    {
        $despachos = Despacho::with(['mercancia', 'vehiculo', 'usuario', 'tipoPago'])->get();
        return response()->json($despachos, 200);
    }

    // Crear un nuevo despacho
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'mercancias_id' => 'required|exists:mercancias,id',
            'vehiculos_id' => 'required|exists:vehiculos,id',
            'usuarios_id' => 'required|exists:usuarios,id',
            'tipo_pago_id' => 'required|exists:tipospago,id',
            'fecha_despacho' => 'required|date',
            'negociacion' => 'required|numeric',
            'anticipo' => 'required|numeric',
            'saldo' => 'required|numeric',
            'observaciones_mer' => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $despacho = Despacho::create($request->all());

        // Crear seguimiento
        Seguimiento::create([
            'mercancias_id' => $despacho->mercancias_id,
            'evento' => 'Despacho creado',
            'estado' => 'Despachado',
            'usuario_id' => $despacho->usuarios_id,
            'despacho_id' => $despacho->id
        ]);

        return response()->json([
            'message' => 'Despacho creado exitosamente',
            'despacho' => $despacho
        ], 201);
    }

    // Obtener un despacho por ID
    public function show($id)
    {
        $despacho = Despacho::with(['mercancia', 'vehiculo', 'usuario', 'tipoPago'])->find($id);

        if (!$despacho) {
            return response()->json(['message' => 'Despacho no encontrado'], 404);
        }

        return response()->json($despacho, 200);
    }

    // Actualizar un despacho
    public function update(Request $request, $id)
    {
        $validator = Validator::make($request->all(), [
            'mercancias_id' => 'required|exists:mercancias,id',
            'vehiculos_id' => 'required|exists:vehiculos,id',
            'usuarios_id' => 'required|exists:usuarios,id',
            'tipo_pago_id' => 'required|exists:tipospago,id',
            'fecha_despacho' => 'required|date',
            'negociacion' => 'required|numeric',
            'anticipo' => 'required|numeric',
            'saldo' => 'required|numeric',
            'observaciones_mer' => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $despacho = Despacho::find($id);

        if (!$despacho) {
            return response()->json(['message' => 'Despacho no encontrado'], 404);
        }

        $despacho->update($request->all());

        // Crear seguimiento para actualización
        Seguimiento::create([
            'mercancias_id' => $despacho->mercancias_id,
            'evento' => 'Despacho actualizado',
            'estado' => 'En tránsito',
            'usuario_id' => $despacho->usuarios_id,
            'despacho_id' => $despacho->id
        ]);

        return response()->json([
            'message' => 'Despacho actualizado exitosamente',
            'despacho' => $despacho
        ], 200);
    }

    // Eliminar un despacho
    public function destroy($id)
    {
        $despacho = Despacho::find($id);

        if (!$despacho) {
            return response()->json(['message' => 'Despacho no encontrado'], 404);
        }

        // Crear seguimiento para eliminación
        Seguimiento::create([
            'mercancias_id' => $despacho->mercancias_id,
            'evento' => 'Despacho eliminado',
            'estado' => 'Eliminado',
            'usuario_id' => $despacho->usuarios_id,
            'despacho_id' => $despacho->id
        ]);

        $despacho->delete();

        return response()->json(['message' => 'Despacho eliminado exitosamente'], 200);
    }
}
