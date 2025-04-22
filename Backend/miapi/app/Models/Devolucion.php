<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Devolucion extends Model
{
    use HasFactory;
    protected $table = 'devoluciones';
    protected $fillable = [
        'mercancias_id',
        'usuarios_id',
        'proveedores_id',
        'fecha_devolucion',
        'motivo_devolucion',
        'estado_devolucion',
        'observaciones',
    ];

    // Relaciones
    public function merancias()
    {
        return $this->belongsTo(Mercancia::class, 'mercancias_id');
    }
    public function usuarios()
    {
        return $this->belongsTo(Usuario::class, 'usuarios_id');
    }

    public function proveedores()
    {
        return $this->belongsTo(Proveedor::class, 'proveedores_id');
    }
}
