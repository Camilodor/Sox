<?php
namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use PHPOpenSourceSaver\JWTAuth\Contracts\JWTSubject;
use Illuminate\Support\Facades\Hash;


class Usuario extends Authenticatable implements JWTSubject
{ /**
 * @OA\Schema(
 *     schema="Usuario",
 *     required={"nombre_usuario", "nombres", "apellidos", "tipo_documento_id", "numero_documento", "telefono", "direccion", "ciudad", "email", "contrasena", "tiposrol_id"},
 *     @OA\Property(property="nombre_usuario", type="string", example="carlos123"),
 *     @OA\Property(property="nombres", type="string", example="Carlos"),
 *     @OA\Property(property="apellidos", type="string", example="García"),
 *     @OA\Property(property="tipo_documento_id", type="integer", example=1),
 *     @OA\Property(property="numero_documento", type="string", example="1020304050"),
 *     @OA\Property(property="telefono", type="string", example="3001234567"),
 *     @OA\Property(property="direccion", type="string", example="Calle 45 #12-34"),
 *     @OA\Property(property="ciudad", type="string", example="Bogotá"),
 *     @OA\Property(property="email", type="string", example="carlos@mail.com"),
 *     @OA\Property(property="contrasena", type="string", example="123456"),
 *     @OA\Property(property="tiposrol_id", type="integer", example=2)
 * )
 */
    use Notifiable;

    protected $table = 'usuarios';
    protected $fillable = [
        'nombre_usuario',
        'nombres',
        'apellidos',
        'tipo_documento_id',
        'numero_documento',
        'telefono',
        'direccion',
        'ciudad',
        'email',
        'contrasena',
        'tiposrol_id'
    ];
    protected $hidden = ['contrasena'];

    // Encripta cada vez que se asigna
    public function setContrasenaAttribute($value)
{
    if (!empty($value) && !Hash::needsRehash($value)) {
        $this->attributes['contrasena'] = Hash::make($value);
    } else {
        $this->attributes['contrasena'] = $value;
    }
}

    // Métodos requeridos por JWTSubject:
    public function getJWTIdentifier()
    {
        return $this->getKey();
    }

    public function getJWTCustomClaims()
    {
        return [];
    }
}
