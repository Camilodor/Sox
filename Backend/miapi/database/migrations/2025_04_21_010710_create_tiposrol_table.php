<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateTiposrolTable extends Migration
{
    public function up()
    {
        Schema::create('tiposrol', function (Blueprint $table) {
            $table->id(); // Columna de ID
            $table->string('nombre', 50); // Columna para el nombre del rol
            $table->timestamps(); // Añade created_at y updated_at
        });
    }

    public function down()
    {
        Schema::dropIfExists('tiposrol');
    }
}
