<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('usuarios', function (Blueprint $table) {
            $table->id();
            $table->string('nombre_usuario');
            $table->string('nombres');
            $table->string('apellidos');
            $table->unsignedBigInteger('tipo_documento_id');  // FK a tipos_documento
            $table->string('numero_documento');
            $table->string('telefono');
            $table->string('direccion');
            $table->string('ciudad');
            $table->string('email');
            $table->string('contrasena');
            $table->unsignedBigInteger('tiposrol_id');       // FK a tiposrol
            $table->timestamps();

            // Clave foránea a tipos_documento (con guión bajo)
            $table->foreign('tipo_documento_id')
                  ->references('id')
                  ->on('tipos_documento')
                  ->onDelete('cascade');

            // Clave foránea a tiposrol (sin guión bajo)
            $table->foreign('tiposrol_id')
                  ->references('id')
                  ->on('tiposrol')
                  ->onDelete('cascade');
        });
    }

    public function down()
    {
        Schema::dropIfExists('usuarios');
    }
};
