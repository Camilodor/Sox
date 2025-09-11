<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Producto;
use App\Models\Proveedor;

class ProductosSeeder extends Seeder
{
    public function run()
    {
        $proveedores = Proveedor::all();

        foreach ($proveedores as $proveedor) {
            Producto::create([
                'proveedores_id' => $proveedor->id,
                'nombre' => 'Producto del proveedor ' . $proveedor->id,
                'descripcion' => 'Descripción del producto ' . $proveedor->id,
            ]);
        }
    }
}
