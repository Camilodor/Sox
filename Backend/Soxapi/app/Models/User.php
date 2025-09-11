<?php

namespace App\Models;


use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Tymon\JWTAuth\Contracts\JWTSubject;

class User extends Authenticatable implements JWTSubject
{
    use HasFactory, Notifiable;

    protected $table = 'users';

    // 🔑 Todos los campos que quieres poder crear/actualizar masivamente
    protected $fillable = [
        'nombre_usuario',
        'nombres',
        'apellidos',
        'tipo_documento_id',
        'numero_documento',
        'celular',
        'direccion',
        'ciudad',
        'email',
        'contrasena',
        'tipo_rol_id',
    ];

    // 👀 Ocultar la contraseña al devolver JSON
    protected $hidden = [
        'contrasena',
    ];

    // Relaciones
    public function tipoDocumento()
    {
        return $this->belongsTo(TipoDocumento::class, 'tipo_documento_id');
    }

    public function tipoRol()
    {
        return $this->belongsTo(TipoRol::class, 'tipo_rol_id');
    }

    // 🔑 Para que Auth use "contrasena" en lugar de "password"
    public function getAuthPassword()
    {
        return $this->contrasena;
    }

    // Métodos requeridos por JWT
    public function getJWTIdentifier()
    {
        return $this->getKey();
    }

    public function getJWTCustomClaims()
    {
        return [];
    }
}