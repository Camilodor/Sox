<?php
// app/Models/Mercancia.php
// app/Models/Mercancia.php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Mercancia extends Model
{
    use HasFactory;

    protected $table = 'mercancias';

   

    protected $fillable = [
        'proveedor_id',
        'usuario_id',
        'fecha_ingreso',
        'num_remesa',
        'origen_mer',
        'destino_mer',
        'nom_remitente',
        'doc_remitente',
        'direccion_remitente',
        'cel_remitente',
        'destin_nom',
        'destin_doc',
        'destin_direccion',
        'destin_celular',
        'val_declarado',
        'val_flete',
        'val_total',
        'peso',
        'unidades',
        'observaciones',
        'tipopago_id'
    ];

    // Relaciones
    public function tipospago()
    {
        return $this->belongsTo(Tipopago::class, 'tipopago_id');
    }

    public function proveedores()
    {
        return $this->belongsTo(Proveedor::class, 'proveedor_id');
    }

    
}
