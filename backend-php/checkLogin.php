<?php
header('Content-Type: application/json');

$userID = $_POST["userID"];
$password = $_POST["password"];

$servidor="localhost"; 
$usuario="root"; 
$clave=""; 
$bd="app_rutas"; 
 
$conexion= new mysqli($servidor,$usuario,$clave,$bd); 
if ($conexion->connect_error) {
    die(json_encode(["response" => false, "message" => "Error de conexión"]));
}

if (isset($userID) && isset($password)) {
    $stmt = $conexion->prepare("SELECT password FROM Usuario WHERE userID = ?");
    $stmt->bind_param("s", $userID);
    $stmt->execute();
    $stmt->bind_result($hash);
    $stmt->fetch();

    if ($hash && password_verify($password, $hash)) {
        echo json_encode(["response" => true, "message" => "Login correcto"]);
    } else {
        echo json_encode(["response" => false, "message" => "Usuario o contraseña incorrectos"]);
    }
    $stmt->close();
} else {
    echo json_encode(["response" => false, "message" => "Datos incompletos"]);
}

$conexion->close();
?>