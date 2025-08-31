-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-02-2025 a las 13:36:11
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `app_rutas`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `RegistrarUsuario` (IN `p_userID` VARCHAR(50), IN `p_name` VARCHAR(100), IN `p_password` VARCHAR(100))   BEGIN
    -- 1. Insertar el usuario en la tabla Usuario
    INSERT INTO Usuario (userID, name, password)
    VALUES (p_userID, p_name, p_password);

    -- 2. Relacionar al usuario con todas las rutas existentes
    INSERT INTO UsuarioRuta (userID, rutaID, completada)
    SELECT p_userID, rutaID, FALSE
    FROM Ruta;

    -- 3. Relacionar al usuario con todas las actividades existentes
    INSERT INTO UsuarioActividad (userID, actividadID, completada, escaneada)
    SELECT p_userID, actividadID, FALSE, FALSE
    FROM Actividad;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `actividad`
--

CREATE TABLE `actividad` (
  `actividadID` int(11) NOT NULL,
  `rutaID` int(11) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `actividad`
--

INSERT INTO `actividad` (`actividadID`, `rutaID`, `nombre`) VALUES
(1, 1, 'La Puerta'),
(2, 1, 'El Paisaje'),
(3, 1, 'La Casopla'),
(4, 1, 'El Río'),
(5, 2, 'La Puerta'),
(6, 2, 'El Paisaje'),
(7, 2, 'La Casopla'),
(8, 2, 'El Río'),
(9, 3, 'La Puerta'),
(10, 3, 'El Paisaje'),
(11, 3, 'La Casopla'),
(12, 3, 'El Río'),
(13, 4, 'La Puerta'),
(14, 4, 'El Paisaje'),
(15, 4, 'La Casopla'),
(16, 4, 'El Río');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentario`
--

CREATE TABLE `comentario` (
  `comentarioID` int(11) NOT NULL,
  `userID` varchar(50) DEFAULT NULL,
  `rutaID` int(11) DEFAULT NULL,
  `texto` text DEFAULT NULL,
  `valoracion` int(11) DEFAULT NULL CHECK (`valoracion` between 1 and 5),
  `fecha` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `comentario`
--

INSERT INTO `comentario` (`comentarioID`, `userID`, `rutaID`, `texto`, `valoracion`, `fecha`) VALUES
(1, 'pedro_manuel_', 3, 'Un poco ñe', 2, '2025-02-27 20:03:28'),
(2, 'manoloGroximus', 3, 'ta ben', 4, '2025-02-27 20:03:46'),
(3, 'pedro_manuel_', 1, 'Esta chulo supongo', 3, '2025-02-27 21:40:30'),
(4, 'pedro_manuel_', 1, 'Una ruta excelente para los amantes de la naturaleza. Paisajes hermosos, senderos desafiantes y un ambiente tranquilo. Perfecta para desconectar y disfrutar del aire libre. Muy recomendable para todos.', 5, '2025-02-27 21:47:01'),
(5, 'pedro_manuel_', 4, 'La ruta no estuvo a la altura de mis expectativas. Los senderos estaban mal señalizados, lo que causó confusión. Además, la dificultad no estaba bien indicada y algunas partes eran peligrosas. No la recomendaría para principiantes.', 2, '2025-02-27 21:48:55'),
(6, 'pedro_manuel_', 4, 'No me gustó la ruta. El camino estaba lleno de barro y los paisajes no eran tan espectaculares como esperaba. La señalización también dejaba mucho que desear. No volveré a hacerla.', 1, '2025-02-27 21:49:08'),
(7, 'pedro_manuel_', 3, 'La ruta estaba bien en general, pero se notaba algo descuidada. Con un poco más de mantenimiento sería mucho mejor.', 1, '2025-02-28 12:34:44'),
(8, 'pedro_manuel_', 3, 'La experiencia fue aceptable, aunque algunos tramos resultaron monótonos. Ideal para principiantes, pero quienes busquen más aventura pueden quedar insatisfechos.', 2, '2025-02-28 13:13:34');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ruta`
--

CREATE TABLE `ruta` (
  `rutaID` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ruta`
--

INSERT INTO `ruta` (`rutaID`, `nombre`) VALUES
(1, 'fitness'),
(2, 'cuentos'),
(3, 'acertijos'),
(4, 'matematicas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `userID` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`userID`, `name`, `password`) VALUES
('manoloGroximus', 'Manolete El Grox', '$2y$10$g2l3zYJxu5aaOTH8QpqPnuGygRKEx4INlBiavoPOvKbglOh.fd/yS'),
('nieves987', 'Nieves', '$2y$10$OEQ7wKZoiERIghGxsBgZjun52Bqxa7r6NBk0SR/XRlt.OskJXYsUy'),
('pedro_manuel_', 'Pedro Manuel', '$2y$10$jo4bwjiYetv3cCdOC8H.xeGh0SxcMCntIbGq4Ce5zntnJDsqiQ3n6');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarioactividad`
--

CREATE TABLE `usuarioactividad` (
  `userID` varchar(50) NOT NULL,
  `actividadID` int(11) NOT NULL,
  `completada` tinyint(1) DEFAULT 0,
  `escaneada` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarioactividad`
--

INSERT INTO `usuarioactividad` (`userID`, `actividadID`, `completada`, `escaneada`) VALUES
('manoloGroximus', 1, 0, 0),
('manoloGroximus', 2, 0, 0),
('manoloGroximus', 3, 0, 0),
('manoloGroximus', 4, 0, 0),
('manoloGroximus', 5, 0, 0),
('manoloGroximus', 6, 0, 0),
('manoloGroximus', 7, 0, 0),
('manoloGroximus', 8, 0, 0),
('manoloGroximus', 9, 0, 0),
('manoloGroximus', 10, 0, 0),
('manoloGroximus', 11, 0, 0),
('manoloGroximus', 12, 0, 0),
('manoloGroximus', 13, 0, 0),
('manoloGroximus', 14, 0, 0),
('manoloGroximus', 15, 0, 0),
('manoloGroximus', 16, 0, 0),
('nieves987', 1, 0, 0),
('nieves987', 2, 0, 0),
('nieves987', 3, 0, 0),
('nieves987', 4, 0, 0),
('nieves987', 5, 0, 0),
('nieves987', 6, 0, 0),
('nieves987', 7, 0, 0),
('nieves987', 8, 0, 0),
('nieves987', 9, 0, 0),
('nieves987', 10, 0, 0),
('nieves987', 11, 0, 0),
('nieves987', 12, 0, 0),
('nieves987', 13, 0, 0),
('nieves987', 14, 0, 0),
('nieves987', 15, 0, 0),
('nieves987', 16, 0, 0),
('pedro_manuel_', 1, 1, 1),
('pedro_manuel_', 2, 0, 0),
('pedro_manuel_', 3, 1, 1),
('pedro_manuel_', 4, 1, 1),
('pedro_manuel_', 5, 0, 0),
('pedro_manuel_', 6, 0, 0),
('pedro_manuel_', 7, 0, 1),
('pedro_manuel_', 8, 0, 0),
('pedro_manuel_', 9, 1, 1),
('pedro_manuel_', 10, 0, 0),
('pedro_manuel_', 11, 1, 1),
('pedro_manuel_', 12, 0, 0),
('pedro_manuel_', 13, 0, 1),
('pedro_manuel_', 14, 0, 0),
('pedro_manuel_', 15, 1, 1),
('pedro_manuel_', 16, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarioruta`
--

CREATE TABLE `usuarioruta` (
  `userID` varchar(50) NOT NULL,
  `rutaID` int(11) NOT NULL,
  `completada` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarioruta`
--

INSERT INTO `usuarioruta` (`userID`, `rutaID`, `completada`) VALUES
('manoloGroximus', 1, 0),
('manoloGroximus', 2, 0),
('manoloGroximus', 3, 0),
('manoloGroximus', 4, 0),
('nieves987', 1, 0),
('nieves987', 2, 0),
('nieves987', 3, 0),
('nieves987', 4, 0),
('pedro_manuel_', 1, 0),
('pedro_manuel_', 2, 0),
('pedro_manuel_', 3, 0),
('pedro_manuel_', 4, 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `actividad`
--
ALTER TABLE `actividad`
  ADD PRIMARY KEY (`actividadID`),
  ADD KEY `actividad_ibfk_1` (`rutaID`);

--
-- Indices de la tabla `comentario`
--
ALTER TABLE `comentario`
  ADD PRIMARY KEY (`comentarioID`),
  ADD KEY `comentario_ibfk_1` (`userID`),
  ADD KEY `comentario_ibfk_2` (`rutaID`);

--
-- Indices de la tabla `ruta`
--
ALTER TABLE `ruta`
  ADD PRIMARY KEY (`rutaID`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`userID`);

--
-- Indices de la tabla `usuarioactividad`
--
ALTER TABLE `usuarioactividad`
  ADD PRIMARY KEY (`userID`,`actividadID`),
  ADD KEY `usuarioactividad_ibfk_2` (`actividadID`);

--
-- Indices de la tabla `usuarioruta`
--
ALTER TABLE `usuarioruta`
  ADD PRIMARY KEY (`userID`,`rutaID`),
  ADD KEY `usuarioruta_ibfk_2` (`rutaID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `actividad`
--
ALTER TABLE `actividad`
  MODIFY `actividadID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `comentario`
--
ALTER TABLE `comentario`
  MODIFY `comentarioID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `ruta`
--
ALTER TABLE `ruta`
  MODIFY `rutaID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `actividad`
--
ALTER TABLE `actividad`
  ADD CONSTRAINT `actividad_ibfk_1` FOREIGN KEY (`rutaID`) REFERENCES `ruta` (`rutaID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `comentario`
--
ALTER TABLE `comentario`
  ADD CONSTRAINT `comentario_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `usuario` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comentario_ibfk_2` FOREIGN KEY (`rutaID`) REFERENCES `ruta` (`rutaID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuarioactividad`
--
ALTER TABLE `usuarioactividad`
  ADD CONSTRAINT `usuarioactividad_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `usuario` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usuarioactividad_ibfk_2` FOREIGN KEY (`actividadID`) REFERENCES `actividad` (`actividadID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuarioruta`
--
ALTER TABLE `usuarioruta`
  ADD CONSTRAINT `usuarioruta_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `usuario` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usuarioruta_ibfk_2` FOREIGN KEY (`rutaID`) REFERENCES `ruta` (`rutaID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
