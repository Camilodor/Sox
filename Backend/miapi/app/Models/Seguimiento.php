<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Seguimiento extends Model
{
    use HasFactory;

    /**
     * The attributes that are mass assignable.
     *
     * @var array<int, string>
     */
    protected $fillable = [
        'mercancias_id',
        'despacho_id',
        'entrega_id',
        'devolucion_id',
        'usuario_id',
        'evento',
        'estado',
    ];

    /**
     * Get the mercancia associated with this seguimiento.
     */
    public function mercancia()
    {
        return $this->belongsTo(Mercancia::class, 'mercancias_id');
    }

    /**
     * Get the despacho associated with this seguimiento.
     */
    public function despacho()
    {
        return $this->belongsTo(Despacho::class, 'despacho_id');
    }

    /**
     * Get the entrega associated with this seguimiento.
     */
    public function entrega()
    {
        return $this->belongsTo(Entrega::class, 'entrega_id');
    }

    /**
     * Get the devolucion associated with this seguimiento.
     */
    public function devolucion()
    {
        return $this->belongsTo(Devolucion::class, 'devolucion_id');
    }

    /**
     * Get the usuario associated with this seguimiento.
     */
    public function usuario()
    {
        return $this->belongsTo(Usuario::class, 'usuario_id');
    }
}
