<?php

// app/Models/Seguimiento.php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Seguimiento extends Model
{
    use HasFactory;

    protected $fillable = [
        'mercancias_id', 
        'despacho_id', 
        'entrega_id', 
        'devolucion_id',
        'usuario_id', 
        'evento', 
        'estado'
    ];

    public function mercancia()
    {
        return $this->belongsTo(Mercancia::class);
    }

    public function despacho()
    {
        return $this->belongsTo(Despacho::class);
    }

    public function entrega()
    {
        return $this->belongsTo(Entrega::class);
    }

    public function devolucion()
    {
        return $this->belongsTo(Devolucion::class);
    }

    public function usuario()
    {
        return $this->belongsTo(Usuario::class);
    }
}
