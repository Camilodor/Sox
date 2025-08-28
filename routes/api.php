<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\UsuarioController;
use App\Http\Controllers\TipodocumentoController;
use App\Http\Controllers\TiporolController;
use App\Http\Controllers\R_personalController;
use App\Http\Controllers\R_laboralController;
use App\Http\Controllers\ProveedorController;
use App\Http\Controllers\ProductoController;
use App\Http\Controllers\TipopagoController;
use App\Http\Controllers\MercanciaController;
use App\Http\Controllers\VehiculoController;
use App\Http\Controllers\DespachoController;
use App\Http\Controllers\EntregaController;
use App\Http\Controllers\DevolucionController;
use App\Http\Controllers\SeguimientoController;
use App\Http\Controllers\AuthController;


// 🔐 RUTAS PROTEGIDAS POR TOKEN JWT
Route::middleware('auth:api')->group(function () {

    // Perfil y logout
    Route::get('/me', [AuthController::class, 'me']);
    Route::post('/logout', [AuthController::class, 'logout']);

    // 🧑‍💼 Usuarios
    Route::apiResource('usuarios', UsuarioController::class);
    Route::patch('/usuarios/{id}', [UsuarioController::class, 'patch']);

    // 🧾 Tipos de documento
    Route::apiResource('tiposdocumento', TipodocumentoController::class);

    // 🎭 Tipos de rol
    Route::apiResource('tiposrol', TiporolController::class);

    // 👤 Referencias personales
    Route::apiResource('r_personales', R_personalController::class);

    // 💼 Referencias laborales
    Route::apiResource('r_laborales', R_laboralController::class);

    // 🧑‍🔧 Proveedores
    Route::apiResource('proveedores', ProveedorController::class);

    // 📦 Productos
    Route::apiResource('productos', ProductoController::class);

    // 💰 Tipos de pago
    Route::apiResource('tipospago', TipopagoController::class);

    // 📦 Mercancías
    Route::apiResource('mercancias', MercanciaController::class);

    // 🚗 Vehículos
    Route::apiResource('vehiculos', VehiculoController::class);

    // 🚚 Despachos
    Route::apiResource('despachos', DespachoController::class);

    // 🏁 Entregas
    Route::apiResource('entregas', EntregaController::class);

    // 🔁 Devoluciones
    Route::apiResource('devoluciones', DevolucionController::class);

    // 📊 Seguimientos
    Route::apiResource('seguimientos', SeguimientoController::class);
});


Route::post('/login', [AuthController::class, 'login']);
Route::middleware('auth:api')->post('/logout', [AuthController::class, 'logout']);

Route::middleware('auth:api')->get('/me', [AuthController::class, 'me']);


// solo para admin
