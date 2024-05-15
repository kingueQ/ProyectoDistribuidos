package servicios;

import bd.Conexion;
import dominio.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import interfaces.IPacienteService;

public class PacienteService implements IPacienteService {

    private final Connection conexion;

    public PacienteService(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public boolean agregarPaciente(Paciente paciente) {
        String query = "INSERT INTO pacientes (nombre, curp, fechaNacimiento, tutor, pass) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, paciente.getNombre());
            statement.setString(2, paciente.getCurp());
            statement.setDate(3, paciente.getFechaNacimiento());
            statement.setString(4, paciente.getTutor());
            statement.setString(5, paciente.getPass());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean actualizarPaciente(Paciente paciente) {
        String query = "UPDATE pacientes SET nombre=?, fechaNacimiento=?, tutor=?, pass=? WHERE curp=?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, paciente.getNombre());
            statement.setDate(2, paciente.getFechaNacimiento());
            statement.setString(3, paciente.getTutor());
            statement.setString(4, paciente.getPass());
            statement.setString(5, paciente.getCurp());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminarPaciente(String curp) {
        String query = "DELETE FROM pacientes WHERE curp=?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, curp);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Paciente> obtenerPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        String query = "SELECT * FROM pacientes";
        try (PreparedStatement statement = conexion.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(result.getInt("id"));
                paciente.setNombre(result.getString("nombre"));
                paciente.setCurp(result.getString("curp"));
                paciente.setFechaNacimiento(result.getDate("fechaNacimiento"));
                paciente.setTutor(result.getString("tutor"));
                paciente.setPass(result.getString("pass"));
                pacientes.add(paciente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pacientes;
    }

    @Override
    public boolean autenticar(String curp, String pass) {
        String query = "SELECT * FROM pacientes WHERE curp=? AND pass=?";
        try (PreparedStatement statement = conexion.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            statement.setString(1, curp);
            statement.setString(2, pass);
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar paciente: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Paciente consultarPaciente(String curp) {
        Paciente paciente = null;
        String query = "SELECT * FROM pacientes WHERE curp=?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, curp);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    paciente = new Paciente();
                    paciente.setId(result.getInt("id"));
                    paciente.setNombre(result.getString("nombre"));
                    paciente.setCurp(result.getString("curp"));
                    paciente.setFechaNacimiento(result.getDate("fechaNacimiento"));
                    paciente.setTutor(result.getString("tutor"));
                    paciente.setPass(result.getString("pass"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar paciente: " + e.getMessage());
        }
        return paciente;
    }
}
