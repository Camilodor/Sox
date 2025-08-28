<?php

// app/Models/Tipopago.php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Tipopago extends Model
{ /**
 * App\Models\Tipopago
 *
 * @property int $id
 * @property string $nombre
 * @property \Illuminate\Support\Carbon|null $created_at
 * @property \Illuminate\Support\Carbon|null $updated_at
 */

    use HasFactory;

    protected $table = 'tipospago'; // nombre exacto de la tabla

    protected $fillable = ['nombre'];
}
