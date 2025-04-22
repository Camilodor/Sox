<?php
// database/migrations/xxxx_xx_xx_create_despachos_table.php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('despachos', function (Blueprint $table) {
            $table->id();
            // Relación con mercancia, vehiculo y usuario
            $table->foreignId('mercancias_id')->constrained('mercancias')->onDelete('cascade');
            $table->foreignId('vehiculos_id')->constrained('vehiculos')->onDelete('cascade');
            $table->foreignId('usuarios_id')->constrained('usuarios');

            // Relación con tipo de pago
            $table->foreignId('tipo_pago_id')->constrained('tipospago')->onDelete('cascade');
            
            // Otros datos
            $table->date('fecha_despacho'); // Fecha en que se crea el despacho
            $table->decimal('negociacion', 10, 2); // Valor de la negociación
            $table->decimal('anticipo', 10, 2); // Valor del anticipo
            $table->decimal('saldo', 10, 2); // Saldo restante
            $table->text('observaciones_mer')->nullable(); // Observaciones adicionales

            $table->timestamps();
        });
    }

    public function down()
    {
        Schema::dropIfExists('despachos');
    }
};
