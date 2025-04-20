<?php
use Illuminate\Http\Request;
use App\Models\Usuario;
use Illuminate\Support\Facades\Validator;

class UsuarioController extends Controller
{
    // Método para obtener todos los usuarios
    public function index()
    {
        $usuarios = Usuario::all(); // Obtiene todos los registros
        return response()->json($usuarios, 200); // Devuelve en formato JSON
    }

    // Método para crear un usuario
    public function store(Request $request)
    {
        // Validación de los datos
        $validator = Validator::make($request->all(), [
            'nombre_usuario' => 'required|unique:usuarios',
            'nombres' => 'required|string|max:255',
            'apellidos' => 'required|string|max:255',
            'tipo_documento' => 'required|string|max:10',
            'numero_documento' => 'required|integer|unique:usuarios',
            'telefono' => 'required|string|max:20',
            'direccion' => 'required|string|max:255',
            'ciudad' => 'required|string|max:100',
            'email' => 'required|email|unique:usuarios',
            'contraseña' => 'required|min:6',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 400);
        }

        $usuario = Usuario::create($request->all());

        return response()->json([
            'message' => 'Usuario creado exitosamente',
            'usuario' => $usuario
        ], 201);
    }
}
