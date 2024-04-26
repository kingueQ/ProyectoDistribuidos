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
        <link href="css/estilos.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div id="header">
            <img src="img/logo.png" alt="Logo de la aplicación">
            <ul>
                <li><a href="index.jsp">Inicio</a></li>
                <li><a href="#">Ingresar</a></li>
                <li><a href="registroMedicos.jsp">Registrarse como medico</a></li>
                <li><a href="registroPacientes.jsp">Registrarse como paciente</a></li>
                <li><a href="about.jsp">Acerca de nosotros</a></li>
                <li><a href="contact.jsp">Contáctanos</a></li>
            </ul>
        </div>
        <div id="content">
            <h1>Registro de Pacientes</h1>
            <form action="RegistroPacientesServlet" method="post">
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
                <input type="password" id="contrasena" name="contrasena" required><br><br>
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

