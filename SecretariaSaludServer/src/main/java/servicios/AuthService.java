/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dominio.Medico;
import dominio.Paciente;
import interfaces.IAuthService;
import interfaces.IMedicoService;
import interfaces.IPacienteService;

/**
 *
 * @author kingu
 */
public class AuthService implements IAuthService{
    private final IPacienteService pacienteDAO;
    private final IMedicoService medicoDAO;

    public AuthService(IPacienteService pacienteDAO, IMedicoService medicoDAO) {
        this.pacienteDAO = pacienteDAO;
        this.medicoDAO = medicoDAO;
    }

    @Override
    public boolean autenticarPaciente(String curp, String pass) {
        Paciente paciente = pacienteDAO.consultarPaciente(curp);
        return paciente != null && paciente.getPass().equals(pass);
    }

    @Override
    public boolean autenticarMedico(String cedula, String pass) {
        Medico medico = medicoDAO.consultarMedico(cedula);
        return medico != null && medico.getPass().equals(pass);
    }
}
