<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Tiporol extends Model
{
    use HasFactory;

    protected $table = 'tiposrol';

    protected $fillable = ['nombre'];
}
