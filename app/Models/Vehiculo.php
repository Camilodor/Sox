<?php

// app/Models/Vehiculo.php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Vehiculo extends Model
{ 
/**
 * @OA\Schema(
 *     schema="Vehiculo",
 *     type="object",
 *     required={"num_placas", "nom_marca_vehiculo", "num_propietario_veh", "num_propietario_cel", "direc_propietario", "ciudad_propietario", "num_modelo_ano", "color_vehiculo"},
 *     @OA\Property(property="id", type="integer", example=1),
 *     @OA\Property(property="num_placas", type="string", example="ABC123"),
 *     @OA\Property(property="nom_marca_vehiculo", type="string", example="Toyota"),
 *     @OA\Property(property="num_propietario_veh", type="string", example="Carlos Pérez"),
 *     @OA\Property(property="num_propietario_cel", type="string", example="3001234567"),
 *     @OA\Property(property="direc_propietario", type="string", example="Cra 1 #2-34"),
 *     @OA\Property(property="ciudad_propietario", type="string", example="Bogotá"),
 *     @OA\Property(property="num_modelo_ano", type="string", example="2022"),
 *     @OA\Property(property="color_vehiculo", type="string", example="Rojo"),
 *     @OA\Property(property="fecha_vencimiento_soat", type="string", format="date", example="2025-01-01"),
 *     @OA\Property(property="fecha_vencimiento_tecno", type="string", format="date", example="2025-06-01"),
 *     @OA\Property(property="nom_satelital", type="string", example="GeoTrack"),
 *     @OA\Property(property="usuario_satelital", type="string", example="usuario123"),
 *     @OA\Property(property="contra_satelital", type="string", example="clave123"),
 *     @OA\Property(property="capacidad_carga", type="number", format="float", example=12.5),
 *     @OA\Property(property="created_at", type="string", format="date-time"),
 *     @OA\Property(property="updated_at", type="string", format="date-time")
 * )
 */
    use HasFactory;

    protected $table = 'vehiculos';

    protected $fillable = [
        'num_placas',
        'nom_marca_vehiculo',
        'num_propietario_veh',
        'num_propietario_cel',
        'direc_propietario',
        'ciudad_propietario',
        'num_modelo_ano',
        'color_vehiculo',
        'fecha_vencimiento_soat',
        'fecha_vencimiento_tecno',
        'nom_satelital',
        'usuario_satelital',
        'contra_satelital',
        'capacidad_carga'
    ];
}

