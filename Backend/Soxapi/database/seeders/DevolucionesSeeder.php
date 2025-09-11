<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Devolucion;
use App\Models\Mercancia;
use App\Models\Despacho;
use App\Models\User;

class DevolucionesSeeder extends Seeder
{
    public function run()
    {
        $mercancias = Mercancia::all();
        $despachos = Despacho::all();
        $usuarios = User::all();

        foreach ($mercancias as $mercancia) {
            $despacho = $despachos->random();
            $usuario = $usuarios->random();

            Devolucion::create([
                'mercancias_id' => $mercancia->id,
                'despachos_id' => $despacho->id,
                'usuarios_id' => $usuario->id,
                'fecha_devolucion' => now(),
                'motivo_devolucion' => 'Motivo de prueba',
                'estado_devolucion' => 'Pendiente',
                'observaciones' => 'Devolución de prueba',
            ]);
        }
    }
}
