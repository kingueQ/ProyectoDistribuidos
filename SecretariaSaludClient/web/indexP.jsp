<%-- 
    Document   : indexP
    Created on : 25/04/2024, 10:06:11 PM
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
    String respuesta = cliente.enviarMensaje("recibir!" + curp);
    if(respuesta==null){
        respuesta="";
    }
    String[] mensajes = respuesta.split("-");
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
            <ul>
                <li><a href="indexP.jsp">Inicio</a></li>
                <li><a href="miExpedienteP.jsp">Mi Expediente</a></li>
                <li><a href="informacionP.jsp">Ver Información</a></li>
                <li id="notification-section" class="dropdown">
                    <a href="javascript:void(0)" class="dropbtn">Notificaciones</a>
                    <div class="dropdown-content">
                        <ul id="notification-list">
                            <% for (String mensaje : mensajes) {%>
                            <li><%= mensaje%></li>
                                <% }%>
                        </ul>
                    </div>
                </li>
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
        <script>
            const notificationList = document.getElementById('notification-list');
            const notificationSection = document.getElementById('notification-section');

// Función para mostrar u ocultar la sección de notificaciones según el arreglo de mensajes
            function toggleNotificationSection(messages) {
                if (messages.length > 0) {
                    notificationSection.style.display = 'block'; // Muestra la sección de notificaciones
                    // Limpia los mensajes existentes
                    notificationList.innerHTML = '';
                    // Agrega los nuevos mensajes
                    messages.forEach(message => {
                        const li = document.createElement('li');
                        li.textContent = message;
                        notificationList.appendChild(li);
                    });
                } else {
                    notificationSection.style.display = 'none'; // Oculta la sección de notificaciones
                }
            }

// Llamada a la función toggleNotificationSection con el arreglo de mensajes obtenidos del servidor
            fetch('obtenerMensajes') // La URL de la solicitud depende de tu configuración del servidor
                    .then(response => response.json())
                    .then(data => {
                        toggleNotificationSection(data); // Muestra u oculta la sección de notificaciones según el arreglo de mensajes
                    })
                    .catch(error => console.error('Error al obtener mensajes:', error));
        </script>
    </body>
</html>

