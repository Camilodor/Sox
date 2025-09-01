<?php
// database/migrations/xxxx_xx_xx_create_seguimientos_table.php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
return new class extends Migration
{
    public function up()
    {
        Schema::create('seguimientos', function (Blueprint $table) {
            $table->id();
            $table->foreignId('mercancias_id')->constrained('mercancias')->onDelete('cascade');  // Relación con mercancías
            $table->foreignId('despacho_id')->nullable()->constrained('despachos')->onDelete('set null'); // Relación con despacho
            $table->foreignId('entrega_id')->nullable()->constrained('entregas')->onDelete('set null'); // Relación con entrega
            $table->foreignId('devolucion_id')->nullable()->constrained('devoluciones')->onDelete('set null'); // Relación con devoluciones
            $table->foreignId('usuario_id')->constrained('usuarios')->onDelete('cascade'); // Relación con usuario

            // Información del evento
            $table->string('evento');  // Ejemplo: "Ingreso", "Despacho", "Entrega", "Devolución"
            $table->string('estado');  // Ejemplo: "en_ruta", "entregado", "devuelto", etc.
            $table->timestamps();
        });
    }

    public function down()
    {
        Schema::dropIfExists('seguimientos');
    }
};
