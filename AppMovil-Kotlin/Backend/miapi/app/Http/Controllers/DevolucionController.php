<?php

// app/Http/Controllers/DevolucionController.php

namespace App\Http\Controllers;

use App\Models\Devolucion;
use App\Models\Seguimiento;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class DevolucionController extends Controller
{
    // Obtener todas las devoluciones
    public function index()
    {
        $devoluciones = Devolucion::with(['mercancia', 'usuario', 'proveedor'])->get();
        return response()->json($devoluciones, 200);
    }

    // Crear una nueva devolución
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'mercancias_id' => 'required|exists:mercancias,id',
            'usuarios_id' => 'required|exists:usuarios,id',
            'proveedores_id' => 'required|exists:proveedores,id',
            'fecha_devolucion' => 'required|date',
            'estado_devolucion' => 'required|string',
            'observaciones' => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $devoluciones = Devolucion::create($request->all());

        // Crear seguimiento
        Seguimiento::create([
            'mercancias_id' => $devoluciones->mercancias_id,
            'evento' => 'Devolución realizada',
            'estado' => 'Devolución procesada',
            'usuario_id' => $devoluciones->usuarios_id,
            'devolucion_id' => $devoluciones->id
        ]);

        return response()->json([
            'message' => 'Devolución registrada exitosamente',
            'devolucion' => $devoluciones
        ], 201);
    }

    // Obtener una devolución específica por ID
    public function show($id)
    {
        $devoluciones = Devolucion::with(['mercancia', 'usuario','proveedor'])->find($id);

        if (!$devoluciones) {
            return response()->json(['message' => 'Devolución no encontrada'], 404);
        }

        return response()->json($devoluciones, 200);
    }

    // Actualizar una devolución
    public function update(Request $request, $id)
    {
        $validator = Validator::make($request->all(), [
            'mercancias_id' => 'required|exists:mercancias,id',
            'usuarios_id' => 'required|exists:usuarios,id',
            'proveedores_id' => 'required|exists:proveedores,id',
            'fecha_devolucion' => 'required|date',
            'estado_devolucion' => 'required|string',
            'observaciones' => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $devoluciones = Devolucion::find($id);

        if (!$devoluciones) {
            return response()->json(['message' => 'Devolución no encontrada'], 404);
        }

        $devoluciones->update($request->all());

        // Crear seguimiento para la actualización
        Seguimiento::create([
            'mercancias_id' => $devoluciones->mercancias_id,
            'evento' => 'Devolución actualizada',
            'estado' => 'Devolución procesada',
            'usuario_id' => $devoluciones->usuarios_id,
            'devolucion_id' => $devoluciones->id
        ]);

        return response()->json([
            'message' => 'Devolución actualizada exitosamente',
            'devolucion' => $devoluciones
        ], 200);
    }

    // Eliminar una devolución
    public function destroy($id)
    {
        $devoluciones= Devolucion::find($id);

        if (!$devoluciones) {
            return response()->json(['message' => 'Devolución no encontrada'], 404);
        }

        // Crear seguimiento para la eliminación
        Seguimiento::create([
            'mercancias_id' => $devoluciones->mercancias_id,
            'evento' => 'Devolución eliminada',
            'estado' => 'Eliminada',
            'usuario_id' => $devoluciones->usuarios_id,
            'devolucion_id' => $devoluciones->id
        ]);

        $devoluciones->delete();

        return response()->json(['message' => 'Devolución eliminada exitosamente'], 200);
    }
}
