<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    public function run()
    {
        $this->call([
            TipodocumentoSeeder::class,
            TiporolSeeder::class,
            TipospagoSeeder::class,
            
            
            // aquí irían otros seeders que quieras, por ejemplo:
            // ProveedoresSeeder::class,php artisan db:seed

            // ProductosSeeder::class,
            // UsuarioSeeder::class,
        ]);
    }
}

