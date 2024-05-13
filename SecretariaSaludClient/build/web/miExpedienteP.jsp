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
    String acceso;
    if(expediente[5].equalsIgnoreCase("true")){
        acceso="publico";
    }else{
        acceso="privado";
    }
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
                %>
                <tr>
                    <td> <img src="<%= a%>" alt="" width="500" height="500"/></td>
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
                %>
                <tr>
                    <td> <a href="<%= a%>" target="_blank">Abrir PDF</a></td>
                </tr>
                <% }
                    }%>
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
                    if (medicos != null) {
                        for (String medico : medicos) {
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

