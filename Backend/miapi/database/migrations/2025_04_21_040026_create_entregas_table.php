<?php
// database/migrations/xxxx_xx_xx_create_entregas_table.php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
return new class extends Migration
{
    public function up()
    {
        Schema::create('entregas', function (Blueprint $table) {
            $table->id();
            // Relación con despacho y usuario
            $table->foreignId('mercancias_id')->constrained('mercancias')->onDelete('cascade');
            $table->foreignId('despacho_id')->constrained('despachos')->onDelete('cascade');
            $table->foreignId('usuario_id')->constrained('usuarios')->onDelete('cascade');
            
            // Otros datos
            $table->string('nombre_recibe'); // Nombre de quien recibe
            $table->string('num_celular_recibe'); // Número celular de quien recibe
            $table->text('observaciones')->nullable(); // Observaciones
            $table->date('fecha_entrega'); // Fecha de entrega
            $table->string('estado_entrega')->default('Pendiente'); // Estado de la entrega

            $table->timestamps();
        });
    }

    public function down()
    {
        Schema::dropIfExists('entregas');
    }
};
