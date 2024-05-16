/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
@WebServlet("/ServletBalanceo")
public class ServletBalanceo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nginxUrl = "http://localhost";

        try {
            // Crear una conexión HTTP a la URL de Nginx
            URL url = new URL(nginxUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Realizar la solicitud GET
            connection.setRequestMethod("GET");

            // Obtener el código de respuesta
            int responseCode = connection.getResponseCode();

            // Verificar si Nginx está funcionando (código de respuesta 200 indica éxito)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response.getWriter().println("Nginx está funcionando correctamente.");

                // También podemos verificar a cuál servidor Tomcat está dirigiendo la solicitud
                String serverHeader = connection.getHeaderField("X-Backend-Server");
                if (serverHeader != null && !serverHeader.isEmpty()) {
                    response.getWriter().println("Solicitud dirigida al servidor Tomcat: " + serverHeader);
                }
            } else {
                response.getWriter().println("No se pudo conectar a Nginx. Código de respuesta: " + responseCode);
            }

            // Cerrar la conexión
            connection.disconnect();
        } catch (Exception e) {
            response.getWriter().println("Error al intentar conectar con Nginx: " + e.getMessage());
        }
    }
}
