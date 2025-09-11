<?php
namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Despacho extends Model
{
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
        'observaciones_mer'
    ];

    public function mercancia()
    {
        return $this->belongsTo(Mercancia::class, 'mercancias_id');
    }

    public function vehiculo()
    {
        return $this->belongsTo(Vehiculo::class, 'vehiculos_id');
    }

    public function usuario()
    {
        return $this->belongsTo(User::class, 'usuarios_id');
    }

    public function tipopago()
    {
        return $this->belongsTo(TipoPago::class, 'tipo_pago_id');
    }

    protected static function booted()
    {
        static::created(function ($despacho) {
            // ✅ Verificamos que haya mercancia asociada
            if ($despacho->mercancia) {
                $despacho->mercancia->seguimientos()->create([
                    'estado' => 'En camino al destino',
                ]);
            }
        });
    }
}
