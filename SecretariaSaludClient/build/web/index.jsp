<%-- 
    Document   : index
    Created on : 25/04/2024, 09:20:45 PM
    Author     : kingu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bienvenido - Expediente Clínico Electrónico</title>
    <link href="css/estilos.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <header id="header">
        <img src="img/logo.png" alt="Logo de la aplicación">
        <nav>
            <ul>
                <li><a href="index.jsp">Inicio</a></li>
                <li><a href="#" onclick="mostrarModal()">Ingresar</a></li>
                <li><a href="registroMedicos.jsp">Registrarse como médico</a></li>
                <li><a href="registroPacientes.jsp">Registrarse como paciente</a></li>
                <li><a href="about.jsp">Acerca de nosotros</a></li>
                <li><a href="contact.jsp">Contáctanos</a></li>
            </ul>
        </nav>
    </header>

    <div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="cerrarModal()">&times;</span>
        <h2>Ingrese sus credenciales</h2>
        <form action="AutenticacionServlet" method="post">
            <div class="form-group">
                <label for="credencial">Cédula médica o CURP:</label>
                <input type="text" id="credencial" name="credencial" required>
            </div>
            <div class="form-group">
                <label for="contrasena">Contraseña:</label>
                <input type="password" id="contrasena" name="contrasena" required>
            </div>
            <div class="form-group">
                <input type="submit" value="Ingresar" class="btn">
            </div>
        </form>
        <p>¿No tienes una cuenta? <a href="#" onclick="mostrarRegistro()">Regístrate aquí</a></p>
    </div>
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

    <!-- Opciones de registro -->
    <section id="opciones-registro" style="display: none;">
        <h2>Regístrate como</h2>
        <ul>
            <li><a href="registroPacientes.jsp">Paciente</a></li>
            <li><a href="registroMedicos.jsp">Médico</a></li>
        </ul>
    </section>

    <script src="js/comandos.js" type="text/javascript"></script>
</body>
</html>

