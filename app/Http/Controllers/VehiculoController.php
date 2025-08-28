<?php

// app/Http/Controllers/VehiculoController.php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Vehiculo;
use Illuminate\Support\Facades\Validator;

class VehiculoController extends Controller
{ 
    /**
     * @OA\Get(
     *     path="/api/vehiculos",
     *     summary="Obtener todos los vehículos",
     *     tags={"Vehículos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Response(response=200, description="Listado de vehículos")
     * )
     */
    // Obtener todos los vehículos
    public function index()
    {
        $vehiculos = Vehiculo::all();
        return response()->json($vehiculos, 200);
    }
/**
     * @OA\Post(
     *     path="/api/vehiculos",
     *     summary="Registrar un nuevo vehículo",
     *     tags={"Vehículos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={
     *                 "num_placas", "nom_marca_vehiculo", "num_propietario_veh", "num_propietario_cel",
     *                 "direc_propietario", "ciudad_propietario", "num_modelo_ano", "color_vehiculo"
     *             },
     *             @OA\Property(property="num_placas", type="string", maxLength=20, example="ABC123"),
     *             @OA\Property(property="nom_marca_vehiculo", type="string", example="Chevrolet"),
     *             @OA\Property(property="num_propietario_veh", type="string", example="Carlos Pérez"),
     *             @OA\Property(property="num_propietario_cel", type="string", example="3004567890"),
     *             @OA\Property(property="direc_propietario", type="string", example="Calle 123 #45-67"),
     *             @OA\Property(property="ciudad_propietario", type="string", example="Bogotá"),
     *             @OA\Property(property="num_modelo_ano", type="string", example="2020"),
     *             @OA\Property(property="color_vehiculo", type="string", example="Blanco"),
     *             @OA\Property(property="fecha_vencimiento_soat", type="string", format="date", example="2025-08-10"),
     *             @OA\Property(property="fecha_vencimiento_tecno", type="string", format="date", example="2025-09-15"),
     *             @OA\Property(property="nom_satelital", type="string", example="GPSMax"),
     *             @OA\Property(property="usuario_satelital", type="string", example="gps_user"),
     *             @OA\Property(property="contra_satelital", type="string", example="1234gps"),
     *             @OA\Property(property="capacidad_carga", type="number", format="float", example=5.5)
     *         )
     *     ),
     *     @OA\Response(response=201, description="Vehículo creado exitosamente"),
     *     @OA\Response(response=400, description="Errores de validación")
     * )
     */
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
            'num_modelo_ano' => 'required|string|max:10',
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

        $vehiculos = Vehiculo::create($request->all());

        return response()->json([
            'message' => 'Vehículo creado exitosamente',
            'vehiculo' => $vehiculos
        ], 201);
    }

    /**
     * @OA\Get(
     *     path="/api/vehiculos/{id}",
     *     summary="Mostrar un vehículo por ID",
     *     tags={"Vehículos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id", in="path", required=true,
     *         description="ID del vehículo", @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=200, description="Vehículo encontrado"),
     *     @OA\Response(response=404, description="Vehículo no encontrado")
     * )
     */
    // Mostrar un vehículo por ID
    public function show($id)
    {
        $vehiculos = Vehiculo::find($id);
        if (!$vehiculos) {
            return response()->json(['message' => 'Vehículo no encontrado'], 404);
        }
        return response()->json($vehiculos, 200);
    }
 /**
     * @OA\Put(
     *     path="/api/vehiculos/{id}",
     *     summary="Actualizar un vehículo por ID",
     *     tags={"Vehículos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id", in="path", required=true,
     *         description="ID del vehículo", @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(ref="#/components/requestBodies/VehiculoStore"),
     *     @OA\Response(response=200, description="Vehículo actualizado exitosamente"),
     *     @OA\Response(response=404, description="Vehículo no encontrado"),
     *     @OA\Response(response=400, description="Errores de validación")
     * )
     */
    // Actualizar un vehículo
    public function update(Request $request, $id)
    {
        $vehiculos = Vehiculo::find($id);
        if (!$vehiculos) {
            return response()->json(['message' => 'Vehículo no encontrado'], 404);
        }

        $validator = Validator::make($request->all(), [
            'num_placas' => 'required|string|max:20',
            'nom_marca_vehiculo' => 'required|string|max:50',
            'num_propietario_veh' => 'required|string|max:50',
            'num_propietario_cel' => 'required|string|max:50',
            'direc_propietario' => 'required|string|max:50',
            'ciudad_propietario' => 'required|string|max:50',
            'num_modelo_ano' => 'required|string|max:10',
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

        $vehiculos->update($request->all());

        return response()->json([
            'message' => 'Vehículo actualizado exitosamente',
            'vehiculo' => $vehiculos
        ], 200);
    }

    /**
     * @OA\Delete(
     *     path="/api/vehiculos/{id}",
     *     summary="Eliminar un vehículo por ID",
     *     tags={"Vehículos"},
     *     security={{"bearerAuth":{}}},
     *     @OA\Parameter(
     *         name="id", in="path", required=true,
     *         description="ID del vehículo", @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=200, description="Vehículo eliminado correctamente"),
     *     @OA\Response(response=404, description="Vehículo no encontrado")
     * )
     */
    // Eliminar un vehículo
    public function destroy($id)
    {
        $vehiculos = Vehiculo::find($id);
        if (!$vehiculos) {
            return response()->json(['message' => 'Vehículo no encontrado'], 404);
        }

        $vehiculos->delete();
        return response()->json(['message' => 'Vehículo eliminado correctamente'], 200);
    }
}
