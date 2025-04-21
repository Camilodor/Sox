<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateTiposdocumentoTable extends Migration
{
    public function up()
    {
        Schema::create('tiposdocumento', function (Blueprint $table) {
            $table->id(); // Columna de ID
            $table->string('nombre', 50); // Columna para el nombre del tipo de documento
            $table->timestamps(); // Añade created_at y updated_at
        });
    }

    public function down()
    {
        Schema::dropIfExists('tiposdocumento');
    }
}
