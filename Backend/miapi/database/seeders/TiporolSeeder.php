<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class TiporolSeeder extends Seeder
{
    public function run()
    {
        DB::table('tiposrol')->insert([
            ['nombre' => 'Administrador'],
            ['nombre' => 'Bodeguero'],
            ['nombre' => 'Conductor'],
            ['nombre' => 'Cliente'],
        ]);
    }
}

