/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author kingu
 */
public interface ILogica {
    public boolean insertarExpediente(String idPaciente);
    public boolean insertarPaciente(String nombre, String curp, String fechaNac, String tutor, String pass);
    public boolean insertarMedico(String cedula, String nombre, String pass, String especialidad);
    public boolean eliminarExpediente(String idExpediente);
    public boolean eliminarPaciente(String curp);
    public boolean eliminarMedico(String cedula);
    public boolean actualizarExpediente(String idExpediente, String idPaciente, String medicosAcceso, String imagenes, String documentos, String textos);
    public boolean actualizarPaciente(String nombre, String curp, String fechaNac, String tutor, String pass);
    public boolean actualizarMedico(String cedula, String nombre, String pass, String especialidad);
    public String consultarPacientes();
    public String consultarExpedientes();
    public String consultarMedicos();
    public String consultarPaciente(String curp);
    public String consultarMedico(String cedula);
    public String consultarExpediente(String curp);
    public boolean autenticarPaciente(String curp, String pass);
    public boolean autenticarMedico(String cedula, String pass);
    public boolean autenticar(String credencial, String pass);
    public boolean enviar(String nombre, String mensaje);
    public String recibir(String nombre);
    public boolean cambiarAcceso(String curp);
    public boolean modificarMedicos(String curp, String medicos);
}
