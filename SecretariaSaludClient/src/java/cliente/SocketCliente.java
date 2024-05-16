package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketCliente {
    private String serverAddress;
    private int serverPort;

    public SocketCliente(String serverAddress, int serverPort) {
        serverAddress = serverAddress;
        serverPort = serverPort;
    }

    public String enviarMensaje(String mensaje) {
        String respuestaServidor = null;
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            System.out.println("Establecio conexion");
            // Enviar mensaje al servidor
            out.println(mensaje);
            System.out.println("Envio mensaje");
            // Leer respuesta del servidor
            respuestaServidor = in.readLine();

        } catch (IOException e) {
            System.err.println("Error al enviar/recibir mensaje: " + e.getMessage());
            e.printStackTrace();
        }
        return respuestaServidor;
    }
}
