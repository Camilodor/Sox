<?php

namespace App\Http\Controllers;

use App\Models\R_laboral;
use Illuminate\Http\Request;

class R_laboralController extends Controller
{
    // Obtener todos los registros
    public function index()
    {
        $rLaborales = R_laboral::all();
        return response()->json($rLaborales, 200);
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
            'tipo_documento' => 'required|exists:tiposdocumento,id',
            'num_celular' => 'required|string|max:20',
            'num_direccion' => 'required|string|max:255',
        ]);

        $rLaboral = R_laboral::create($request->all());

        return response()->json([
            'message' => 'Referencia laboral creada exitosamente.',
            'r_laboral' => $rLaboral
        ], 201);
    }

    // Obtener un solo registro por ID
    public function show($id)
    {
        $rLaboral = R_laboral::findOrFail($id);
        return response()->json($rLaboral, 200);
    }

    // Actualizar un registro por ID
    public function update(Request $request, $id)
    {
        $rLaboral = R_laboral::findOrFail($id);
        $rLaboral->update($request->all());

        return response()->json([
            'message' => 'Referencia laboral actualizada exitosamente.',
            'r_laboral' => $rLaboral
        ], 200);
    }

    // Eliminar un registro por ID
    public function destroy($id)
    {
        $rLaboral = R_laboral::findOrFail($id);
        $rLaboral->delete();

        return response()->json([
            'message' => 'Referencia laboral eliminada exitosamente.'
        ], 200);
    }
}
