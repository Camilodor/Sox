<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\UsuarioController;

// Definir rutas para la API de usuarios
Route::post('/usuarios', [UsuarioController::class, 'store']); // Crear usuario
Route::get('/usuarios', [UsuarioController::class, 'index']); // Obtener todos los usuarios
Route::get('/usuarios/{id}', [UsuarioController::class, 'show']); // Obtener usuario por ID
Route::put('/usuarios/{id}', [UsuarioController::class, 'update']); // Actualizar usuario
Route::delete('/usuarios/{id}', [UsuarioController::class, 'destroy']); // Eliminar usuario

