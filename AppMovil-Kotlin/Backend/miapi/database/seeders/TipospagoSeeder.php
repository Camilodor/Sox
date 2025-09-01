<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class TipospagoSeeder extends Seeder
{
    public function run()
    {
        DB::table('tipospago')->insert([
            ['nombre' => 'Crédito'],
            ['nombre' => 'Contraentrega'],
            ['nombre' => 'Contado'],
        ]);
    }
}
