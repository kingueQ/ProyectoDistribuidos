package servlet;

import cliente.SocketCliente;
import encriptacion.KeyManager;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

@WebServlet(name = "AutenticacionServlet", urlPatterns = {"/AutenticacionServlet"})
public class AutenticacionServlet extends HttpServlet {

    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String AES_KEY_ALGORITHM = "AES";
    private static final int AES_KEY_SIZE = 256;

    private SecretKey secretKey;
    private IvParameterSpec iv;

    @Override
    public void init() throws ServletException {
        secretKey = KeyManager.getSecretKey(); // Obtener la misma clave secreta que en RegistroPacientesServlet
        iv = KeyManager.getIv(); // Obtener el mismo vector de inicialización que en RegistroPacientesServlet
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Obtener los parámetros del formulario
            String cedulaCurp = request.getParameter("credencial");
            String contrasena = request.getParameter("contrasena");

            // Encriptar la contraseña
            String contrasenaEncriptada = encriptarContrasena(contrasena);

            String serverAddress = "localhost"; // Dirección IP del servidor
            int serverPort = 12345; // Puerto del servidor
            SocketCliente socketCliente = new SocketCliente(serverAddress, serverPort);

            // Enviar mensaje al servidor
            String respuesta = socketCliente.enviarMensaje("autenticar!" + cedulaCurp + "!" + contrasenaEncriptada);
            System.out.println("Respuesta del servidor: " + respuesta);

            if (respuesta.equalsIgnoreCase("true")) {
                // Si la respuesta es true, redirigir a indexM.jsp
                if (cedulaCurp.length() == 18) {
                    HttpSession objSesion = request.getSession(true);
                    objSesion.setAttribute("curp", cedulaCurp);
                    response.sendRedirect("indexP.jsp");
                } else {
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

    private String encriptarContrasena(String contrasena) throws ServletException {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
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
        return "Servlet para autenticación de usuarios";
    }
}
