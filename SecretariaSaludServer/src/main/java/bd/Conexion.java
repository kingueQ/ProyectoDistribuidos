package bd;

import interfaces.IConexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion implements IConexion {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Blaziquen_01";
    private static final String HOST = "localhost";  // Nombre del servicio MySQL en Docker
    private static final String PORT = "3306";   // Puerto interno del contenedor MySQL
    private static final String DATABASE = "secretariadesalud";
    private static final String CLASSNAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
    private Connection con;

    public Conexion() {
        try {
            Class.forName(CLASSNAME);
            boolean connected = false;
            int attempts = 0;
            while (!connected && attempts < 10) {
                try {
                    con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    connected = true;
                    System.out.println("Conexion exitosa");
                } catch (SQLException e) {
                    System.err.println("Intento de conexión fallido, reintentando en 5 segundos...");
                    e.printStackTrace();
                    Thread.sleep(10000);  // Espera 5 segundos antes de reintentar
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
                e.printStackTrace();
            }
        }
    }
}