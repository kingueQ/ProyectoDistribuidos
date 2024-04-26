<%-- 
    Document   : about
    Created on : 25/04/2024, 09:54:46 PM
    Author     : kingu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Contacto - Expediente Clínico Electrónico</title>
        <link rel="stylesheet" href="estilos.css">
    </head>
    <body>
        <div id="header">
            <img src="logo.png" alt="Logo de la aplicación">
            <ul>
                <li><a href="#">Ingresar</a></li>
                <li><a href="#">Registrarse como medico</a></li>
                <li><a href="#">Registrarse como paciente</a></li>
                <li><a href="about.jsp">Acerca de nosotros</a></li>
                <li><a href="contact.jsp">Contáctanos</a></li>
            </ul>
        </div>
        <div id="content">
            <h1>Contacto</h1>
            <form action="enviar_mensaje.php" method="post">
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" required><br><br>
                <label for="correo">Correo electrónico:</label>
                <input type="email" id="correo" name="correo" required><br><br>
                <label for="mensaje">Mensaje:</label><br>
                <textarea id="mensaje" name="mensaje" rows="4" cols="50" required></textarea><br><br>
                <input type="submit" value="Enviar">
            </form>
        </div>
    </body>
</html>
