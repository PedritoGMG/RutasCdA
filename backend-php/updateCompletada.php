<?php
header('Content-Type: application/json');

$userID = $_POST["userID"];
$actividadID = $_POST["actividadID"];

$servidor = "localhost"; 
$usuario = "root"; 
$clave = ""; 
$bd = "app_rutas"; 

$conexion = new mysqli($servidor, $usuario, $clave, $bd); 
if ($conexion->connect_error) {
    die(json_encode(["response" => false, "message" => "Error de conexión"]));
}

if (isset($userID) && isset($actividadID)) {
    $stmt = $conexion->prepare("UPDATE usuarioactividad SET completada = 1 WHERE userID = ? AND actividadID = ?");
    $stmt->bind_param("ss", $userID, $actividadID);
    $stmt->execute();

    if ($stmt->affected_rows > 0) {
        echo json_encode(["response" => true, "message" => "Completada actualizada correctamente"]);
    } else {
        echo json_encode(["response" => false, "message" => "No se encontró la actividad para el usuario"]);
    }
    $stmt->close();
} else {
    echo json_encode(["response" => false, "message" => "Datos incompletos"]);
}

$conexion->close();
?>