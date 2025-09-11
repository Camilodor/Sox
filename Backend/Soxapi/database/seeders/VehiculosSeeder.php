<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Vehiculo;
use App\Models\User;

class VehiculosSeeder extends Seeder
{
    public function run()
    {
        $usuarios = User::all();

        foreach ($usuarios as $usuario) {
           Vehiculo::create([
    'usuarios_id' => $usuario->id,
    'numero_placas' => 'ABC-' . rand(100, 999),
    'nombre_marca_vehiculo' => 'Marca ' . rand(1,5),
    'nombre_propietario_vehiculo' => $usuario->nombres,
    'documento_propietario_vehiculo' => $usuario->numero_documento,
    'numero_celular_propietario' => '300' . rand(1000000, 9999999),
    'direccion_propietario' => 'Dirección ' . $usuario->id,
    'ciudad_propietario' => 'Ciudad ' . rand(1,5),
    'numero_modelo_anio' => '202' . rand(0,5),
    'color_vehiculo' => 'Color ' . rand(1,5),
    'fecha_vencimiento_soat' => now()->addYear(),
    'fecha_vencimiento_tecno' => now()->addYear(),
    'nombre_satelital' => 'Sat-' . rand(1,100),
    'usuario_satelital' => 'user' . rand(1,100),
    'contrasena_satelital' => 'pass' . rand(1000,9999),
    'capacidad_carga' => rand(1000,5000) / 10, // Ej: 100.0 a 500.0
]);


        }
    }
}
