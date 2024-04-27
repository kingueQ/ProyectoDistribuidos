<%-- 
    Document   : about
    Created on : 25/04/2024, 09:54:46 PM
    Author     : kingu
--%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contacto - Expediente Clínico Electrónico</title>
    <link href="css/estilos.css" rel="stylesheet" type="text/css">
    <style>
        #content {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h1 {
            color: #007bff;
            text-align: center;
        }
        label {
            font-weight: bold;
        }
        input[type="text"],
        input[type="email"],
        textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div id="header">
        <img src="img/logo.png" alt="Logo de la aplicación">
        <ul>
            <li><a href="index.jsp">Inicio</a></li>
            <li><a href="indexP.jsp">Ingresar</a></li>
            <li><a href="registroMedicos.jsp">Registrarse como médico</a></li>
            <li><a href="registroPacientes.jsp">Registrarse como paciente</a></li>
            <li><a href="about.jsp">Acerca de nosotros</a></li>
            <li><a href="contact.jsp">Contáctanos</a></li>
        </ul>
    </div>
    <div id="content">
        <h1>Contacto</h1>
        <form action="enviar_mensaje.php" method="post">
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" required>
            <label for="correo">Correo electrónico:</label>
            <input type="email" id="correo" name="correo" required>
            <label for="mensaje">Mensaje:</label>
            <textarea id="mensaje" name="mensaje" rows="4" cols="50" required></textarea>
            <input type="submit" value="Enviar">
        </form>
    </div>
</body>
</html>
