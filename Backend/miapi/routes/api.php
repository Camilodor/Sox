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

// Obtener todos los seguimientos
Route::get('/seguimientos', [SeguimientoController::class, 'index']);

// Crear un nuevo seguimiento
Route::post('/seguimientos', [SeguimientoController::class, 'store']);

// Obtener un seguimiento específico
Route::get('/seguimientos/{id}', [SeguimientoController::class, 'show']);

// Actualizar un seguimiento
Route::put('/seguimientos/{id}', [SeguimientoController::class, 'update']);

// Eliminar un seguimiento
Route::delete('/seguimientos/{id}', [SeguimientoController::class, 'destroy']);


// 📦 ENTREGAS
Route::prefix('entregas')->group(function () {
    Route::get('/', [EntregaController::class, 'index']);        // Obtener todas las entregas
    Route::post('/', [EntregaController::class, 'store']);        // Crear una nueva entrega
    Route::get('/{id}', [EntregaController::class, 'show']);      // Mostrar una entrega específica
    Route::put('/{id}', [EntregaController::class, 'update']);    // Actualizar una entrega
    Route::delete('/{id}', [EntregaController::class, 'destroy']); // Eliminar una entrega
});

// 🔄 DEVOLUCIONES
Route::prefix('devoluciones')->group(function () {
    Route::get('/', [DevolucionController::class, 'index']);        // Obtener todas las devoluciones
    Route::post('/', [DevolucionController::class, 'store']);       // Crear una nueva devolución
    Route::get('/{id}', [DevolucionController::class, 'show']);     // Mostrar una devolución específica
    Route::put('/{id}', [DevolucionController::class, 'update']);   // Actualizar una devolución
    Route::delete('/{id}', [DevolucionController::class, 'destroy']); // Eliminar una devolución
});

Route::post('/despachos', [DespachoController::class, 'store']);
Route::get('/despachos', [DespachoController::class, 'index']);
Route::get('/despachos/{id}', [DespachoController::class, 'show']);
Route::put('/despachos/{id}', [DespachoController::class, 'update']);
Route::delete('/despachos/{id}', [DespachoController::class, 'destroy']);

Route::post('/vehiculos', [VehiculoController::class, 'store']);
Route::get('/vehiculos', [VehiculoController::class, 'index']);
Route::get('/vehiculos/{id}', [VehiculoController::class, 'show']);
Route::put('/vehiculos/{id}', [VehiculoController::class, 'update']);
Route::delete('/vehiculos/{id}', [VehiculoController::class, 'destroy']);

// Rutas para Mercancia
Route::post('/mercancias', [MercanciaController::class, 'store']);
Route::get('/mercancias', [MercanciaController::class, 'index']);
Route::get('/mercancias/{id}', [MercanciaController::class, 'show']);
Route::put('/mercancias/{id}', [MercanciaController::class, 'update']);
Route::delete('/mercancias/{id}', [MercanciaController::class, 'destroy']);

// Rutas para Tipopago
Route::post('/tipospago', [TipopagoController::class, 'store']);
Route::get('/tipospago', [TipopagoController::class, 'index']);
Route::get('/tipospago/{id}', [TipopagoController::class, 'show']);
Route::put('/tipospago/{id}', [TipopagoController::class, 'update']);
Route::delete('/tipospago/{id}', [TipopagoController::class, 'destroy']);

// Rutas para proveedores
Route::post('/proveedores', [ProveedorController::class, 'store']);
Route::get('/proveedores', [ProveedorController::class, 'index']);
Route::get('/proveedores/{id}', [ProveedorController::class, 'show']);
Route::put('/proveedores/{id}', [ProveedorController::class, 'update']);
Route::delete('/proveedores/{id}', [ProveedorController::class, 'destroy']);

// Rutas para productos
Route::post('/productos', [ProductoController::class, 'store']);
Route::get('/productos', [ProductoController::class, 'index']);
Route::get('/productos/{id}', [ProductoController::class, 'show']);
Route::put('/productos/{id}', [ProductoController::class, 'update']);
Route::delete('/productos/{id}', [ProductoController::class, 'destroy']);
// Rutas para R_personal
Route::post('/r_personales', [R_personalController::class, 'store']); // Crear referencia personal
Route::get('/r_personales', [R_personalController::class, 'index']); // Obtener todas las referencias personales
Route::get('/r_personales/{id}', [R_personalController::class, 'show']); // Obtener referencia personal por ID
Route::put('/r_personales/{id}', [R_personalController::class, 'update']); // Actualizar referencia personal
Route::delete('/r_personales/{id}', [R_personalController::class, 'destroy']); // Eliminar referencia personal

// Rutas para R_laboral
Route::post('/r_laborales', [R_laboralController::class, 'store']); // Crear referencia laboral
Route::get('/r_laborales', [R_laboralController::class, 'index']); // Obtener todas las referencias laborales
Route::get('/r_laborales/{id}', [R_laboralController::class, 'show']); // Obtener referencia laboral por ID
Route::put('/r_laborales/{id}', [R_laboralController::class, 'update']); // Actualizar referencia laboral
Route::delete('/r_laborales/{id}', [R_laboralController::class, 'destroy']); // Eliminar referencia laboral
// Rutas para Usuario
Route::post('/usuarios', [UsuarioController::class, 'store']); // Crear usuario
Route::get('/usuarios', [UsuarioController::class, 'index']); // Obtener todos los usuarios
Route::get('/usuarios/{id}', [UsuarioController::class, 'show']); // Obtener usuario por ID
Route::put('/usuarios/{id}', [UsuarioController::class, 'update']); // Actualizar usuario
Route::delete('/usuarios/{id}', [UsuarioController::class, 'destroy']); // Eliminar usuario
Route::patch('/usuarios/{id}', [UsuarioController::class, 'patch']);  

// Rutas para Tipodocumento
Route::post('/tiposdocumento', [TipodocumentoController::class, 'store']); // Crear tipo de documento
Route::get('/tiposdocumento', [TipodocumentoController::class, 'index']); // Obtener todos los tipos de documento
Route::get('/tiposdocumento/{id}', [TipodocumentoController::class, 'show']); // Obtener tipo de documento por ID
Route::put('/tiposdocumento/{id}', [TipodocumentoController::class, 'update']); // Actualizar tipo de documento
Route::delete('/tiposdocumento/{id}', [TipodocumentoController::class, 'destroy']); // Eliminar tipo de documento

// Rutas para Tiporol
Route::post('/tiposrol', [TiporolController::class, 'store']); // Crear tipo de rol
Route::get('/tiposrol', [TiporolController::class, 'index']); // Obtener todos los tipos de rol
Route::get('/tiposrol/{id}', [TiporolController::class, 'show']); // Obtener tipo de rol por ID
Route::put('/tiposrol/{id}', [TiporolController::class, 'update']); // Actualizar tipo de rol
Route::delete('/tiposrol/{id}', [TiporolController::class, 'destroy']); // Eliminar tipo de rol
