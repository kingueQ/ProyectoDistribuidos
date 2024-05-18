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
    String msgs = (String) objSesion.getAttribute("mensajes");
    if (curp == null || curp.isEmpty()) {
        // Si el correo no está presente en la sesión, redirige a la página de inicio de sesión
        response.sendRedirect("index.jsp");
    }
    objSesion.setAttribute("curp", curp);
    objSesion.setAttribute("mensajes", msgs);
    String serverAddress = "localhost"; // Dirección IP del servidor
    int serverPort = 12345; // Puerto del servidor
    SocketCliente cliente = new SocketCliente(serverAddress, serverPort);
    String respuesta = cliente.enviarMensaje("consultarExpediente!" + curp);
    String[] expediente = respuesta.split("!", -1);
    cliente = new SocketCliente(serverAddress, serverPort);
    respuesta = cliente.enviarMensaje("consultaPaciente!" + curp);
    System.out.println(respuesta);
    String[] paciente = respuesta.split("!");
    String acceso;
    if(expediente[5].equalsIgnoreCase("true")){
        acceso="publico";
    }else{
        acceso="privado";
    }
    String[] mensajes = msgs.split("-");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mi Expediente - Expediente Clínico Electrónico</title>
        <link href="css/expedientes.css" rel="stylesheet" type="text/css"/>
        <link href="css/estilos.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div id="header">
            <img src="img/logo.png" alt="Logo de la aplicación">
            <ul>
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
        <div id="content">
            <h1>Mi Expediente</h1>

            <h2>Datos del Expediente</h2>
            <p>Nombre: <%= paciente[2]%></p>
            <p>CURP: <%= paciente[1]%></p>
            <p>Fecha de Nacimiento: <%= paciente[3]%></p>
            <hr>

            <h2>Imágenes</h2>
            <table>
                <% String[] imagenes = expediente[1].split("-");
                    if (imagenes != null && imagenes.length > 1) {
                        for (int i = 1; i < imagenes.length; i++) {
                            String a = imagenes[i];
                            String encodedUrl = a.replace(" ", "%20");
                %>
                <tr>
                    <td> <img src="<%= encodedUrl%>" alt="" width="500" height="500"/></td>
                </tr>
                <% }
                    }%>
            </table>
            <hr>

            <h2>Documentos PDF</h2>
            <table>
                <% String[] documentos = expediente[2].split("-");
                    if (documentos != null && documentos.length > 1) {
                        for (int i = 1; i < documentos.length; i++) {
                            String a = documentos[i];
                            // Encuentra la última aparición de '/' para extraer el nombre del archivo
                            int lastSlashIndex = a.lastIndexOf('/');
                            String fileName = (lastSlashIndex >= 0) ? a.substring(lastSlashIndex + 1) : a;
                %>
                <tr>
                    <td> <a href="<%= a.replace(" ", "%20")%>" target="_blank"><%= fileName%></a></td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
            <hr>

            <h2>Textos</h2>
            <table>
                <% String[] textos = expediente[3].split("-");
                    if (textos != null && textos.length > 1) {
                        for (int i = 1; i < textos.length; i++) {
                            String a = textos[i];
                %>
                <tr>
                    <td> <%= a%> </td>
                </tr>
                <% }
                    }%>
            </table>
            <hr>

            <h2>Acceso del Expediente</h2>
            <p>Tipo de Acceso: <%= acceso %></p>
            <form action="CambiarAccesoServlet" method="post">
                <input type="hidden" name="curp" value="<%= curp%>">
                <button type="submit">Cambiar Acceso</button>
            </form>
            <hr>

            <h2>Médicos con Acceso</h2>
            <table>
                <%
                    String[] medicos = expediente[4].split("-");
                    if (medicos != null && medicos.length>1) {
                        for (int i=1;i<medicos.length;i++) {
                            String medico=medicos[i];
                %>
                <tr>
                    <td><%= medico%></td>
                    <td>
                        <form action="ModificarMedicosServlet" method="post">
                            <input type="hidden" name="operacion" value="eliminar">
                            <input type="hidden" name="curp" value="<%= curp%>">
                            <input type="hidden" name="cedula" value="<%= medico%>">
                            <button type="submit" name="action" value="eliminar">Eliminar</button>
                        </form>
                    </td>
                </tr>
                <%}
        }%>
            </table>
            <form action="ModificarMedicosServlet" method="post">
                <label for="cedula_medico">Cédula del Médico:</label>
                <input type="hidden" name="operacion" value="agregar">
                <input type="hidden" name="curp" value="<%= curp%>">
                <input type="text" id="cedula_medico" name="cedula">
                <button type="submit" name="action" value="agregar">Agregar Médico</button>
            </form>
        </div>
    </body>
</html>

