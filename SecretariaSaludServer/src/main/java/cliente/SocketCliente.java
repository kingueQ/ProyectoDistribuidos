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
        this.serverAddress = "localhost";
        this.serverPort = 1234;
    }

    public String enviarMensaje(String mensaje) {
        String respuestaServidor = null;
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Enviar mensaje al servidor
            out.println(mensaje);

            // Leer respuesta del servidor
            respuestaServidor = in.readLine();

        } catch (IOException e) {
            System.err.println("Error al enviar/recibir mensaje: " + e.getMessage());
        }
        return respuestaServidor;
    }
}
