<?php

namespace App\Http\Controllers;

use App\Models\Despacho;
use App\Models\Mercancia;
use App\Models\Vehiculo;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class DespachoController extends Controller
{
    public function index()
    {
        $despachos = Despacho::with(['mercancia', 'vehiculo', 'usuario', 'tipopago'])->get();
        return response()->json($despachos, 200);
    }

    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'numero_remesa' => 'required|exists:mercancias,numero_remesa',
            'numero_placas' => 'required|exists:vehiculos,numero_placas',
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

        $mercancia = Mercancia::where('numero_remesa', $request->numero_remesa)->first();
        $vehiculo = Vehiculo::where('numero_placas', $request->numero_placas)->first();
        $usuarioId = auth()->id();

        $despacho = Despacho::create([
            'mercancias_id' => $mercancia->id,
            'vehiculos_id' => $vehiculo->id,
            'usuarios_id' => $usuarioId,
            'tipo_pago_id' => $request->tipo_pago_id,
            'fecha_despacho' => $request->fecha_despacho,
            'negociacion' => $request->negociacion,
            'anticipo' => $request->anticipo,
            'saldo' => $request->saldo,
            'observaciones_mer' => $request->observaciones_mer,
        ]);

        $despacho->load(['mercancia', 'vehiculo', 'usuario', 'tipopago']);

        return response()->json([
            'message' => 'Despacho creado exitosamente',
            'despacho' => $despacho
        ], 201);
    }

    public function show($id)
    {
        $despacho = Despacho::with(['mercancia', 'vehiculo', 'usuario', 'tipopago'])->find($id);

        if (!$despacho) {
            return response()->json(['message' => 'Despacho no encontrado'], 404);
        }

        return response()->json($despacho, 200);
    }

    public function update(Request $request, $id)
    {
        $validator = Validator::make($request->all(), [
            'numero_remesa' => 'required|exists:mercancias,numero_remesa',
            'numero_placas' => 'required|exists:vehiculos,numero_placas',
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

        $mercancia = Mercancia::where('numero_remesa', $request->numero_remesa)->first();
        $vehiculo = Vehiculo::where('numero_placas', $request->numero_placas)->first();
        $usuarioId = auth()->id();

        $despacho->update([
            'mercancias_id' => $mercancia->id,
            'vehiculos_id' => $vehiculo->id,
            'usuarios_id' => $usuarioId,
            'tipo_pago_id' => $request->tipo_pago_id,
            'fecha_despacho' => $request->fecha_despacho,
            'negociacion' => $request->negociacion,
            'anticipo' => $request->anticipo,
            'saldo' => $request->saldo,
            'observaciones_mer' => $request->observaciones_mer,
        ]);

        $despacho->load(['mercancia', 'vehiculo', 'usuario', 'tipopago']);

        return response()->json([
            'message' => 'Despacho actualizado exitosamente',
            'despacho' => $despacho
        ], 200);
    }

    public function destroy($id)
    {
        $despacho = Despacho::find($id);

        if (!$despacho) {
            return response()->json(['message' => 'Despacho no encontrado'], 404);
        }

        $despacho->delete();

        return response()->json(['message' => 'Despacho eliminado exitosamente'], 200);
    }
}
