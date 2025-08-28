<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Seguimiento extends Model
{ /**
 * App\Models\Seguimiento
 *
 * @property int $id
 * @property int $mercancias_id
 * @property string $evento
 * @property string $estado
 * @property int $usuario_id
 * @property int|null $despacho_id
 * @property int|null $entrega_id
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 */

    use HasFactory;

    protected $table = 'seguimientos';

    protected $fillable = [
        'mercancias_id',
        'despacho_id',
        'entrega_id',
        'devolucion_id',
        'usuario_id',
        'evento',
        'estado',
    ];

    public function mercancia()
    {
        return $this->belongsTo(Mercancia::class, 'mercancias_id')
            ->select(['id', 'num_remesa', 'origen_mer', 'destino_mer', 'peso', 'unidades']);
    }

    public function despacho()
    {
        return $this->belongsTo(Despacho::class, 'despacho_id')
            ->select(['id', 'fecha_despacho', 'negociacion']);
    }

    public function entrega()
    {
        return $this->belongsTo(Entrega::class, 'entrega_id')
            ->select(['id', 'fecha_entrega', 'estado_entrega']);
    }

    public function devolucion()
    {
        return $this->belongsTo(Devolucion::class, 'devolucion_id')
            ->select(['id', 'fecha_devolucion', 'estado_devolucion']);
    }

    public function usuario()
    {
        return $this->belongsTo(Usuario::class, 'usuario_id')
            ->select(['id', 'nombre_usuario', 'email']);
    }
}
