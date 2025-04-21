<?php

// app/Http/Controllers/VehiculoController.php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Vehiculo;
use Illuminate\Support\Facades\Validator;

class VehiculoController extends Controller
{
    // Obtener todos los vehículos
    public function index()
    {
        $vehiculos = Vehiculo::all();
        return response()->json($vehiculos, 200);
    }

    // Crear un nuevo vehículo
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'num_placas' => 'required|string|max:20',
            'nom_marca_vehiculo' => 'required|string|max:50',
            'num_propietario_veh' => 'required|string|max:50',
            'num_propietario_cel' => 'required|string|max:50',
            'direc_propietario' => 'required|string|max:50',
            'ciudad_propietario' => 'required|string|max:50',
            'num_modelo_año' => 'required|string|max:10',
            'color_vehiculo' => 'required|string|max:30',
            'fecha_vencimiento_soat' => 'nullable|date',
            'fecha_vencimiento_tecno' => 'nullable|date',
            'nom_satelital' => 'nullable|string|max:50',
            'usuario_satelital' => 'nullable|string|max:50',
            'contra_satelital' => 'nullable|string|max:50',
            'capacidad_carga' => 'nullable|numeric',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $vehiculo = Vehiculo::create($request->all());

        return response()->json([
            'message' => 'Vehículo creado exitosamente',
            'vehiculo' => $vehiculo
        ], 201);
    }

    // Mostrar un vehículo por ID
    public function show($id)
    {
        $vehiculo = Vehiculo::find($id);
        if (!$vehiculo) {
            return response()->json(['message' => 'Vehículo no encontrado'], 404);
        }
        return response()->json($vehiculo, 200);
    }

    // Actualizar un vehículo
    public function update(Request $request, $id)
    {
        $vehiculo = Vehiculo::find($id);
        if (!$vehiculo) {
            return response()->json(['message' => 'Vehículo no encontrado'], 404);
        }

        $validator = Validator::make($request->all(), [
            'num_placas' => 'sometimes|required|string|max:20',
            'nom_marca_vehiculo' => 'sometimes|required|string|max:50',
            'num_propietario_veh' => 'sometimes|required|string|max:50',
            'num_modelo_año' => 'sometimes|required|string|max:10',
            'color_vehiculo' => 'sometimes|required|string|max:30',
            'fecha_vencimiento_soat' => 'nullable|date',
            'fecha_vencimiento_tecno' => 'nullable|date',
            'nom_satelital' => 'nullable|string|max:50',
            'usuario_satelital' => 'nullable|string|max:50',
            'contra_satelital' => 'nullable|string|max:50',
            'capacidad_carga' => 'nullable|numeric',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $vehiculo->update($request->all());

        return response()->json([
            'message' => 'Vehículo actualizado exitosamente',
            'vehiculo' => $vehiculo
        ], 200);
    }

    // Eliminar un vehículo
    public function destroy($id)
    {
        $vehiculo = Vehiculo::find($id);
        if (!$vehiculo) {
            return response()->json(['message' => 'Vehículo no encontrado'], 404);
        }

        $vehiculo->delete();
        return response()->json(['message' => 'Vehículo eliminado correctamente'], 200);
    }
}
