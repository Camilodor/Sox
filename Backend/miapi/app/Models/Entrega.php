<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Entrega extends Model
{
    use HasFactory;
    protected $table = 'entregas';
    protected $fillable = [
        'mercancias_id',
        'despacho_id',
        'usuario_id',
        'nombre_recibe',
        'num_celular_recibe',
        'observaciones',
        'fecha_entrega',
        'estado_entrega',

        
    ];

    // Relaciones
    public function mercancias()
    {
        return $this->belongsTo(Mercancia::class, 'mercancias_id');
    }
    public function despachos()
    {
        return $this->belongsTo(Despacho::class, 'despacho_id');
    }

    public function usuarios()
    {
        return $this->belongsTo(Usuario::class, 'usuario_id');
    }
}
