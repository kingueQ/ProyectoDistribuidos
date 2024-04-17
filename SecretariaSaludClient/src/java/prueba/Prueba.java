/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import cliente.SocketCliente;

/**
 *
 * @author kingu
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Dirección IP del servidor
        int serverPort = 12345; // Puerto del servidor
        SocketCliente socketCliente = new SocketCliente(serverAddress, serverPort);

        // Ejemplo: Enviar mensaje al servidor
        String respuesta = socketCliente.enviarMensaje("insertarPaciente!Abraham Quintana!QUGA031128HSRNRBA0!28/11/2003!NA!Meganium_01");
        System.out.println("Respuesta del servidor: " + respuesta);
        
        socketCliente = new SocketCliente(serverAddress, serverPort);

        // Ejemplo: Enviar mensaje al servidor
        respuesta = socketCliente.enviarMensaje("insertarExpediente!1!true!resources/images/imagen1.jpg-resources/images/imagen2.jpg!resources/docs/doc1.pdf!texto1");
        System.out.println("Respuesta del servidor: " + respuesta);
        
        socketCliente = new SocketCliente(serverAddress, serverPort);

        // Ejemplo: Enviar mensaje al servidor
        respuesta = socketCliente.enviarMensaje("insertarMedico!GKWK533GD!Eduardo García!Charizard_01!Neurocirujia");
        System.out.println("Respuesta del servidor: " + respuesta);
    }
    
}
