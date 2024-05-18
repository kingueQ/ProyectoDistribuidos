<%-- 
    Document   : informacionM
    Created on : 25/04/2024, 10:07:17 PM
    Author     : kingu
--%>

<%@page import="cliente.SocketCliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession objSesion = request.getSession(false);
    String cedula = (String) objSesion.getAttribute("cedula");
    if (cedula == null || cedula.isEmpty()) {
        // Si el correo no está presente en la sesión, redirige a la página de inicio de sesión
        response.sendRedirect("index.jsp");
    }
    objSesion.setAttribute("cedula", cedula);
    String serverAddress = "localhost"; // Dirección IP del servidor
    int serverPort = 12345; // Puerto del servidor
    SocketCliente cliente = new SocketCliente(serverAddress, serverPort);
    String respuesta = cliente.enviarMensaje("consultaMedico!" + cedula);
    String[] medico = respuesta.split("!");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Información del Médico - Expediente Clínico Electrónico</title>
        <link href="css/formularios.css" rel="stylesheet" type="text/css"/>
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
            <h1>Información del Médico</h1>
            <form>
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" value="<%= medico[2] %>" disabled>
                <button type="button" id="btnModificarNombre">Modificar</button><br><br>
                <label for="cedula">Cédula:</label>
                <input type="text" id="cedula" name="cedula" value="<%= medico[1] %>" disabled><br><br>
                <label for="especialidad">Especialidad:</label>
                <input type="text" id="especialidad" name="especialidad" value="<%= medico[3] %>" disabled><br><br>
                <label for="contraseña">Contraseña:</label>
                <input type="password" id="contraseña" name="contraseña" value="<%= medico[4] %>" disabled>
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
