package servlet;

import cliente.SocketCliente;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegistroMedicosServlet", urlPatterns = {"/RegistroMedicosServlet"})
public class RegistroMedicosServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Obtener los parámetros del formulario
            String cedula = request.getParameter("cedula");
            String nombre = request.getParameter("nombre");
            String contrasena = request.getParameter("contrasena");
            String especialidad = request.getParameter("especialidad");
            
            String serverAddress = "localhost"; // Dirección IP del servidor
            int serverPort = 12345; // Puerto del servidor
            SocketCliente socketCliente = new SocketCliente(serverAddress, serverPort);
            // Ejemplo: Enviar mensaje al servidor
            String respuesta = socketCliente.enviarMensaje("insertarMedico!" + cedula + "!" + nombre + "!" + contrasena + "!" + especialidad);
            System.out.println("Respuesta del servidor: " + respuesta);
            
            if (respuesta.equals("true")) {
                // Si la respuesta es true, redirigir a indexM.jsp
                response.sendRedirect("indexM.jsp");
            } else {
                // Si la respuesta es false, redirigir a index.jsp
                response.sendRedirect("index.jsp");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para registro de médicos";
    }
}
