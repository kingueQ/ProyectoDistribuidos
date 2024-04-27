/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kingu
 */
public class Conexion {
    private String USERNAME="root";
    private String PASSWORD="Blaziquen_01";
    private String HOST="localhost";
    private String PORT="1234";
    private String DATEBASE="secretariadesalud";
    private String CLASSNAME="com.mysql.jdbc.Driver";
    private String URL="jdbc:mysql://"+HOST+":"+PORT+"/"+DATEBASE;
    private Connection con;
    
    public Conexion(){
        try{
            Class.forName(CLASSNAME);
            con=DriverManager.getConnection(URL,USERNAME,PASSWORD);            
        }catch(ClassNotFoundException e){
            System.err.println("Error en: "+e);
        }catch(SQLException e){
            System.err.println("Error en: "+e);
        }
    }
    
    public Connection getConexion(){
        return con;
    }
}
