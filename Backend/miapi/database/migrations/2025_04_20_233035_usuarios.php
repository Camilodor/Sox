<?php
use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration {
    public function up()
    {
        Schema::create('usuarios', function (Blueprint $table) {
            $table->id();
            $table->string('nombre_usuario')->unique();
            $table->string('nombres');
            $table->string('apellidos');
            $table->string('tipo_documento');
            $table->integer('numero_documento')->unique();
            $table->string('telefono');
            $table->string('direccion');
            $table->string('ciudad');
            $table->string('email')->unique();
            $table->string('contraseña'); // Guardar con hash en el modelo
            $table->timestamps();
        });
    }

    public function down()
    {
        Schema::dropIfExists('usuarios');
    }
};
