<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('r_laborales', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('usuarios_id');
            $table->string('nombre', 45);
            $table->string('apellido', 45);
            $table->string('parentezco', 45);
            $table->string('num_documento', 20);
            $table->unsignedBigInteger('tipo_documento_id'); // Relación con tiposdocumento
            $table->string('num_celular', 20);
            $table->string('num_direccion', 255);
            $table->timestamps();

            // Llaves foráneas
            $table->foreign('usuarios_id')->references('id')->on('usuarios')->onDelete('cascade');
            $table->foreign('tipo_documento_id')->references('id')->on('tipos_documento')->onDelete('cascade');
        });
    }

    public function down()
    {
        Schema::dropIfExists('r_laborales');
    }
};
