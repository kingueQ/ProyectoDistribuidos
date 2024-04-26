<%-- 
    Document   : miExpedienteP
    Created on : 25/04/2024, 10:07:06 PM
    Author     : kingu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <p>Nombre: Nombre del Paciente</p>
            <p>CURP: CURP del Paciente</p>
            <p>Fecha de Nacimiento: Fecha de Nacimiento del Paciente</p>
            <hr>

            <h2>Imágenes</h2>
            <table>
                <!-- Aquí se muestran las imágenes en una tabla -->
            </table>
            <hr>

            <h2>Documentos PDF</h2>
            <table>
                <!-- Aquí se muestran los documentos PDF en una tabla -->
            </table>
            <hr>

            <h2>Textos</h2>
            <ul>
                <!-- Aquí se muestran los textos en una lista -->
            </ul>
            <hr>

            <h2>Acceso del Expediente</h2>
            <p>Tipo de Acceso: Acceso Público/Privado</p>
            <button>Cambiar Acceso</button>
            <hr>

            <h2>Médicos con Acceso</h2>
            <table>
                <!-- Aquí se muestran las cédulas de los médicos con acceso en una tabla -->
            </table>
            <button>Agregar Médico</button>
            <button>Quitar Médico</button>
        </div>
    </body>
</html>

