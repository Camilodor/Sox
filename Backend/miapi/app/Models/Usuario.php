<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Usuario extends Model
{
    use HasFactory;

    protected $table = 'usuarios';

    protected $fillable = [
        'nombre_usuario',
        'nombres',
        'apellidos',
        'tipodocumento_id', // Relación
        'numero_documento',
        'telefono',
        'direccion',
        'ciudad',
        'email',
        'contraseña',
        'tiporol_id' // Relación
    ];
}
