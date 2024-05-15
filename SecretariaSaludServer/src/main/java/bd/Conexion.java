package bd;

import interfaces.IConexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion implements IConexion {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Blaziquen_01";
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "secretariadesalud";
    private static final String CLASSNAME = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
    private Connection con;

    public Conexion() {
        try {
            Class.forName(CLASSNAME);
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Error en: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error en: " + e.getMessage());
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
