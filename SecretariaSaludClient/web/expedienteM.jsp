<%-- 
    Document   : expedienteM
    Created on : 25/04/2024, 10:07:06 PM
    Author     : kingu
--%>

<%@page import="cliente.SocketCliente"%>
<%@page import="java.io.File"%>
<%@page import="java.nio.file.Paths"%>
<%@page import="javax.servlet.http.Part"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>

<%@page import="javax.servlet.http.HttpSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession objSesion = request.getSession(false);
    String cedula = (String) objSesion.getAttribute("cedula");
    if (cedula == null || cedula.isEmpty()) {
        // Si el correo no está presente en la sesión, redirige a la página de inicio de sesión
        response.sendRedirect("index.jsp");
    }
    objSesion.setAttribute("cedula", cedula);
    String curp = request.getParameter("curp");
    System.out.println(curp);
    String serverAddress = "localhost"; // Dirección IP del servidor
    int serverPort = 12345; // Puerto del servidor
    SocketCliente cliente = new SocketCliente(serverAddress, serverPort);
    String respuesta = cliente.enviarMensaje("consultarExpediente!" + curp);
    String[] expediente = respuesta.split("!", -1);
    cliente = new SocketCliente(serverAddress, serverPort);
    respuesta = cliente.enviarMensaje("consultaPaciente!" + curp);
    System.out.println(respuesta);
    String[] paciente = respuesta.split("!");
    boolean access=false;
    if(expediente[4]!=null&&expediente[4].equals(cedula)){
        access=true;
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Expediente Clínico Electrónico - </title>
        <link href="css/expedientes.css" rel="stylesheet" type="text/css"/>
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
            <h1>Expediente Electrónico</h1>

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
            <% if(access){
                
            %>
            <form action="SubirImagenServlet" method="post" enctype="multipart/form-data">
                <input type="file" name="imagen" accept="image/*">
                <input type="hidden" name="cedula" value="<%= cedula%>">
                <input type="hidden" name="curp" value="<%= curp%>">
                <button type="submit">Subir Imagen</button>
            </form>
            <hr>
            <% } %>
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

            <% if(access){
                
            %>
            <form action="SubirPdfServlet" method="post" enctype="multipart/form-data">
                <input type="file" name="document" accept=".pdf">
                <input type="hidden" name="cedula" value="<%= cedula%>">
                <input type="hidden" name="curp" value="<%= curp%>">
                <button type="submit">Subir Documento</button>
            </form>
            <hr>
            <% } %>
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
            <% if(access){
                
            %>
            <form action="SubirTextoServlet" method="post" onsubmit="return validarTexto()">
                <textarea id="texto" name="texto" rows="4" cols="50"></textarea><br>
                <input type="hidden" name="cedula" value="<%= cedula%>">
                <input type="hidden" name="curp" value="<%= curp%>">
                <button type="submit">Agregar Texto</button>
            </form>
            <hr>
            <% } %>
            <h2>Medicos autorizados</h2>
            
            <table>
                <% String[] medicos = expediente[4].split("-");
                    if (medicos != null && medicos.length > 1) {
                        for (int i = 1; i < medicos.length; i++) {
                            String a = medicos[i];
                %>
                <tr>
                    <td> <%= a%> </td>
                </tr>
                <% }
                    }%>
            </table>
            <form action="SolicitarAcceso" method="post"">
                <input type="hidden" name="cedula" value="<%= cedula%>">
                <input type="hidden" name="curp" value="<%= curp%>">
                <button type="submit">Solicitar Acceso</button>
            </form>
            <hr>
        </div>
        <script>
            function validarTexto() {
                var texto = document.getElementById("texto").value.trim();
                if (texto === "") {
                    alert("Por favor, ingresa un texto antes de enviar el formulario.");
                    return false; // Evita que se envíe el formulario
                }
                return true; // Permite que se envíe el formulario si se ingresó texto
            }
        </script>
    </body>
</html>
