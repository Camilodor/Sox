<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class TipodocumentoSeeder extends Seeder
{
    public function run()
    {
        DB::table('tipos_documento')->insert([
            ['nombre' => 'Cédula de Ciudadanía'],
            ['nombre' => 'Tarjeta de Identidad'],
            ['nombre' => 'Pasaporte'],
            ['nombre' => 'Cédula de Extranjería'],
        ]);
    }
}
