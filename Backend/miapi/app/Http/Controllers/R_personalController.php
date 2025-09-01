<?php

namespace App\Http\Controllers;

use App\Models\R_personal;
use Illuminate\Http\Request;

class R_personalController extends Controller
{
    // Obtener todos los registros
    public function index()
    {
        $r_personales = R_personal::all();
        return response()->json($r_personales, 200);
    }

    // Crear un nuevo registro
    public function store(Request $request)
    {
        $request->validate([
            'usuarios_id' => 'required|exists:usuarios,id',
            'nombre' => 'required|string|max:255',
            'apellido' => 'required|string|max:255',
            'parentezco' => 'required|string|max:255',
            'num_documento' => 'required|string|max:20',
            'tipo_documento_id' => 'required|exists:tipos_documento,id',
            'num_celular' => 'required|string|max:20',
            'num_direccion' => 'required|string|max:255',
        ]);

        $r_personales = R_personal::create($request->all());

        return response()->json([
            'message' => 'Referencia personal creada exitosamente.',
            'r_personal' => $r_personales
        ], 201);
    }

    // Obtener un solo registro por ID
    public function show($id)
    {
        $r_personales = R_personal::findOrFail($id);
        return response()->json($r_personales, 200);
    }

    // Actualizar un registro por ID
    public function update(Request $request, $id)
    {
        $r_personales = R_personal::findOrFail($id);
        
        if (!$r_personales) {
            return response()->json(['message' => 'Referencia no encontrada'], 404);
        }

        $request->validate([
            'usuarios_id' => 'required|exists:usuarios,id',
            'nombre' => 'required|string|max:255',
            'apellido' => 'required|string|max:255',
            'parentezco' => 'required|string|max:255',
            'num_documento' => 'required|string|max:20',
            'tipo_documento_id' => 'required|exists:tipos_documento,id',
            'num_celular' => 'required|string|max:20',
            'num_direccion' => 'required|string|max:255',
        ]);
        $r_personales->update($request->all());

        return response()->json([
            'message' => 'Referencia personal actualizada exitosamente.',
            'r_personal' => $r_personales
        ], 200);
    }

    // Eliminar un registro por ID
    public function destroy($id)
    {
        $R_personales = R_personal::findOrFail($id);
        $R_personales->delete();

        return response()->json([
            'message' => 'Referencia personal eliminada exitosamente.'
        ], 200);
    }
}
