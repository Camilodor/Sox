<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Proveedor extends Model
{/**
 * App\Models\Proveedor
 *
 * @property int $id
 * @property string $nombre
 * @property string|null $descripcion
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 */

    use HasFactory;
    protected $table = 'proveedores';
    protected $fillable =  [
        'nombre',
        'descripcion',
    ];
}
