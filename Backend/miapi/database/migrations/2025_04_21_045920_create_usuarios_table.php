<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateUsuariosTable extends Migration
{


public function up()
{
    Schema::create('usuarios', function (Blueprint $table) {
        $table->id();
        $table->string('nombre_usuario');
        $table->string('nombres');
        $table->string('apellidos');
        $table->unsignedBigInteger('tipo_documento_id');
        $table->string('numero_documento');
        $table->string('telefono');
        $table->string('direccion');
        $table->string('ciudad');
        $table->string('email');
        $table->string('contraseña');
        $table->unsignedBigInteger('rol_id');
        $table->timestamps();

        // Definir las relaciones con las tablas
        $table->foreign('tipo_documento_id')->references('id')->on('tipos_documento');
        $table->foreign('rol_id')->references('id')->on('tipos_rol');
    });
}
 }