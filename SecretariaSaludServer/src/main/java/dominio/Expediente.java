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
public class Expediente {
    private int id;
    private String imagenes;
    private String textos;
    private String documentos;
    private int idPaciente;
    private String medicos;
    private boolean acceso;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public Expediente() {
    }

    public Expediente(String imagenes, String textos, String documentos, int idPaciente) {
        this.imagenes = imagenes;
        this.textos = textos;
        this.documentos = documentos;
        this.idPaciente = idPaciente;
    }

    public String getMedicos() {
        return medicos;
    }

    public void setMedicos(String medicos) {
        this.medicos = medicos;
    }

    public boolean getAcceso() {
        return acceso;
    }

    public void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }

    public String getImagenes() {
        return imagenes;
    }

    public void setImagenes(String imagenes) {
        this.imagenes = imagenes;
    }

    public String getTextos() {
        return textos;
    }

    public void setTextos(String textos) {
        this.textos = textos;
    }

    public String getDocumentos() {
        return documentos;
    }

    public void setDocumentos(String documentos) {
        this.documentos = documentos;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    public void setNewImagen(String imagen){
        this.imagenes=imagenes + "!" + imagen;
    }
    
    public void setNewTexto(String texto){
        this.textos=textos + "!" + texto;
    }
    
    public void setNewDocumento(String documento){
        this.documentos=documentos + "!" + documento;
    }
    
    public void setNewMedico(String medico){
        this.medicos=medicos + "!" + medico;
    }

    @Override
    public String toString() {
        return "Expediente{" + "imagenes=" + imagenes + ", textos=" + textos + ", documentos=" + documentos + ", idPaciente=" + idPaciente + '}';
    }
    
    
}
