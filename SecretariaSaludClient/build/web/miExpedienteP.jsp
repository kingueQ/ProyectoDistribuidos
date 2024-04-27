<%-- 
    Document   : miExpedienteP
    Created on : 25/04/2024, 10:07:06 PM
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
    String respuesta = cliente.enviarMensaje("consultarExpediente!" + curp);
    String[] expediente = respuesta.split("!");
    cliente = new SocketCliente(serverAddress, serverPort);
    respuesta = cliente.enviarMensaje("consultaPaciente!" + curp);
    System.out.println(respuesta);
    String[] paciente = respuesta.split("!");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mi Expediente - Expediente Clínico Electrónico</title>
        <link href="css/estilos.css" rel="stylesheet" type="text/css"/>
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
            <h1>Mi Expediente</h1>

            <h2>Datos del Expediente</h2>
            <p>Nombre: <%= paciente[2] %></p>
            <p>CURP: <%= paciente[1] %></p>
            <p>Fecha de Nacimiento: <%= paciente[3] %></p>
            <hr>

            <h2>Imágenes</h2>
            <table>
                <%
                    String[] imagenes = expediente[1].split("-");
                                    if (imagenes != null) {
                                        for (String a : imagenes) {
                                %>
                                <tr>
                                    <td> <%= a %></td>
                                </tr>

                                <%}
                                    }%>
            </table>
            <hr>

            <h2>Documentos PDF</h2>
            <table>
                <%
                    String[] documentos = expediente[2].split("-");
                                    if (documentos != null) {
                                        for (String a : documentos) {
                                %>
                                <tr>
                                    <td> <%= a %></td>
                                </tr>

                                <%}
                                    }%>
            </table>
            <hr>

            <h2>Textos</h2>
            <ul>
                <%
                    String[] textos = expediente[3].split("-");
                                    if (textos != null) {
                                        for (String a : textos) {
                                %>
                                <tr>
                                    <td> <%= a %></td>
                                </tr>

                                <%}
                                    }%>
            </ul>
            <hr>

            <h2>Acceso del Expediente</h2>
            <p>Tipo de Acceso: <%= expediente[4] %></p>
            <button>Cambiar Acceso</button>
            <hr>

            <h2>Médicos con Acceso</h2>
            <table>
                <%
                    String[] medicos = expediente[5].split("-");
                                    if (medicos != null) {
                                        for (String a : medicos) {
                                %>
                                <tr>
                                    <td> <%= a %></td>
                                </tr>

                                <%}
                                    }%>
            </table>
            <button>Agregar Médico</button>
            <button>Quitar Médico</button>
        </div>
    </body>
</html>

