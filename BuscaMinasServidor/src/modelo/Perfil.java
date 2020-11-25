/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author fywer
 */
public class Perfil implements Serializable {
    private String genero;
    private String correo;
    private Date fechaNacimiento;
    private String idioma;
    private byte[] foto;
    private Date fechaCreacion;
    private String dificultad;
    
    private String cuentaUsuario;
    private Cuenta cuenta;

    
     public Perfil(String genero, String correo, Date fechaNacimiento, String cuentaUsuario) {
        this.genero = genero;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.cuentaUsuario = cuentaUsuario;
    }
    
    public Perfil(String genero, String correo, Date fechaNacimiento, String dificultad, String idioma, byte[] foto, Date fechaCreacion, String cuentaUsuario) {
        this.genero = genero;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.dificultad = dificultad;
        this.idioma = idioma;
        this.foto = foto;
        this.fechaCreacion = fechaCreacion;
        this.cuentaUsuario = cuentaUsuario;
    }
    
    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the fechaNacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * @param idioma the idioma to set
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * @return the foto
     */
    public byte[] getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the cuentaUsuario
     */
    public String getCuentaUsuario() {
        return cuentaUsuario;
    }

    /**
     * @param cuentaUsuario the cuentaUsuario to set
     */
    public void setCuentaUsuario(String cuentaUsuario) {
        this.cuentaUsuario = cuentaUsuario;
    }

    /**
     * @return the cuenta
     */
    public Cuenta getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return the dificultad
     */
    public String getDificultad() {
        return dificultad;
    }

    /**
     * @param dificultad the dificultad to set
     */
    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
    
}