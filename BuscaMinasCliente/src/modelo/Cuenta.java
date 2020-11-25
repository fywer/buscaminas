/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author fywer
 */
public class Cuenta implements Serializable{
    private String usuario;
    private String contrasena;
    private Partida partida;
    private Perfil perfil;
    private boolean iniciado;

    public Cuenta(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }
    
    public Cuenta(String usuario, String contrasena, Partida partida, Perfil perfil) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.partida = partida;
        this.perfil = perfil;
    }
    
    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the partida
     */
    public Partida getPartida() {
        return partida;
    }

    /**
     * @param partida the partida to set
     */
    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    /**
     * @return the perfil
     */
    public Perfil getPerfil() {
        return perfil;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    /**
     * @return the iniciado
     */
    public boolean isIniciado() {
        return iniciado;
    }

    /**
     * @param iniciado the iniciado to set
     */
    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
    }
    
    
    
}
