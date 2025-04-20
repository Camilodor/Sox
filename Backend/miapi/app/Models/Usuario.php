<?php




namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Illuminate\Support\Facades\Hash;

class Usuario extends Authenticatable
{
    use HasFactory, Notifiable;

    protected $table = 'usuarios'; // Nombre de la tabla en la base de datos

    protected $fillable = [ // Campos que se pueden insertar de forma masiva
        'nombre_usuario',
        'nombres',
        'apellidos',
        'tipo_documento',
        'numero_documento',
        'telefono',
        'direccion',
        'ciudad',
        'email',
        'contraseña',
    ];

    // Mutador para encriptar la contraseña automáticamente
    public function setContraseñaAttribute($value)
    {
        $this->attributes['contraseña'] = Hash::make($value);
    }
}
