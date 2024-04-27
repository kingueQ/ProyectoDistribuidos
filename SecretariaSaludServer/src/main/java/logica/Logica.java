/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import dao.ExpedienteDAO;
import dao.MedicoDAO;
import dao.PacienteDAO;
import dominio.Expediente;
import dominio.Medico;
import dominio.Paciente;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author kingu
 */
public class Logica {

    private ExpedienteDAO expedienteDAO;
    private PacienteDAO pacienteDAO;
    private MedicoDAO medicoDAO;

    public Logica() {
        expedienteDAO = new ExpedienteDAO();
        pacienteDAO = new PacienteDAO();
        medicoDAO = new MedicoDAO();
    }

    public boolean insertarExpediente(String idPaciente, String medicosAcceso, String imagenes, String documentos, String textos) {

        Expediente expediente = new Expediente(imagenes, textos, documentos, Integer.parseInt(idPaciente));
        expediente.setMedicos(medicosAcceso);
        expediente.setAcceso(false);
        return expedienteDAO.agregarExpediente(expediente);
    }

    public boolean insertarPaciente(String nombre, String curp, String fechaNac, String tutor, String pass) {

        String[] datos = fechaNac.split("/");
        int dia = Integer.parseInt(datos[0]);
        int mes = Integer.parseInt(datos[1]);
        int year = Integer.parseInt(datos[2]);
        Paciente paciente = new Paciente(nombre, curp, new Date(dia, mes, year), tutor, pass);
        return pacienteDAO.agregarPaciente(paciente);
    }

    public boolean insertarMedico(String cedula, String nombre, String pass, String especialidad) {

        Medico medico = new Medico(cedula, nombre, pass, especialidad);
        return medicoDAO.agregarMedico(medico);
    }

    public boolean eliminarExpediente(String idExpediente) {

        return expedienteDAO.eliminarExpediente(Integer.parseInt(idExpediente));
    }

    public boolean eliminarPaciente(String curp) {

        return pacienteDAO.eliminarPaciente(curp);
    }

    public boolean eliminarMedico(String cedula) {

        return medicoDAO.eliminarMedico(cedula);
    }

    public boolean actualizarExpediente(String idExpediente, String idPaciente, String medicosAcceso, String imagenes, String documentos, String textos) {

        Expediente expediente = new Expediente(imagenes, textos, documentos, Integer.parseInt(idPaciente));
        expediente.setId(Integer.parseInt(idExpediente));
        expediente.setMedicos(medicosAcceso);
        expediente.setAcceso(false);
        return expedienteDAO.actualizarExpediente(expediente);
    }

    public boolean actualizarPaciente(String nombre, String curp, String fechaNac, String tutor, String pass) {

        String[] datos = fechaNac.split("/");
        int dia = Integer.parseInt(datos[0]);
        int mes = Integer.parseInt(datos[1]);
        int year = Integer.parseInt(datos[2]);
        Paciente paciente = new Paciente(nombre, curp, new Date(year, mes, dia), tutor, pass);
        return pacienteDAO.actualizarPaciente(paciente);
    }

    public boolean actualizarMedico(String cedula, String nombre, String pass, String especialidad) {

        Medico medico = new Medico(cedula, nombre, pass, especialidad);
        return medicoDAO.actualizarMedico(medico);
    }

    public String consultarPacientes() {

        List<Paciente> pacientes = pacienteDAO.obtenerPacientes();
        String result = "";
        for (Paciente paciente : pacientes) {
            result = result + paciente.getId() + "!" + paciente.getCurp() + "!" + paciente.getNombre() + "!" + paciente.getFechaNacimiento() + "!" + paciente.getTutor() + "!?";
        }
        return result;
    }

    public String consultarExpedientes() {

        List<Expediente> expedientes = expedienteDAO.obtenerExpedientes();
        StringBuilder result = new StringBuilder();
        for (Expediente expediente : expedientes) {
            result.append(expediente.getId()).append("!").append(expediente.getImagenes()).append("!").append(expediente.getDocumentos()).append("!").append(expediente.getTextos()).append("!").append(expediente.getMedicos()).append("!").append(expediente.getAcceso()).append("!?"); // Usamos "!?" como delimitador
        }
        return result.toString();
    }

    public String consultarMedicos() {

        List<Medico> medicos = medicoDAO.obtenerMedicos();
        StringBuilder result = new StringBuilder();
        for (Medico medico : medicos) {
            result.append(medico.getId()).append("!").append(medico.getNombre()).append("!").append(medico.getCedula()).append("!").append(medico.getEspecialidad()).append("!").append(medico.getPass()).append("!?"); // Usamos "!?" como delimitador
        }
        return result.toString();
    }

    public String consultarPaciente(String curp) {
        Paciente paciente = pacienteDAO.consultarPaciente(curp);
        if (paciente != null) {
            return paciente.getId() + "!" + paciente.getCurp() + "!" + paciente.getNombre() + "!" + paciente.getFechaNacimiento() + "!" + paciente.getTutor() + "!" + paciente.getPass();
        } else {
            return "Paciente no encontrado";
        }
    }

    public String consultarMedico(String cedula) {
        Medico medico = medicoDAO.consultarMedico(cedula);
        if (medico != null) {
            return medico.getId() + "!" + medico.getNombre() + "!" + medico.getCedula() + "!" + medico.getEspecialidad() + "!" + medico.getPass();
        } else {
            return "Médico no encontrado";
        }
    }

    public String consultarExpediente(String curp) {
        Paciente paciente = pacienteDAO.consultarPaciente(curp);
        Expediente expediente = expedienteDAO.consultarExpediente(paciente.getId());
        if (expediente != null) {
            return expediente.getId() + "!" + expediente.getImagenes() + "!" + expediente.getDocumentos() + "!" + expediente.getTextos() + "!" + expediente.getMedicos() + "!" + expediente.getAcceso();
        } else {
            return "Expediente no encontrado";
        }
    }

    public boolean autenticarPaciente(String curp, String pass) {
        return pacienteDAO.autenticar(curp, pass);
    }

    public boolean autenticarMedico(String cedula, String pass) {
        return medicoDAO.autenticar(cedula, pass);
    }
    
    public boolean autenticar(String credencial, String pass){
        if(credencial.length()==18){
            return this.autenticarPaciente(credencial, pass);
        }else{
            return this.autenticarMedico(credencial, pass);
        }
    }
}
