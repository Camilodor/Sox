<?php

namespace App\Models;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Mercancia extends Model
{
    use HasFactory;
    
    protected $table = 'mercancias';

    protected $fillable = [
        'proveedores_id',
        'usuarios_id',
        'fecha_ingreso',
        'numero_remesa',
        'origen_mercancia',
        'destino_mercancia',
        'nombre_remitente',
        'documento_remitente',
        'direccion_remitente',
        'celular_remitente',
        'nombre_destinatario',
        'documento_destinatario',
        'direccion_destinatario',
        'celular_destinatario',
        'valor_declarado',
        'valor_flete',
        'valor_total',
        'peso',
        'unidades',
        'observaciones',
        'tipo_pago_id'
        
    ];
    
    public function proveedores()
    {
        return $this->belongsTo(Proveedor::class, 'proveedores_id');
    }

    public function usuarios()
    {
        return $this->belongsTo(User::class, 'usuarios_id');
    }

    public function tipopago()
    {
        return $this->belongsTo(TipoPago::class, 'tipo_pago_id');
    }

    public function despachos()
    {
        return $this->hasMany(Despacho::class, 'mercancias_id');
    }

    public function entregas()
    {
        return $this->hasMany(Entrega::class, 'mercancias_id');
    }

    public function devoluciones()
    {
        return $this->hasMany(Devolucion::class, 'mercancias_id');
    }
    public function seguimientos()
{
    return $this->hasMany(Seguimiento::class, 'mercancias_id');
}
protected static function booted()
{
    static::created(function ($mercancia) {
        $mercancia->seguimientos()->create([
            'estado' => 'Ingresado a bodega',
        ]);
    });
}



}

