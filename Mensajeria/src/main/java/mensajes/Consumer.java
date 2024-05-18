package mensajes;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    private Connection connection;
    private Channel channel;

    public Consumer() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public String recibir(String nombre, long timeoutMillis) throws IOException {
        if(nombre==null||nombre.equals("null")||nombre.equals("")){
            return "";
        }
        String QUEUE_NAME = nombre;
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // Utilizamos un StringBuilder para almacenar los mensajes
        StringBuilder mensajes = new StringBuilder();

        // Creamos un objeto Object para sincronizar el bucle while
        Object lock = new Object();

        // Definimos el DeliverCallback fuera del bucle
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), "UTF-8");
            System.out.println("Recibido: " + msg);
            mensajes.append(msg).append("-");
            // Sincronizamos y notificamos al objeto lock
            synchronized (lock) {
                lock.notify();
            }
        };

        try {
            // Iniciamos el consumo de mensajes
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });

            // Esperamos hasta que se reciba un mensaje o se alcance el tiempo de espera
            synchronized (lock) {
                long startTime = System.currentTimeMillis();
                long elapsedTime = 0;
                while (elapsedTime < timeoutMillis) {
                    lock.wait(timeoutMillis - elapsedTime);
                    elapsedTime = System.currentTimeMillis() - startTime;
                }
            }
        } catch (InterruptedException e) {
            // Manejamos la excepción de interrupción
            Thread.currentThread().interrupt();
        }
        System.out.println("Mensaje recibido: " + mensajes.toString());
        // Devolvemos los mensajes recibidos como una cadena
        return mensajes.toString();
    }

    public void cerrar() throws IOException, TimeoutException {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
        if (connection != null && connection.isOpen()) {
            connection.close();
        }
    }
}
