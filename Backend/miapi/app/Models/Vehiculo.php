<?php

// app/Models/Vehiculo.php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Vehiculo extends Model
{
    use HasFactory;

    protected $table = 'vehiculos';

    protected $fillable = [
        'num_placas',
        'nom_marca_vehiculo',
        'num_propietario_veh',
        'num_propietario_cel',
        'direc_propietario',
        'ciudad_propietario',
        'num_modelo_año',
        'color_vehiculo',
        'fecha_vencimiento_soat',
        'fecha_vencimiento_tecno',
        'nom_satelital',
        'usuario_satelital',
        'contra_satelital',
        'capacidad_carga'
    ];
}

