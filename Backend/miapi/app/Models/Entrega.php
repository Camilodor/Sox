<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Entrega extends Model
{
    use HasFactory;

    protected $fillable = [
        'despacho_id',
        'usuarios_id',
        'nombre_recibe',
        'num_celular_recibe',
        'observaciones',
        'fecha_entrega',
        'estado_entrega',
    ];

    // Relaciones
    public function despacho()
    {
        return $this->belongsTo(Despacho::class, 'despacho_id');
    }

    public function usuario()
    {
        return $this->belongsTo(Usuario::class, 'usuarios_id');
    }
}
