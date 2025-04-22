<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('tipos_documento', function (Blueprint $table) {
            $table->id(); // Columna de ID
            $table->string('nombre', 50); // Columna para el nombre del tipo de documento
            $table->timestamps(); // Añade created_at y updated_at
        });
    }

    public function down()
    {
        Schema::dropIfExists('tipos_documento');
    }
};
