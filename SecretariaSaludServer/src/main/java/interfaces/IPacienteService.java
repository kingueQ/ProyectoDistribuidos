/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import dominio.Paciente;
import java.util.List;

/**
 *
 * @author kingu
 */
public interface IPacienteService {
    boolean agregarPaciente(Paciente paciente);
    boolean actualizarPaciente(Paciente paciente);
    boolean eliminarPaciente(String curp);
    List<Paciente> obtenerPacientes();
    boolean autenticar(String curp, String pass);
    Paciente consultarPaciente(String curp);
}
