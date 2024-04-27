<%-- 
    Document   : registroPacientes
    Created on : 25/04/2024, 09:49:47 PM
    Author     : kingu
--%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Pacientes - Expediente Clínico Electrónico</title>
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
        input[type="submit"],
        input[type="checkbox"] {
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
        #tutorField {
            display: none;
            margin-top: 15px; 
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
        <h1>Registro de Pacientes</h1>
        <form action="RegistroPacientesServlet" method="post">
            <label for="curp">CURP:</label>
            <input type="text" id="curp" name="curp" required>
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" required>
            <label for="fechaNacimiento">Fecha de Nacimiento:</label>
            <input type="date" id="fechaNacimiento" name="fechaNacimiento" required>
            <label for="tutorCheck">¿Tiene tutor?</label>
            <input type="checkbox" id="tutorCheck" name="tutorCheck">
            <div id="tutorField">
                <label for="tutorNombre">Nombre del tutor:</label>
                <input type="text" id="tutorNombre" name="tutorNombre">
            </div>
            <label for="contrasena">Contraseña:</label>
            <input type="password" id="contrasena" name="contrasena" required>
            <input type="submit" value="Registrar">
        </form>
    </div>
    <script>
        document.getElementById("tutorCheck").addEventListener("change", function () {
            var tutorField = document.getElementById("tutorField");
            tutorField.style.display = this.checked ? "block" : "none";
        });
    </script>
</body>
</html>
