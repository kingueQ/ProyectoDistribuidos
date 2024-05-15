/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import dominio.Medico;
import java.util.List;

/**
 *
 * @author kingu
 */
public interface IMedicoService {
    boolean agregarMedico(Medico medico);
    boolean actualizarMedico(Medico medico);
    boolean eliminarMedico(String cedula);
    List<Medico> obtenerMedicos();
    boolean autenticar(String cedula, String pass);
    Medico consultarMedico(String cedula);
}
