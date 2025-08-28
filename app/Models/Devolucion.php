<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Devolucion extends Model
{ /**
 * App\Models\Devolucion
 *
 * @property int $id
 * @property int $mercancias_id
 * @property int $usuarios_id
 * @property int $proveedores_id
 * @property string $fecha_devolucion
 * @property string $motivo_devolucion
 * @property string $estado_devolucion
 * @property string|null $observaciones
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 */

    use HasFactory;

    protected $table = 'devoluciones';

    protected $fillable = [
        'mercancias_id',
        'usuarios_id',
        'proveedores_id',
        'fecha_devolucion',
        'motivo_devolucion',
        'estado_devolucion',
        'observaciones'
    ];

    // relaciones correctas
    public function mercancia()
    {
        return $this->belongsTo(Mercancia::class, 'mercancias_id');
    }

    public function usuario()
    {
        return $this->belongsTo(Usuario::class, 'usuarios_id');
    }

    public function proveedor()
    {
        return $this->belongsTo(Proveedor::class, 'proveedores_id');
    }
}
