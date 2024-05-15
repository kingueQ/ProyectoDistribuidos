package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import logica.Logica;

public class SocketServer {

    private Logica logica = new Logica();
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
                boolean result;
                switch (command) {
                    case "insertarExpediente":
                        // Llama al método para insertar expediente en ExpedienteDAO
                        result = logica.insertarExpediente(parts[1]);
                        // Enviar resultado al cliente
                        output.println(result ? "true" : "false");
                        break;
                    case "insertarPaciente":
                        // Llama al método para insertar medico en PacienteDAO
                        result = logica.insertarPaciente(parts[1], parts[2], parts[3], parts[4], parts[5]);
                        // Enviar resultado al cliente
                        output.println(result ? "true" : "false");
                        break;
                    case "insertarMedico":
                        // Llama al método para insertar medico en PacienteDAO
                        result = logica.insertarMedico(parts[1], parts[2], parts[3], parts[4]);
                        // Enviar resultado al cliente
                        output.println(result ? "true" : "false");
                        break;
                    case "eliminarExpediente":
                        // Llama al método para eliminar expediente en ExpedienteDAO
                        result = logica.eliminarExpediente(parts[1]);
                        // Enviar resultado al cliente
                        output.println(result ? "true" : "false");
                        break;
                    case "eliminarPaciente":
                        // Llama al método para eliminar paciente en PacienteDAO
                        result = logica.eliminarPaciente(parts[1]);
                        // Enviar resultado al cliente
                        output.println(result ? "true" : "false");
                        break;
                    case "eliminarMedico":
                        // Llama al método para eliminar medico en MedicoDAO
                        result = logica.eliminarMedico(parts[1]);
                        // Enviar resultado al cliente
                        output.println(result ? "true" : "false");
                        break;
                    case "actualizarExpediente":
                        // Llama al método para actualizar expediente en ExpedienteDAO
                        result = logica.actualizarExpediente(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                        // Enviar resultado al cliente
                        output.println(result ? "true" : "false");
                        break;
                    case "actualizarPaciente":
                        // Llama al método para actualizar paciente en PacienteDAO
                        result = logica.actualizarPaciente(parts[1], parts[2], parts[3], parts[4], parts[5]);
                        // Enviar resultado al cliente
                        output.println(result ? "true" : "false");
                        break;
                    case "actualizarMedico":
                        // Llama al método para actualizar medico en MedicoDAO
                        result = logica.actualizarMedico(parts[1], parts[2], parts[3], parts[4]);
                        // Enviar resultado al cliente
                        output.println(result ? "true" : "false");
                        break;
                    case "consultarPacientes":
                        // Llama al método para consultar pacientes en PacienteDAO
                        String pacientesResult = logica.consultarPacientes();
                        // Enviar resultado al cliente
                        output.println(pacientesResult);
                        break;
                    case "consultarExpedientes":
                        // Llama al método para consultar expedientes en ExpedienteDAO
                        String expedientesResult = logica.consultarExpedientes();
                        // Enviar resultado al cliente
                        output.println(expedientesResult);
                        break;
                    case "consultarMedicos":
                        // Llama al método para consultar medicos en MedicoDAO
                        String medicosResult = logica.consultarMedicos();
                        // Enviar resultado al cliente
                        output.println(medicosResult);
                        break;
                    case "autenticar":
                        // Llama al método para consultar medicos en MedicoDAO
                        result = logica.autenticar(parts[1], parts[2]);
                        // Enviar resultado al cliente
                        output.println(result ? "true" : "false");
                        break;
                    case "consultaMedico":
                        // Llama al método para consultar medicos en MedicoDAO
                        String medicoResult = logica.consultarMedico(parts[1]);
                        // Enviar resultado al cliente
                        output.println(medicoResult);
                        break;
                    case "consultaPaciente":
                        // Llama al método para consultar medicos en MedicoDAO
                        String pacienteResult = logica.consultarPaciente(parts[1]);
                        // Enviar resultado al cliente
                        output.println(pacienteResult);
                        break;
                    case "consultarExpediente":
                        // Llama al método para consultar medicos en MedicoDAO
                        String expedienteResult = logica.consultarExpediente(parts[1]);
                        // Enviar resultado al cliente
                        output.println(expedienteResult);
                        break;
                    case "enviar":
                        // Llama al método para consultar medicos en MedicoDAO
                        result = logica.enviar(parts[1], parts[2]);
                        // Enviar resultado al cliente
                        output.println(result ? "true" : "false");
                        break;
                    case "recibir":
                        // Llama al método para consultar medicos en MedicoDAO
                        String mensajes = logica.recibir(parts[1]);
                        // Enviar resultado al cliente
                        System.out.println("Respuesta: " + mensajes);
                        output.println(mensajes);
                        break;
                    case "cambiarAcceso":
                        // Llama al método para consultar medicos en MedicoDAO
                        result = logica.cambiarAcceso(parts[1]);
                        // Enviar resultado al cliente
                        System.out.println(result);
                        output.println(result ? "true" : "false");
                        break;
                    case "modificarMedicos":
                        // Llama al método para consultar medicos en MedicoDAO
                        result = logica.modificarMedicos(parts[1], parts[2]);
                        // Enviar resultado al cliente
                        System.out.println(result);
                        output.println(result ? "true" : "false");
                        break;
                    default:
                        output.println("Comando no reconocido: " + command);
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al manejar cliente: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 12345; // Puerto en el que escucha el servidor
        SocketServer socketServer = new SocketServer(port);
        socketServer.start();
    }
}
