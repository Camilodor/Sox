<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('productos', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('proveedores_id');
            $table->string('nombre', 255);
            $table->text('descripcion')->nullable();
            $table->timestamps();

            $table->foreign('proveedores_id')
                  ->references('id')
                  ->on('proveedores')
                  ->onDelete('cascade');
        });
    }
 
    public function down()
    {
        Schema::dropIfExists('productos');
    }
};
