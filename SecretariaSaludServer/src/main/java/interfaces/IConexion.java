/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.sql.Connection;

/**
 *
 * @author kingu
 */
public interface IConexion {
    Connection getConexion();
    void cerrarConexion();
}
