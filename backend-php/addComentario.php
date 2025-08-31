<?php
header('Content-Type: application/json');

// Obtener datos desde la solicitud
$userID = $_POST["userID"];
$rutaID = $_POST["rutaID"];
$texto = $_POST["texto"];
$valoracion = $_POST["valoracion"];

$servidor = "localhost";
$usuario = "root";
$clave = "";
$bd = "app_rutas";

$conexion = new mysqli($servidor, $usuario, $clave, $bd);
if ($conexion->connect_error) {
    die(json_encode(["response" => false, "message" => "Error de conexión"]));
}

if (isset($userID) && isset($rutaID) && isset($texto) && isset($valoracion)) {
    $query = "INSERT INTO Comentario (userID, rutaID, texto, valoracion) VALUES (?, ?, ?, ?)";
    $stmt = $conexion->prepare($query);
    $stmt->bind_param("sisi", $userID, $rutaID, $texto, $valoracion);
    $stmt->execute();

    if ($stmt->affected_rows > 0) {
        echo json_encode(["response" => true, "message" => "Comentario añadido correctamente"]);
    } else {
        echo json_encode(["response" => false, "message" => "Error al añadir el comentario"]);
    }
    $stmt->close();
} else {
    echo json_encode(["response" => false, "message" => "Datos incompletos"]);
}

$conexion->close();
?>