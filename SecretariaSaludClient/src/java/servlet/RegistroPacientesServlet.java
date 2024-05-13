import cliente.SocketCliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegistroPacientesServlet", urlPatterns = {"/RegistroPacientesServlet"})
public class RegistroPacientesServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Obtener los parámetros del formulario
            String curp = request.getParameter("curp");
            String nombre = request.getParameter("nombre");
            String fechaNacimientoString = request.getParameter("fechaNacimiento");
            String tieneTutor = request.getParameter("tutorCheck");
            String tutorNombre = request.getParameter("tutorNombre");
            String contrasena = request.getParameter("contrasena");
            
            // Reformatear la fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = null;
            try {
                fechaNacimiento = dateFormat.parse(fechaNacimientoString);
            } catch (ParseException e) {
                // Manejar cualquier excepción de formato de fecha
                e.printStackTrace();
                // Puedes enviar un mensaje de error o redirigir a una página de error
                return;
            }
            
            // Convertir la fecha reformateada al formato deseado
            SimpleDateFormat dateFormatOutput = new SimpleDateFormat("dd/MM/yyyy");
            String fechaNacimientoFormatted = dateFormatOutput.format(fechaNacimiento);
            
            // Verificar si el paciente tiene tutor y ajustar los parámetros en consecuencia
            if (tieneTutor != null && tieneTutor.equals("on")) {
                // Si tiene tutor, asignar el valor del campo correspondiente
                tutorNombre = request.getParameter("tutorNombre");
            } else {
                // Si no tiene tutor, asignar un valor por defecto
                tutorNombre = "N/A";
            }
            
            String serverAddress = "localhost"; // Dirección IP del servidor
            int serverPort = 12345; // Puerto del servidor
            SocketCliente socketCliente = new SocketCliente(serverAddress, serverPort);

            // Ejemplo: Enviar mensaje al servidor
            String respuesta = socketCliente.enviarMensaje("insertarPaciente!" + nombre + "!" + curp + 
                    "!" + fechaNacimientoFormatted + "!" + tutorNombre + "!" + contrasena);
            System.out.println("Respuesta del servidor: " + respuesta);
            
            if (respuesta.equals("true")) {
                socketCliente = new SocketCliente(serverAddress, serverPort);
                respuesta = socketCliente.enviarMensaje("consultarPaciente!" + curp);
                String[] partes = respuesta.split("!");
                socketCliente = new SocketCliente(serverAddress, serverPort);
                respuesta = socketCliente.enviarMensaje("insertarExpediente!" + partes[0]);
                // Si la respuesta es true, redirigir a indexM.jsp
                response.sendRedirect("indexP.jsp");
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
        return "Servlet para registro de pacientes";
    }
}
