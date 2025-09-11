<?php

namespace App\Models;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Devolucion extends Model
{
   
use HasFactory;
    
    protected $table = 'devoluciones';

    protected $fillable = [
        'mercancias_id',
        'usuarios_id',
        'despachos_id',
        'fecha_devolucion',
        'motivo_devolucion',
        'estado_devolucion',
        'observaciones'
    ];
     public function mercancias()
    {
        return $this->belongsTo(Mercancia::class, 'mercancias_id');
    }

    public function usuarios()
    {
        return $this->belongsTo(User::class, 'usuarios_id');
    }

    public function despachos()
    {
        return $this->belongsTo(Despacho::class, 'despachos_id');
    }


    protected static function booted()
{
    static::created(function ($devolucion) {
        if($devolucion->mercancia){
        $devolucion->mercancia->seguimientos()->create([
            'estado' => 'Devolución exitosa',
        ]);
    }
    });
}


}
