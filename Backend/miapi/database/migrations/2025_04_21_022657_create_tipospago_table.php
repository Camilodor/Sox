<?php
// database/migrations/xxxx_xx_xx_create_tipospago_table.php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('tipospago', function (Blueprint $table) {
            $table->id();
            $table->string('nombre', 50); // crédito, contado, contraentrega
            $table->timestamps();
        });
    }

    public function down()
    {
        Schema::dropIfExists('tipospago');
    }
};
