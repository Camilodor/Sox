<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Despacho;
use App\Models\Mercancia;
use App\Models\Vehiculo;
use App\Models\TipoPago;
use App\Models\User;

class DespachosSeeder extends Seeder
{
    public function run()
    {
        $mercancias = Mercancia::all();
        $vehiculos = Vehiculo::all();
        $tiposPago = TipoPago::all();
        $usuarios = User::all();

        foreach ($mercancias as $mercancia) {
            $vehiculo = $vehiculos->random();
            $usuario = $usuarios->random();
            $tipoPago = $tiposPago->random();

            Despacho::create([
                'mercancias_id' => $mercancia->id,
                'vehiculos_id' => $vehiculo->id,
                'usuarios_id' => $usuario->id,
                'tipo_pago_id' => $tipoPago->id,
                'fecha_despacho' => now(),
                'negociacion' => rand(1000, 5000),
                'anticipo' => rand(100, 500),
                'saldo' => rand(500, 4500),
                'observaciones_mer' => 'Despacho de prueba',
            ]);
        }
    }
}
