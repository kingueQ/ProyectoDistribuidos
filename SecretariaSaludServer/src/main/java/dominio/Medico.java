/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

/**
 *
 * @author kingu
 */
public class Medico {
    private int id;
    private String cedula;
    private String nombre;
    private String pass;
    private String especialidad;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public Medico() {
    }

    public Medico(String cedula, String nombre, String pass, String especialidad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.pass = pass;
        this.especialidad = especialidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return "Medico{" + "cedula=" + cedula + ", nombre=" + nombre + ", pass=" + pass + ", especialidad=" + especialidad + '}';
    }
    
    
    
}
