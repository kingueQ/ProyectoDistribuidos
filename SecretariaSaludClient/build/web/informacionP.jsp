<%-- 
    Document   : informacionP
    Created on : 25/04/2024, 10:07:27 PM
    Author     : kingu
--%>

<%@page import="cliente.SocketCliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession objSesion = request.getSession(false);
    String curp = (String) objSesion.getAttribute("curp");
    if (curp == null || curp.isEmpty()) {
        // Si el correo no está presente en la sesión, redirige a la página de inicio de sesión
        response.sendRedirect("index.jsp");
    }
    objSesion.setAttribute("curp", curp);
    String serverAddress = "localhost"; // Dirección IP del servidor
    int serverPort = 12345; // Puerto del servidor
    SocketCliente cliente = new SocketCliente(serverAddress, serverPort);
    String respuesta = cliente.enviarMensaje("consultaPaciente!" + curp);
    String[] paciente = respuesta.split("!");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Información del Paciente - Expediente Clínico Electrónico</title>
        <link href="css/estilos.css" rel="stylesheet" type="text/css"/>
        <link href="css/formularios.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div id="header">
            <img src="img/logo.png" alt="Logo de la aplicación">
            <ul>
                <li><a href="indexP.jsp">Inicio</a></li>
                <li><a href="miExpedienteP.jsp">Mi Expediente</a></li>
                <li><a href="informacionP.jsp">Ver Información</a></li>
                <li><a href="index.jsp">Cerrar Sesión</a></li>
            </ul>
        </div>
        <div id="content">
            <h1>Información del Paciente</h1>
            <form>
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" value="<%= paciente[2] %>" disabled>
                <button type="button" id="btnModificarNombre">Modificar</button><br><br>
                <label for="curp">CURP:</label>
                <input type="text" id="curp" name="curp" value="<%= paciente[1] %>" disabled><br><br>
                <label for="fechaNacimiento">Fecha de Nacimiento:</label>
                <input type="text" id="fechaNacimiento" name="fechaNacimiento" value="<%= paciente[3] %>" disabled><br><br>
                <label for="tutor">Tutor:</label>
                <input type="text" id="tutor" name="tutor" value="<%= paciente[4] %>" disabled><br><br>
                <label for="password">Password:</label>
                <input type="password" id="contrasena" name="contrasena" value="<%= paciente[5] %>" disabled>
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
