<%-- 
    Document   : registroPacientes
    Created on : 25/04/2024, 09:49:47 PM
    Author     : kingu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registro de Pacientes - Expediente Clínico Electrónico</title>
        <link rel="stylesheet" href="estilos.css">
    </head>
    <body>
        <div id="header">
            <img src="logo.png" alt="Logo de la aplicación">
            <ul>
                <li><a href="#">Ingresar</a></li>
                <li><a href="#">Registrarse</a></li>
                <li><a href="#">Acerca de nosotros</a></li>
                <li><a href="#">Contáctanos</a></li>
            </ul>
        </div>
        <div id="content">
            <h1>Registro de Pacientes</h1>
            <form action="procesar_registro.php" method="post">
                <label for="curp">CURP:</label>
                <input type="text" id="curp" name="curp" required><br><br>
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" required><br><br>
                <label for="fechaNacimiento">Fecha de Nacimiento:</label>
                <input type="date" id="fechaNacimiento" name="fechaNacimiento" required><br><br>
                <input type="checkbox" id="tutorCheck" name="tutorCheck">
                <label for="tutorCheck">¿Tiene tutor?</label><br><br>
                <div id="tutorField" style="display: none;">
                    <label for="tutorNombre">Nombre del tutor:</label>
                    <input type="text" id="tutorNombre" name="tutorNombre"><br><br>
                </div>
                <label for="contraseña">Contraseña:</label>
                <input type="password" id="contraseña" name="contraseña" required><br><br>
                <input type="submit" value="Registrar">
            </form>
        </div>
        <script>
            document.getElementById("tutorCheck").addEventListener("change", function () {
                var tutorField = document.getElementById("tutorField");
                tutorField.style.display = this.checked ? "block" : "none";
            });
        </script>
    </body>
</html>

