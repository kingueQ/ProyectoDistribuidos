package dao;

import bd.Conexion;
import dominio.Medico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {
    private final Connection conexion;

    public MedicoDAO() {
        this.conexion = new Conexion().getConexion();
    }

    // Método para agregar un nuevo médico
    public boolean agregarMedico(Medico medico) {
        try {
            String query = "INSERT INTO medicos (cedula, nombre, pass, especialidad) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, medico.getCedula());
            statement.setString(2, medico.getNombre());
            statement.setString(3, medico.getPass());
            statement.setString(4, medico.getEspecialidad());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar médico: " + e.getMessage());
        }
        return false;
    }

    // Método para actualizar un médico existente
    public boolean actualizarMedico(Medico medico) {
        try {
            String query = "UPDATE medicos SET nombre=?, pass=?, especialidad=? WHERE cedula=?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, medico.getNombre());
            statement.setString(2, medico.getPass());
            statement.setString(3, medico.getEspecialidad());
            statement.setString(4, medico.getCedula());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar médico: " + e.getMessage());
        }
        return false;
    }

    // Método para eliminar un médico existente
    public boolean eliminarMedico(String cedula) {
        try {
            String query = "DELETE FROM medicos WHERE cedula=?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, cedula);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar médico: " + e.getMessage());
        }
        return false;
    }

    // Método para obtener todos los médicos
    public List<Medico> obtenerMedicos() {
        List<Medico> medicos = new ArrayList<>();
        try {
            String query = "SELECT * FROM medicos";
            PreparedStatement statement = conexion.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Medico medico = new Medico();
                medico.setId(result.getInt("id"));
                medico.setCedula(result.getString("cedula"));
                medico.setNombre(result.getString("nombre"));
                medico.setPass(result.getString("pass"));
                medico.setEspecialidad(result.getString("especialidad"));
                medicos.add(medico);
            }
            statement.close();
            result.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener médicos: " + e.getMessage());
        }
        return medicos;
    }
}
