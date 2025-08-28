<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Despacho extends Model
{ /**
 * App\Models\Despacho
 *
 * @property int $id
 * @property int $mercancias_id
 * @property int $vehiculos_id
 * @property int $usuarios_id
 * @property int $tipo_pago_id
 * @property string $fecha_despacho
 * @property float $negociacion
 * @property float $anticipo
 * @property float $saldo
 * @property string|null $observaciones_mer
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 */

    use HasFactory;

    protected $table = 'despachos';

    protected $fillable = [
        'mercancias_id',
        'vehiculos_id',
        'usuarios_id',
        'tipo_pago_id',
        'fecha_despacho',
        'negociacion',
        'anticipo',
        'saldo',
        'observaciones_mer',
    ];

    // RELACIONES

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

    public function vehiculo()
    {
        return $this->belongsTo(Vehiculo::class, 'vehiculos_id')
            ->select([
                'id', 
                'num_placas', 
                'num_propietario_veh',
                'num_propietario_cel', 
                'direc_propietario',
                'ciudad_propietario'
            ]);
    }

    public function usuario()
    {
        return $this->belongsTo(Usuario::class, 'usuarios_id')
            ->select([
                'id', 
                'nombre_usuario', 
                'nombres', 
                'apellidos', 
                'email'
            ]);
    }

    public function tipopago()
    {
        return $this->belongsTo(TipoPago::class, 'tipo_pago_id')
            ->select([
                'id', 
                'nombre'
            ]);
    }
}
