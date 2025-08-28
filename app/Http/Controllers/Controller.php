<?php

namespace App\Http\Controllers;

abstract class Controller
{
 /**
 * @OA\SecurityScheme(
 *     type="http",
 *     description="Autenticación con token JWT usando Bearer",
 *     name="Authorization",
 *     in="header",
 *     scheme="bearer",
 *     bearerFormat="JWT",
 *     securityScheme="bearerAuth"
 * )
 */   //
}
