<?php
// database/migrations/xxxx_xx_xx_create_mercancias_table.php
// database/migrations/xxxx_xx_xx_create_mercancias_table.php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
return new class extends Migration
{
    public function up()
    {
        Schema::create('mercancias', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('proveedor_id')->nullable(); // NUEVo
            $table->unsignedBigInteger('usuario_id')->nullable(); // NUEVo
            $table->date('fecha_ingreso');
            $table->string('num_remesa', 50)->nullable();
            $table->string('origen_mer', 100);
            $table->string('destino_mer', 100);

            $table->string('nom_remitente', 100);
            $table->string('doc_remitente', 50);
            $table->string('direccion_remitente', 150);
            $table->string('cel_remitente', 20);

            $table->string('destin_nom', 100);
            $table->string('destin_doc', 50);
            $table->string('destin_direccion', 150);
            $table->string('destin_celular', 20);

            $table->decimal('val_declarado', 10, 2);
            $table->decimal('val_flete', 10, 2);
            $table->decimal('val_total', 10, 2);
            $table->decimal('peso', 10, 2);
            $table->integer('unidades');
            $table->text('observaciones')->nullable();

            $table->unsignedBigInteger('tipopago_id')->nullable(); // sigue relacionado
            $table->timestamps();

            // Relaciones
            $table->foreign('tipopago_id')->references('id')->on('tipospago')->onDelete('set null');
            $table->foreign('proveedor_id')->references('id')->on('proveedores')->onDelete('set null'); // NUEVA
            $table->foreign('usuario_id')->references('id')->on('usuarios')->onDelete('set null'); // NUEVA
        });
    }

    public function down()
    {
        Schema::dropIfExists('mercancias');
    }
};
