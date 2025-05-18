<?php

namespace App\Http\Controllers;

use App\Models\Proveedor;
use Illuminate\Http\Request;

class ProveedorController extends Controller
{
    public function index()
    {
        $proveedores = Proveedor::all();
        return response()->json($proveedores, 200);
    }

    public function store(Request $request)
    {
        $request->validate([
            'nombre' => 'required|string|max:255',
            'descripcion' => 'nullable|string',
        ]);

        $proveedores = Proveedor::create($request->all());

        return response()->json([
            'message' => 'Proveedor creado exitosamente.',
            'proveedor' => $proveedores
        ], 201);
    }

    public function show($id)
    {
        $proveedor = Proveedor::findOrFail($id);
        return response()->json($proveedores, 200);
    }

    public function update(Request $request, $id)
    {
        $proveedores = Proveedor::findOrFail($id);
        $proveedores->update($request->all());

        return response()->json([
            'message' => 'Proveedor actualizado exitosamente.',
            'proveedor' => $proveedores
        ], 200);
    }

    public function destroy($id)
    {
        $proveedores = Proveedor::findOrFail($id);
        $proveedores->delete();

        return response()->json([
            'message' => 'Proveedor eliminado exitosamente.'
        ], 200);
    }
}
