/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import cliente.SocketCliente;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/SubirImagenServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class SubirImagenServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cedula = request.getParameter("cedula");
        String curp = request.getParameter("curp");
// Obtiene la parte del archivo de la solicitud
        Part filePart = request.getPart("imagen");

        // Obtiene el nombre del archivo
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Guarda el archivo en la carpeta img
        String uploadDir = getServletContext().getRealPath("") + File.separator + ".." + File.separator + "web" + File.separator + "img";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs(); // Crea la carpeta si no existe
        }
        String filePath = uploadDir + File.separator + fileName;
        filePart.write(filePath);

        String serverAddress = "localhost"; // Dirección IP del servidor
        int serverPort = 12345; // Puerto del servidor
        SocketCliente cliente = new SocketCliente(serverAddress, serverPort);
        String respuesta = cliente.enviarMensaje("consultarExpediente!" + curp);
        String[] expediente = respuesta.split("!");

        String expedienteN = expediente[0] + "!" + expediente[0] + "!" + expediente[4] + "!"
                + expediente[1] + "-" + "img/" + fileName + "!" + expediente[2] + "!" + expediente[3];

        cliente = new SocketCliente(serverAddress, serverPort);
        respuesta = cliente.enviarMensaje("actualizarExpediente!" + expedienteN);
        // Envía una respuesta al cliente
        HttpSession objSesion = request.getSession(true);
        objSesion.setAttribute("cedula", cedula);
        response.sendRedirect("expedienteM.jsp?curp=" + curp);
    }
}
