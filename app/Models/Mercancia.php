<?php
// app/Models/Mercancia.php
// app/Models/Mercancia.php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Mercancia extends Model
{  /**
 * App\Models\Mercancia
 *
 * @property int $id
 * @property int $proveedor_id
 * @property int $usuario_id
 * @property string $fecha_ingreso
 * @property string $num_remesa
 * @property string $origen_mer
 * @property string $destino_mer
 * @property string $nom_remitente
 * @property string $doc_remitente
 * @property string $direccion_remitente
 * @property string $cel_remitente
 * @property string $destin_nom
 * @property string $destin_doc
 * @property string $destin_direccion
 * @property string $destin_celular
 * @property float $val_declarado
 * @property float $val_flete
 * @property float $val_total
 * @property float $peso
 * @property int $unidades
 * @property string|null $observaciones
 * @property int $tipopago_id
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 */

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
