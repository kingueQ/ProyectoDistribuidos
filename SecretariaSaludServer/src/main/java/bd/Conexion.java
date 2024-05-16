package bd;

import interfaces.IConexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion implements IConexion {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "secretariadesalud";
    private static final String CLASSNAME = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
//    private static final String CLASSNAME = "com.mysql.cj.jdbc.Driver";
//    private static final String URL = "jdbc:mysql://proyectodistribuidos-mysql-1:3306/" + DATABASE + "?useSSL=false&allowPublicKeyRetrieval=true";
    private Connection con;

    public Conexion() {
        try {
            Class.forName(CLASSNAME);
            boolean connected = false;
            int attempts = 0;
            while (!connected && attempts < 5) {
                try {
                    con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    connected = true;
                } catch (SQLException e) {
                    System.err.println("Intento de conexión fallido, reintentando en 5 segundos...");
                    e.printStackTrace();
                    Thread.sleep(5000);  // Espera 5 segundos antes de reintentar
                    attempts++;
                }
            }
            if (!connected) {
                throw new SQLException("No se pudo establecer la conexión después de varios intentos.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver de MySQL: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException | InterruptedException e) {
            System.err.println("Error al establecer la conexión con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConexion() {
        return con;
    }

    @Override
    public void cerrarConexion() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
