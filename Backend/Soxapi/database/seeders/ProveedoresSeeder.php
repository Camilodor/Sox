<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Proveedor;
use App\Models\User;

class ProveedoresSeeder extends Seeder
{
    public function run()
    {
       
        $usuarios = User::all();

        foreach ($usuarios as $usuario) {
            Proveedor::create([
                'usuarios_id' => $usuario->id,
                'nombre' => 'Proveedor ' . $usuario->id,
                'descripcion' => 'Descripción del proveedor ' . $usuario->id,
            ]);
        }
    }
}

