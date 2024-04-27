<%-- 
    Document   : registroMedico
    Created on : 25/04/2024, 09:42:07 PM
    Author     : kingu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registro de Médicos - Expediente Clínico Electrónico</title>
        <link href="css/estilos.css" rel="stylesheet" type="text/css"/>
         <style>
        #content {
            margin: 20px auto;
            max-width: 600px;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        #content h1 {
            text-align: center;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"],
        input[type="password"],
        input[type="date"],
        input[type="checkbox"],
        input[type="submit"]
         {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>

    </head>
    <body>
        <div id="header">
            <img src="img/logo.png" alt="Logo de la aplicación">
            <ul>
                <li><a href="index.jsp">Inicio</a></li>
                <li><a href="#">Ingresar</a></li>
                <li><a href="registroMedicos.jsp">Registrarse como médico</a></li>
                <li><a href="registroPacientes.jsp">Registrarse como paciente</a></li>
                <li><a href="about.jsp">Acerca de nosotros</a></li>
                <li><a href="contact.jsp">Contáctanos</a></li>
            </ul>
        </div>
        <div id="content">
            <h1>Registro de Médicos</h1>
            <form action="RegistroMedicosServlet" method="post">
                <label for="cedula">Cédula:</label>
                <input type="text" id="cedula" name="cedula" required><br><br>
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" required><br><br>
                <label for="contraseña">Contraseña:</label>
                <input type="password" id="contrasena" name="contrasena" required><br><br>
                <label for="especialidad">Especialidad:</label>
                <input type="text" id="especialidad" name="especialidad" required><br><br>
                <input type="submit" value="Registrar">
            </form>
        </div>
    </body>
</html>
