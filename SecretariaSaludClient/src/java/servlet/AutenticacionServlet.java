package servlet;

import cliente.SocketCliente;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AutenticacionServlet", urlPatterns = {"/AutenticacionServlet"})
public class AutenticacionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Obtener los parámetros del formulario
            String cedulaCurp = request.getParameter("credencial");
            String contrasena = request.getParameter("contrasena");

            String serverAddress = "localhost"; // Dirección IP del servidor
            int serverPort = 12345; // Puerto del servidor
            SocketCliente socketCliente = new SocketCliente(serverAddress, serverPort);

            // Ejemplo: Enviar mensaje al servidor
            String respuesta = socketCliente.enviarMensaje("autenticar!" + cedulaCurp + "!" + contrasena);
            System.out.println("Respuesta del servidor: " + respuesta);

            if (respuesta.equals("true")) {
                // Si la respuesta es true, redirigir a indexM.jsp
                if(cedulaCurp.length()==18){
                    HttpSession objSesion = request.getSession(true);
                    objSesion.setAttribute("curp", cedulaCurp);
                    response.sendRedirect("indexP.jsp");
                }else{
                    HttpSession objSesion = request.getSession(true);
                    objSesion.setAttribute("cedula", cedulaCurp);
                    response.sendRedirect("indexM.jsp");
                }
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
        return "Servlet para autenticación de usuarios";
    }
}
