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

    String respuesta = "";
    if (curp != null) {
        String serverAddress = "localhost"; // Dirección IP del servidor
        int serverPort = 12345; // Puerto del servidor
        SocketCliente cliente = new SocketCliente(serverAddress, serverPort);
        respuesta = cliente.enviarMensaje("recibir!" + curp);
        if (respuesta == null) {
            respuesta = "";
        }
    }
    String[] mensajes = respuesta.split("-");
    objSesion.setAttribute("mensajes", respuesta);
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bienvenido - Expediente Clínico Electrónico</title>
        <link href="css/estilos.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div id="header">
            <img src="img/logo.png" alt="Logo de la aplicación">
            <ul class="navbar">
                <li><a href="indexP.jsp">Inicio</a></li>
                <li><a href="miExpedienteP.jsp">Mi Expediente</a></li>
                <li><a href="informacionP.jsp">Ver Información</a></li>
                <select id="notificaciones-select">
                    <option value="">Mensajes</option>
                    <% for (String mensaje : mensajes) {%>
                    <option value="<%= mensaje%>"><%= mensaje%></option>
                    <% }%>
                </select>
                <li><a href="index.jsp">Cerrar Sesión</a></li>
            </ul>
        </div>
        <section id="welcome-message">
            <h1>Bienvenido al Expediente Clínico Electrónico</h1>
            <p>El Expediente Clínico Electrónico es una herramienta que te permite gestionar la información médica de manera segura y eficiente.</p>
        </section>
        <section id="features">
            <h2>Funcionalidades</h2>
            <ul>
                <li>Puedes visualizar los expedientes de tus pacientes.</li>
                <li>Si eres paciente, puedes ver y gestionar tu propio expediente.</li>
                <!-- Agrega más funcionalidades aquí -->
            </ul>
        </section>
    </body>
</html>
