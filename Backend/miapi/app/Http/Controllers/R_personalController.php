<?php

namespace App\Http\Controllers;

use App\Models\R_personal;
use Illuminate\Http\Request;

class R_personalController extends Controller
{
    // Obtener todos los registros
    public function index()
    {
        $rPersonales = R_personal::all();
        return response()->json($rPersonales, 200);
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

        $rPersonal = R_personal::create($request->all());

        return response()->json([
            'message' => 'Referencia personal creada exitosamente.',
            'r_personal' => $rPersonal
        ], 201);
    }

    // Obtener un solo registro por ID
    public function show($id)
    {
        $rPersonal = R_personal::findOrFail($id);
        return response()->json($rPersonal, 200);
    }

    // Actualizar un registro por ID
    public function update(Request $request, $id)
    {
        $rPersonal = R_personal::findOrFail($id);
        $rPersonal->update($request->all());

        return response()->json([
            'message' => 'Referencia personal actualizada exitosamente.',
            'r_personal' => $rPersonal
        ], 200);
    }

    // Eliminar un registro por ID
    public function destroy($id)
    {
        $rPersonal = R_personal::findOrFail($id);
        $rPersonal->delete();

        return response()->json([
            'message' => 'Referencia personal eliminada exitosamente.'
        ], 200);
    }
}
