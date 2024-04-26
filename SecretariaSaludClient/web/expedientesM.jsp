<%-- 
    Document   : expedientesM
    Created on : 25/04/2024, 10:06:54 PM
    Author     : kingu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Expedientes - Expediente Clínico Electrónico</title>
        <link href="css/estilos.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div id="header">
            <img src="img/logo.png" alt="Logo de la aplicación">
            <ul>
                <li><a href="indexM.jsp">Inicio</a></li>
                <li><a href="expedientesM.jsp">Expedientes</a></li>
                <li><a href="informacionM.jsp">Ver Información</a></li>
                <li><a href="index.jsp">Cerrar Sesión</a></li>
            </ul>
        </div>
        <div id="content">
            <h1>Expedientes</h1>
            <table>
                <tr>
                    <th>CURP</th>
                    <th>Nombre del Paciente / Tutor</th>
                    <th>Fecha de Nacimiento</th>
                    <th>Acciones</th>
                </tr>
                <tr>
                    <td>12345678901234567</td>
                    <td>Juan Pérez</td>
                    <td>01/01/1990</td>
                    <td><button onclick="verExpediente('12345678901234567')">Ver</button></td>
                </tr>
                <!-- Agrega más filas según sea necesario -->
            </table>
        </div>
        <script>
            function verExpediente(curp) {
                // Aquí puedes agregar la lógica para manejar el evento de "ver" el expediente
                // Por ejemplo, redirigir a otra página con más detalles del expediente
                alert("Ver expediente con CURP: " + curp);
            }
        </script>
    </body>
</html>
