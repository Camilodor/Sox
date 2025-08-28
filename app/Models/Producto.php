<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Producto extends Model
{ /**
 * App\Models\Producto
 *
 * @property int $id
 * @property int $proveedores_id
 * @property string $nombre
 * @property string|null $descripcion
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 */

    use HasFactory;
    protected $table = 'productos';
    protected $fillable = [
        'proveedores_id',
        'nombre',
        'descripcion',
    ];


    public function proveedores()
    {
        return $this->belongsTo(Proveedor::class, 'proveedores_id');
    }

}
