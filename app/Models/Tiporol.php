<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Tiporol extends Model
{ /**
 * App\Models\Tiporol
 *
 * @property int $id
 * @property string $nombre
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 */

    use HasFactory;

    protected $table = 'tiposrol';

    protected $fillable = ['nombre'];
}
