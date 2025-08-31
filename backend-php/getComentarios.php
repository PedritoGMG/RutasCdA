<?php
header('Content-Type: application/json');

$rutaID = $_POST["rutaID"]; // Obtener rutaID desde la solicitud

$servidor = "localhost";
$usuario = "root";
$clave = "";
$bd = "app_rutas";

$conexion = new mysqli($servidor, $usuario, $clave, $bd);
if ($conexion->connect_error) {
    die(json_encode(["success" => false, "message" => "Error de conexión"]));
}

if (isset($rutaID)) {
    $query = "
        SELECT u.name, c.texto, c.valoracion, c.fecha 
        FROM Comentario c 
        JOIN Usuario u ON c.userID = u.userID 
        WHERE c.rutaID = ?
    ";
    $stmt = $conexion->prepare($query);
    $stmt->bind_param("i", $rutaID);
    $stmt->execute();
    $result = $stmt->get_result();

    $comentarios = [];
    while ($row = $result->fetch_assoc()) {
        $comentarios[] = $row;
    }

    echo json_encode($comentarios);
	
    $stmt->close();
} else {
    echo json_encode(["success" => false, "message" => "rutaID no proporcionado"]);
}

$conexion->close();
?>