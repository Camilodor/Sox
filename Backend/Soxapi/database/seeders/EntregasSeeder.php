<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Entrega;
use App\Models\Mercancia;
use App\Models\Despacho;
use App\Models\User;

class EntregasSeeder extends Seeder
{
    public function run()
    {
        $mercancias = Mercancia::all();
        $despachos = Despacho::all();
        $usuarios = User::all();

        foreach ($mercancias as $mercancia) {
            $despacho = $despachos->random();
            $usuario = $usuarios->random();

            Entrega::create([
                'mercancias_id' => $mercancia->id,
                'despachos_id' => $despacho->id,
                'usuarios_id' => $usuario->id,
                'nombre_recibe' => 'Cliente ' . $mercancia->id,
                'numero_celular_recibe' => '300' . rand(1000000, 9999999),
                'fecha_entrega' => now(),
                'estado_entrega' => 'Entregado',
                'observaciones' => 'Entrega de prueba',
            ]);
        }
    }
}
