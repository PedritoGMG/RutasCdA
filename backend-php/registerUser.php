<?php
header('Content-Type: application/json');

// Obtener los datos del formulario
$userID = $_POST["userID"];
$name = $_POST["name"];
$password = $_POST["password"];

// Configuración de la base de datos
$servidor = "localhost";
$usuario = "root";
$clave = "";
$bd = "app_rutas";

// Conexión a la base de datos
$conexion = new mysqli($servidor, $usuario, $clave, $bd);
if ($conexion->connect_error) {
    echo json_encode(["response" => false, "message" => "Error de conexión"]);
    exit;
}

// Verificar si el userID ya existe
$stmt = $conexion->prepare("SELECT userID FROM Usuario WHERE userID = ?");
if (!$stmt) {
    echo json_encode(["response" => false, "message" => "Error al preparar la consulta"]);
    exit;
}
$stmt->bind_param("s", $userID);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    echo json_encode(["response" => false, "message" => "El userID ya existe"]);
    $stmt->close();
    $conexion->close();
    exit;
}

// Si el userID no existe, proceder con el registro
$hash = password_hash($password, PASSWORD_DEFAULT);

// Llamar al procedimiento RegistrarUsuario
$stmt = $conexion->prepare("CALL RegistrarUsuario(?, ?, ?)");
if (!$stmt) {
    echo json_encode(["response" => false, "message" => "Error al preparar la consulta"]);
    exit;
}
$stmt->bind_param("sss", $userID, $name, $hash);

if ($stmt->execute()) {
    echo json_encode(["response" => true, "message" => "Usuario registrado correctamente"]);
} else {
    echo json_encode(["response" => false, "message" => "Error al registrar el usuario"]);
}

$stmt->close();
$conexion->close();
?>