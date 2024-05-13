/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import cliente.SocketCliente;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kingu
 */
@WebServlet(name = "SubirTextoServlet", urlPatterns = {"/SubirTextoServlet"})
public class SubirTextoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String cedula = request.getParameter("cedula");
        String curp = request.getParameter("curp");
        String texto = request.getParameter("texto");
        
        String serverAddress = "localhost"; // Dirección IP del servidor
        int serverPort = 12345; // Puerto del servidor
        SocketCliente cliente = new SocketCliente(serverAddress, serverPort);
        String respuesta = cliente.enviarMensaje("consultarExpediente!" + curp);
        String[] expediente = respuesta.split("!");

        String expedienteN = expediente[0] + "!" + expediente[0] + "!" + expediente[4] + "!"
                + expediente[1] + "!" + expediente[2] + "!" + expediente[3] + "-" + texto;

        cliente = new SocketCliente(serverAddress, serverPort);
        respuesta = cliente.enviarMensaje("actualizarExpediente!" + expedienteN);
        // Envía una respuesta al cliente
        HttpSession objSesion = request.getSession(true);
        objSesion.setAttribute("cedula", cedula);
        response.sendRedirect("expedienteM.jsp?curp=" + curp);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
