<%-- 
    Document   : informacionM
    Created on : 25/04/2024, 10:07:17 PM
    Author     : kingu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Información del Médico - Expediente Clínico Electrónico</title>
        <link rel="stylesheet" href="estilos.css">
    </head>
    <body>
        <div id="header">
            <img src="logo.png" alt="Logo de la aplicación">
            <ul>
                <li><a href="#">Inicio</a></li>
                <li><a href="#">Expedientes</a></li>
                <li><a href="#">Ver Información</a></li>
                <li><a href="#">Salir</a></li>
            </ul>
        </div>
        <div id="content">
            <h1>Información del Médico</h1>
            <form>
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" value="Nombre del Médico" disabled>
                <button type="button" id="btnModificarNombre">Modificar</button><br><br>
                <label for="cedula">Cédula:</label>
                <input type="text" id="cedula" name="cedula" value="Número de Cédula" disabled><br><br>
                <label for="especialidad">Especialidad:</label>
                <input type="text" id="especialidad" name="especialidad" value="Especialidad del Médico" disabled><br><br>
                <label for="correo">Correo electrónico:</label>
                <input type="email" id="correo" name="correo" value="Correo electrónico del Médico" disabled><br><br>
                <label for="contraseña">Contraseña:</label>
                <input type="password" id="contraseña" name="contraseña" value="Contraseña del Médico" disabled>
                <button type="button" id="btnModificarContraseña">Modificar</button><br><br>
            </form>
        </div>
        <script>
            document.getElementById("btnModificarNombre").addEventListener("click", function () {
                document.getElementById("nombre").removeAttribute("disabled");
            });

            document.getElementById("btnModificarContraseña").addEventListener("click", function () {
                document.getElementById("contraseña").removeAttribute("disabled");
            });
        </script>
    </body>
</html>
