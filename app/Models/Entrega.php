<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Entrega extends Model
{ /**
 * App\Models\Entrega
 *
 * @property int $id
 * @property int $mercancias_id
 * @property int $despacho_id
 * @property int $usuario_id
 * @property string $nombre_recibe
 * @property string $num_celular_recibe
 * @property string $fecha_entrega
 * @property string $estado_entrega
 * @property string|null $observaciones
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 */

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

    public function mercancia()
    {
        return $this->belongsTo(Mercancia::class, 'mercancias_id')
            ->select([
                'id',
                'num_remesa',
                'origen_mer',
                'nom_remitente',
                'destino_mer',
                'destin_nom',
                'destin_direccion',
                'destin_celular',
                'peso',
                'unidades',
                'observaciones'
            ]);
    }

    public function usuario()
    {
        return $this->belongsTo(Usuario::class, 'usuario_id')
            ->select([
                'id',
                'nombre_usuario',
                'nombres',
                'apellidos',
                'email'
            ]);
    }

    public function despacho()
    {
        return $this->belongsTo(Despacho::class, 'despacho_id')
            ->select([
                'id',
                'fecha_despacho',
                'negociacion',
                'anticipo',
                'saldo'
            ]);
    }
}
