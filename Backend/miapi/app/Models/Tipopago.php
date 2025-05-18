<?php

// app/Models/Tipopago.php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Tipopago extends Model
{
    use HasFactory;

    protected $table = 'tipospago'; // nombre exacto de la tabla

    protected $fillable = ['nombre'];
}
