<?php

namespace App\Http\Controllers;

use App\Models\Producto;
use Illuminate\Http\Request;

class ProductoController extends Controller
{
    public function index()
    {
        $productos = Producto::all();
        return response()->json($productos, 200);
    }

    public function store(Request $request)
    {
        $request->validate([
            'proveedores_id' => 'required|exists:proveedores,id',
            'nombre' => 'required|string|max:255',
            'descripcion' => 'nullable|string',
        ]);

        $productos = Producto::create($request->all());

        return response()->json([
            'message' => 'Producto creado exitosamente.',
            'producto' => $productos
        ], 201);
    }

    public function show($id)
    {
        $productos = Producto::findOrFail($id);
        return response()->json($productos, 200);
    }

    public function update(Request $request, $id)
    {
        $productos = Producto::findOrFail($id);
        $productos->update($request->all());

        return response()->json([
            'message' => 'Producto actualizado exitosamente.',
            'producto' => $productos
        ], 200);
    }

    public function destroy($id)
    {
        $productos = Producto::findOrFail($id);
        $productos->delete();

        return response()->json([
            'message' => 'Producto eliminado exitosamente.'
        ], 200);
    }
}
