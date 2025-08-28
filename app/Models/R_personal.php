<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class R_personal extends Model
{ /**
 * App\Models\R_personal
 *
 * @property int $id
 * @property int $usuarios_id
 * @property string $nombre
 * @property string $apellido
 * @property string $parentezco
 * @property string $num_documento
 * @property int $tipo_documento_id
 * @property string $num_celular
 * @property string $num_direccion
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 */

    use HasFactory;

    protected $table = 'r_personales';

    protected $fillable = [
        'usuarios_id',
        'nombre',
        'apellido',
        'parentezco',
        'num_documento',
        'tipo_documento_id',
        'num_celular',
        'num_direccion',
    ];

    // Relación con usuario
    public function usuarios()
    {
        return $this->belongsTo(Usuario::class, 'usuarios_id');
    }

    // Relación con tipo_documento
    public function tipos_documento()
    {
        return $this->belongsTo(Tipodocumento::class, 'tipo_documento_id');
    }
}
