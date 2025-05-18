<?php
// database/migrations/xxxx_xx_xx_create_devoluciones_table.php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('devoluciones', function (Blueprint $table) {
            $table->id();
            // Relación con entregas y productos
            $table->foreignId('mercancias_id')->constrained('mercancias')->onDelete('cascade');
            $table->foreignId('usuarios_id')->constrained('usuarios')->onDelete('cascade');
            $table->foreignId('proveedores_id')->constrained('proveedores')->onDelete('cascade');
            
            // Otros datos
            $table->date('fecha_devolucion'); // Fecha de la devolución
            $table->text('motivo_devolucion'); // Motivo de la devolución
            $table->string('estado_devolucion')->default('Pendiente'); // Estado de la devolución
            $table->text('observaciones')->nullable(); // Observaciones adicionales

            $table->timestamps();
        });
    }

    public function down()
    {
        Schema::dropIfExists('devoluciones');
    }
};
