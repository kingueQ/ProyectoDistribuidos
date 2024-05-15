/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

/**
 *
 * @author kingu
 */
public interface IAuthService {
    boolean autenticarPaciente(String curp, String pass);
    boolean autenticarMedico(String cedula, String pass);
}
