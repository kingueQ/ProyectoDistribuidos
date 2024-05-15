package servicios;

import bd.Conexion;
import dominio.Medico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.IMedicoService;

public class MedicoService implements IMedicoService {

    private final Connection conexion;

    public MedicoService(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public boolean agregarMedico(Medico medico) {
        String query = "INSERT INTO medicos (cedula, nombre, pass, especialidad) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, medico.getCedula());
            statement.setString(2, medico.getNombre());
            statement.setString(3, medico.getPass());
            statement.setString(4, medico.getEspecialidad());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar médico: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizarMedico(Medico medico) {
        String query = "UPDATE medicos SET nombre=?, pass=?, especialidad=? WHERE cedula=?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, medico.getNombre());
            statement.setString(2, medico.getPass());
            statement.setString(3, medico.getEspecialidad());
            statement.setString(4, medico.getCedula());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar médico: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminarMedico(String cedula) {
        String query = "DELETE FROM medicos WHERE cedula=?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, cedula);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar médico: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Medico> obtenerMedicos() {
        List<Medico> medicos = new ArrayList<>();
        String query = "SELECT * FROM medicos";
        try (PreparedStatement statement = conexion.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Medico medico = new Medico();
                medico.setId(result.getInt("id"));
                medico.setCedula(result.getString("cedula"));
                medico.setNombre(result.getString("nombre"));
                medico.setPass(result.getString("pass"));
                medico.setEspecialidad(result.getString("especialidad"));
                medicos.add(medico);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener médicos: " + e.getMessage());
        }
        return medicos;
    }

    @Override
    public boolean autenticar(String cedula, String pass) {
        String query = "SELECT * FROM medicos WHERE cedula=? AND pass=?";
        try (PreparedStatement statement = conexion.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            statement.setString(1, cedula);
            statement.setString(2, pass);
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Medico consultarMedico(String cedula) {
        Medico medico = null;
        String query = "SELECT * FROM medicos WHERE cedula=?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, cedula);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    medico = new Medico();
                    medico.setId(result.getInt("id"));
                    medico.setCedula(result.getString("cedula"));
                    medico.setNombre(result.getString("nombre"));
                    medico.setPass(result.getString("pass"));
                    medico.setEspecialidad(result.getString("especialidad"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar médico: " + e.getMessage());
        }
        return medico;
    }
}
