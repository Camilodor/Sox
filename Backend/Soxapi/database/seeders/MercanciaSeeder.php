<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Mercancia;
use App\Models\Proveedor;
use App\Models\User;
use App\Models\TipoPago;

class MercanciaSeeder extends Seeder
{
    public function run()
    {
        $usuarios = User::all();
        $proveedores = Proveedor::all();
        $tiposPago = TipoPago::all();

        foreach ($proveedores as $proveedor) {
            foreach ($usuarios as $usuario) {
                Mercancia::create([
                    'usuarios_id' => $usuario->id,
                    'proveedores_id' => $proveedor->id,
                    'fecha_ingreso' => now(),
                    'numero_remesa' => 'REM-' . rand(1000, 9999),
                    'origen_mercancia' => 'Ciudad A',
                    'destino_mercancia' => 'Ciudad B',
                    'nombre_remitente' => $usuario->nombres,
                    'documento_remitente' => $usuario->numero_documento,
                    'direccion_remitente' => 'Calle Falsa 123',
                    'celular_remitente' => '3001234567',
                    'nombre_destinatario' => 'Destinatario ' . $usuario->id,
                    'documento_destinatario' => '100000' . $usuario->id,
                    'direccion_destinatario' => 'Calle Real 456',
                    'celular_destinatario' => '3007654321',
                    'valor_declarado' => rand(1000, 5000),
                    'valor_flete' => rand(100, 500),
                    'valor_total' => rand(1100, 5500),
                    'peso' => rand(1, 50),
                    'unidades' => rand(1, 10),
                    'observaciones' => 'Mercancia de prueba',
                    'tipo_pago_id' => $tiposPago->random()->id,
                ]);
            }
        }
    }
}
