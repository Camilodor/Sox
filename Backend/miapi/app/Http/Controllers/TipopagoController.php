<?php

// app/Http/Controllers/TipopagoController.php

namespace App\Http\Controllers;

use App\Models\Tipopago;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class TipopagoController extends Controller
{
    public function index()
    {
        $tipospago = Tipopago::all();
        return response()->json($tipospago, 200);
    }

    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'nombre' => 'required|string|max:50|unique:tipospago',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $tipopago = Tipopago::create($request->all());

        return response()->json([
            'message' => 'Tipo de pago creado exitosamente',
            'tipopago' => $tipopago
        ], 201);
    }

    public function show($id)
    {
        $tipopago = Tipopago::find($id);

        if (!$tipopago) {
            return response()->json(['message' => 'Tipo de pago no encontrado'], 404);
        }

        return response()->json($tipopago, 200);
    }

    public function update(Request $request, $id)
    {
        $tipopago = Tipopago::find($id);

        if (!$tipopago) {
            return response()->json(['message' => 'Tipo de pago no encontrado'], 404);
        }

        $validator = Validator::make($request->all(), [
            'nombre' => 'required|string|max:50|unique:tipospago,nombre,' . $id,
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $tipopago->update($request->all());

        return response()->json([
            'message' => 'Tipo de pago actualizado exitosamente',
            'tipopago' => $tipopago
        ], 200);
    }

    public function destroy($id)
    {
        $tipopago = Tipopago::find($id);

        if (!$tipopago) {
            return response()->json(['message' => 'Tipo de pago no encontrado'], 404);
        }

        $tipopago->delete();

        return response()->json(['message' => 'Tipo de pago eliminado exitosamente'], 200);
    }
}

