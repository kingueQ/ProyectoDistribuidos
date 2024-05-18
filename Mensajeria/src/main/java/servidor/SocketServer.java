package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.Consumer;
import mensajes.Sender;

public class SocketServer {

    private ServerSocket serverSocket;
    private int port;

    public SocketServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor iniciado. Esperando conexiones en el puerto " + port + "...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while ((message = input.readLine()) != null) {
                System.out.println("Mensaje recibido: " + message);
                String[] parts = message.split("!");
                String command = parts[0];
                switch (command) {
                    case "enviar":
                        // Llama al método para insertar expediente en ExpedienteDAO
                        boolean result = enviar(parts[1], parts[2]);
                        // Enviar resultado al cliente
                        output.println(result ? "true" : "false");
                        break;
                    case "recibir":
                        if (parts[1] == null) {
                            output.println("");
                            break;
                        } else {
                            // Llama al método para insertar medico en PacienteDAO
                            String mensajes = recibir(parts[1]);
                            // Enviar resultado al cliente
                            output.println(mensajes);
                            break;
                        }
                    default:
                        output.println("Comando no reconocido: " + command);
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al manejar cliente: " + e.getMessage());
        }
    }

    public boolean enviar(String nombre, String mensaje) {
        try {
            Sender sender = new Sender();
            sender.enviar(nombre, mensaje);
            sender.cerrar();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String recibir(String nombre) {
        try {
            Consumer consumer = new Consumer();
            String mensajes = consumer.recibir(nombre, 3000);
            consumer.cerrar();
            return mensajes;
        } catch (IOException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static void main(String[] args) {
        int port = 1234; // Puerto en el que escucha el servidor
        SocketServer socketServer = new SocketServer(port);
        socketServer.start();
    }
}
