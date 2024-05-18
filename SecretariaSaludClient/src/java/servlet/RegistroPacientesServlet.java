package servlet;

import cliente.SocketCliente;
import encriptacion.KeyManager;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Base64;

@WebServlet(name = "RegistroPacientesServlet", urlPatterns = {"/RegistroPacientesServlet"})
public class RegistroPacientesServlet extends HttpServlet {

    private SecretKey secretKey;
    private IvParameterSpec iv;

    @Override
    public void init() throws ServletException {
        secretKey = KeyManager.getSecretKey();
        iv = KeyManager.getIv();
    }

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
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de fecha incorrecto.");
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

            // Encriptar la contraseña
            String contrasenaEncriptada = encriptarContrasena(contrasena);

            String serverAddress = "localhost"; // Dirección IP del servidor
            int serverPort = 12345; // Puerto del servidor
            SocketCliente socketCliente = new SocketCliente(serverAddress, serverPort);

            // Ejemplo: Enviar mensaje al servidor
            String respuesta = socketCliente.enviarMensaje("insertarPaciente!" + nombre + "!" + curp + 
                    "!" + fechaNacimientoFormatted + "!" + tutorNombre + "!" + contrasenaEncriptada);
            System.out.println("Respuesta del servidor: " + respuesta);

            if (respuesta.equals("true")) {
                socketCliente = new SocketCliente(serverAddress, serverPort);
                respuesta = socketCliente.enviarMensaje("consultaPaciente!" + curp);
                String[] partes = respuesta.split("!");
                socketCliente = new SocketCliente(serverAddress, serverPort);
                respuesta = socketCliente.enviarMensaje("insertarExpediente!" + partes[0]);
                // Si la respuesta es true, redirigir a indexP.jsp
                response.sendRedirect("indexP.jsp");
            } else {
                // Si la respuesta es false, redirigir a index.jsp
                response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            throw new ServletException("Error procesando la solicitud", e);
        }
    }

    private String encriptarContrasena(String contrasena) throws ServletException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encryptedBytes = cipher.doFinal(contrasena.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new ServletException("Error encriptando la contraseña", e);
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
