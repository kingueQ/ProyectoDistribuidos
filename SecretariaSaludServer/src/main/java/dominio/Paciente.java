/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.sql.Date;

/**
 *
 * @author kingu
 */
public class Paciente {
    private int id;
    private String nombre;
    private String curp;
    private Date fechaNacimiento;
    private String tutor;
    private String pass;

    public Paciente() {
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Paciente(String nombre, String curp, Date fechaNacimiento, String tutor, String pass) {
        this.nombre = nombre;
        this.curp = curp;
        this.fechaNacimiento = fechaNacimiento;
        this.tutor = tutor;
        this.pass = pass;
    }

    public Paciente(String nombre, String curp, Date fechaNacimiento, String tutor) {
        this.nombre = nombre;
        this.curp = curp;
        this.fechaNacimiento = fechaNacimiento;
        this.tutor = tutor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "Paciente{" + "id=" + id + ", nombre=" + nombre + ", curp=" + curp + ", fechaNacimiento=" + fechaNacimiento + ", tutor=" + tutor + '}';
    }
    
    
}
