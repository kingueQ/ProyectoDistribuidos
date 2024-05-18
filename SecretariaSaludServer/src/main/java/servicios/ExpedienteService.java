package servicios;

import bd.Conexion;
import dominio.Expediente;
import interfaces.IConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.IExpedienteService;

public class ExpedienteService implements IExpedienteService {

    private final Connection conexion;

    public ExpedienteService(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public boolean agregarExpediente(Expediente expediente) {
        String query = "INSERT INTO expedientes (idPaciente, imagenes, textos, documentos, medicosAcceso, acceso) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, expediente.getIdPaciente());
            statement.setString(2, expediente.getImagenes());
            statement.setString(3, expediente.getTextos());
            statement.setString(4, expediente.getDocumentos());
            statement.setString(5, expediente.getMedicos());
            statement.setBoolean(6, expediente.getAcceso());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar expediente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizarExpediente(Expediente expediente) {
        String query = "UPDATE expedientes SET imagenes=?, textos=?, documentos=?, medicosAcceso=?, acceso=? WHERE idPaciente=?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, expediente.getImagenes());
            statement.setString(2, expediente.getTextos());
            statement.setString(3, expediente.getDocumentos());
            statement.setString(4, expediente.getMedicos());
            statement.setBoolean(5, expediente.getAcceso());
            statement.setInt(6, expediente.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar expediente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminarExpediente(int id) {
        String query = "DELETE FROM expedientes WHERE idPaciente=?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar expediente: " + e.getMessage());
            return false;
        }
    }

    @Override
public List<Expediente> obtenerExpedientes() {
    List<Expediente> expedientes = new ArrayList<>();
    String query = "SELECT * FROM expedientes WHERE acceso=?";
    try (PreparedStatement statement = conexion.prepareStatement(query)) {
        statement.setBoolean(1, true);
        try (ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Expediente expediente = new Expediente();
                expediente.setId(result.getInt("id"));
                expediente.setIdPaciente(result.getInt("idPaciente"));
                expediente.setImagenes(result.getString("imagenes"));
                expediente.setTextos(result.getString("textos"));
                expediente.setDocumentos(result.getString("documentos"));
                expediente.setMedicos(result.getString("medicosAcceso"));
                expediente.setAcceso(result.getBoolean("acceso"));
                expedientes.add(expediente);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener expedientes: " + e.getMessage());
    }
    return expedientes;
}


    @Override
    public Expediente consultarExpediente(int idPaciente) {
        String query = "SELECT * FROM expedientes WHERE idPaciente=?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, idPaciente);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    Expediente expediente = new Expediente();
                    expediente.setId(result.getInt("id"));
                    expediente.setIdPaciente(result.getInt("idPaciente"));
                    expediente.setImagenes(result.getString("imagenes"));
                    expediente.setTextos(result.getString("textos"));
                    expediente.setDocumentos(result.getString("documentos"));
                    expediente.setMedicos(result.getString("medicosAcceso"));
                    expediente.setAcceso(result.getBoolean("acceso"));
                    return expediente;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar expediente: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean cambiarAcceso(int id, boolean acceso) {
        String query = "UPDATE expedientes SET acceso=? WHERE idPaciente=?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setBoolean(1, acceso);
            statement.setInt(2, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar expediente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificarMedicos(int id, String medicos) {
        String query = "UPDATE expedientes SET medicosAcceso=? WHERE idPaciente=?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, medicos);
            statement.setInt(2, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar expediente: " + e.getMessage());
            return false;
        }
    }
}
