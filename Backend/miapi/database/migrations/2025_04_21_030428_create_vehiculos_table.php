<?php
// database/migrations/xxxx_xx_xx_create_vehiculos_table.php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('vehiculos', function (Blueprint $table) {
            $table->id();
            $table->string('num_placas', 20);
            $table->string('nom_marca_vehiculo', 50);
            $table->string('num_propietario_veh', 50);
            $table->string('num_propietario_cel', 50);
            $table->string('direc_propietario', 50);
            $table->string('ciudad_propietario', 50);
            $table->string('num_modelo_año', 10);
            $table->string('color_vehiculo', 30);
            $table->date('fecha_vencimiento_soat')->nullable();
            $table->date('fecha_vencimiento_tecno')->nullable();
            $table->string('nom_satelital', 50)->nullable();
            $table->string('usuario_satelital', 50)->nullable();
            $table->string('contra_satelital', 50)->nullable();
            $table->decimal('capacidad_carga', 8, 2)->nullable();
            $table->timestamps();
        });
    }

    public function down()
    {
        Schema::dropIfExists('vehiculos');
    }
};
