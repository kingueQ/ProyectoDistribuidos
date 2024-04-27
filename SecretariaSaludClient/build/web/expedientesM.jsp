<%-- 
    Document   : expedientesM
    Created on : 25/04/2024, 10:06:54 PM
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
    String respuesta = cliente.enviarMensaje("consultarPacientes");
    System.out.println(respuesta);
    String respuesta1=respuesta.substring(0, respuesta.length()-1);
    System.out.println(respuesta1);
    String[] expedientes=respuesta1.split("!\\?");
%>
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
                <%
                                    if (expedientes != null) {
                                        for (String a : expedientes) {
                                            String[] expediente=a.split("!");
                                %>
                                <tr>
                                    <td> <%= expediente[1] %></td>
                                    <td> <%= expediente[2] %></td>
                                    <td> <%= expediente[3] %></td>
                                    <td> <%= expediente[4] %></td>
                                    <td><button onclick="verExpediente('<%= expediente[1] %>')">Ver</button></td>
                                </tr>

                                <%}
                                    }%>
                <!-- Agrega más filas según sea necesario -->
            </table>
        </div>
        <script>
            function verExpediente(curp) {
                // Aquí puedes agregar la lógica para manejar el evento de "ver" el expediente
                // Por ejemplo, redirigir a otra página con más detalles del expediente
                window.location.href = "expedienteM.jsp?curp=" + curp;
            }
        </script>
    </body>
</html>
