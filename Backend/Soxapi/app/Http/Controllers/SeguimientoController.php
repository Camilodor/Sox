<?php

namespace App\Http\Controllers;

use App\Models\Seguimiento;
use Illuminate\Http\Request;

class SeguimientoController extends Controller
{
    /**
     * Listar todos los seguimientos
     */
    public function index()
    {
        return Seguimiento::with('mercancia')->get();
    }

    /**
     * Crear un seguimiento
     */
    public function store(Request $request)
    {
        $validated = $request->validate([
            'mercancias_id' => 'required|exists:mercancias,id',
            'estado' => 'required|string|max:255',
        ]);

        $seguimiento = Seguimiento::create($validated);

        return response()->json([
            'message' => 'Seguimiento creado correctamente',
            'seguimiento' => $seguimiento
        ], 201);
    }

    /**
     * Mostrar un seguimiento específico
     */
    public function show($id)
    {
        return Seguimiento::with('mercancia')->findOrFail($id);
    }

    /**
     * Actualizar un seguimiento
     */
    public function update(Request $request, $id)
    {
        $seguimiento = Seguimiento::findOrFail($id);

        $validated = $request->validate([
            'estado' => 'required|string|max:255',
        ]);

        $seguimiento->update($validated);

        return response()->json([
            'message' => 'Seguimiento actualizado correctamente',
            'seguimiento' => $seguimiento
        ]);
    }

    /**
     * Eliminar un seguimiento
     */
    public function destroy($id)
    {
        $seguimiento = Seguimiento::findOrFail($id);
        $seguimiento->delete();

        return response()->json([
            'message' => 'Seguimiento eliminado correctamente'
        ]);
    }
}
