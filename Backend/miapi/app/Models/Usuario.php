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
        'tipo_documento_id', // Relación
        'numero_documento',
        'telefono',
        'direccion',
        'ciudad',
        'email',
        'contrasena',
        'tiposrol_id' // Relación
    ];
}
