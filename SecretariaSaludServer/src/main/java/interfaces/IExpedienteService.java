/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import dominio.Expediente;
import java.util.List;

/**
 *
 * @author kingu
 */
public interface IExpedienteService {
    boolean agregarExpediente(Expediente expediente);
    boolean actualizarExpediente(Expediente expediente);
    boolean eliminarExpediente(int id);
    List<Expediente> obtenerExpedientes();
    Expediente consultarExpediente(int idPaciente);
    boolean cambiarAcceso(int id, boolean acceso);
    boolean modificarMedicos(int id, String medicos);
}
