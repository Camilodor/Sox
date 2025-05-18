<?php
// app/Models/Despacho.php

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
        'observaciones_mer',
    ];

    // Relación con Mercancia (solo los campos necesarios)
    public function mercancias()
    {
        return $this->belongsTo(Mercancia::class, 'mercancias_id')->select(['id', 'num_remesa', 'origen_mer','nom_remitente', 'destino_mer','destin_nom', 'destin_direccion','destin_celular','peso','unidades','observaciones']); // Selección de campos específicos
    }

    // Relación con Vehiculo (solo los campos necesarios)
    public function vehiculos()
    {
        return $this->belongsTo(Vehiculo::class, 'vehiculos_id')->select(['id', 'num_placas', 'num_propietario_veh','num_propietario_cel', 'direc_propietario','ciudad_propietario']); // Selección de campos específicos
    }

    // Relación con Usuario (solo los campos necesarios)
    public function usuarios()
    {
        return $this->belongsTo(Usuario::class, 'usuarios_id')->select(['id', 'nombre_usuario', 'nombres']); // Selección de campos específicos
    }

    // Relación con Tipo de Pago
    public function tiposago()
    {
        return $this->belongsTo(TipoPago::class, 'tipo_pago_id')->select(['id', 'nombre']); // Solo los campos necesarios del tipo de pago
    }
}
