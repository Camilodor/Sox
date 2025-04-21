<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class R_personal extends Model
{
    use HasFactory;

    protected $table = 'r_personales';

    protected $fillable = [
        'usuarios_id',
        'nombre',
        'apellido',
        'parentezco',
        'num_documento',
        'tipo_documento',
        'num_celular',
        'num_direccion',
    ];

    // Relación con usuario
    public function usuario()
    {
        return $this->belongsTo(Usuario::class, 'usuarios_id');
    }

    // Relación con tipo_documento
    public function tipoDocumento()
    {
        return $this->belongsTo(TipoDocumento::class, 'tipo_documento');
    }
}
