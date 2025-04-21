<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Devolucion extends Model
{
    use HasFactory;

    protected $fillable = [
        'entregas_id',
        'productos_id',
        'fecha_devolucion',
        'motivo_devolucion',
        'estado_devolucion',
        'observaciones',
    ];

    // Relaciones
    public function entrega()
    {
        return $this->belongsTo(Entrega::class, 'entregas_id');
    }

    public function producto()
    {
        return $this->belongsTo(Mercancia::class, 'productos_id');
    }
}
