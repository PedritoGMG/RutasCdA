<?php
header('Content-Type: application/json');

$userID = $_POST["userID"];
$rutaID = $_POST["rutaID"];

$servidor = "localhost"; 
$usuario = "root"; 
$clave = ""; 
$bd = "app_rutas"; 

$conexion = new mysqli($servidor, $usuario, $clave, $bd); 
if ($conexion->connect_error) {
    die(json_encode(["error" => "Error de conexión a la base de datos"]));
}

if (isset($userID) && isset($rutaID)) {
    // Consulta para obtener las actividades del usuario en la ruta específica
    $query = "
        SELECT 
            ua.userID,
            ua.actividadID,
            ua.completada,
            ua.escaneada
        FROM usuarioactividad ua
        WHERE ua.userID = ? 
        AND ua.actividadID IN (
            SELECT a.actividadID 
            FROM actividad a 
            WHERE a.rutaID = ?
        )
    ";
    
    $stmt = $conexion->prepare($query);
    $stmt->bind_param("si", $userID, $rutaID); // "s" para userID (string), "i" para rutaID (int)
    $stmt->execute();
    $result = $stmt->get_result();

    $actividades = [];
    while ($row = $result->fetch_assoc()) {
        // Convertir completada y escaneada a booleanos
        $row['completada'] = (bool)$row['completada'];
        $row['escaneada'] = (bool)$row['escaneada'];
        $actividades[] = $row;
    }

    echo json_encode($actividades); // Devuelve la lista de actividades
    $stmt->close();
} else {
    echo json_encode(["error" => "Datos incompletos: userID y rutaID son requeridos"]);
}

$conexion->close();
?>