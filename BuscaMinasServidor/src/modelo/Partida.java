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
public class Partida implements Serializable {
    private int ganadas;
    private int perdidas;
    private Date ultimaActualizacion;
    private String enPartida;
    
    private String cuentaUsuario;
    private Cuenta cuenta;
    
    private boolean turno = false;
    
    public Partida() {
        this.ganadas = 0;
        this.perdidas = 0;
        this.enPartida = "desconectado";
    }
    
    public Partida(int ganadas, int perdidas, Date ultimaActualizacion, String cuentaUsuario) {
        this.ganadas = ganadas;
        this.perdidas = perdidas;
        this.ultimaActualizacion = ultimaActualizacion;
        this.cuentaUsuario = cuentaUsuario;
        this.enPartida = "En linea";
    }
    
    /**
     * @return the ganadas
     */
    public int getGanadas() {
        return ganadas;
    }

    /**
     * @param ganadas the ganadas to set
     */
    public void setGanadas(int ganadas) {
        this.ganadas = ganadas;
    }

    /**
     * @return the perdidas
     */
    public int getPerdidas() {
        return perdidas;
    }

    /**
     * @param perdidas the perdidas to set
     */
    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }

    /**
     * @return the ultimaActualizacion
     */
    public Date getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    /**
     * @param ultimaActualizacion the ultimaActualizacion to set
     */
    public void setUltimaActualizacion(Date ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
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
     * @return the enPartida
     */
    public String getEnPartida() {
        return enPartida;
    }

    /**
     * @param enPartida the enPartida to set
     */
    public void setEnPartida(String enPartida) {
        this.enPartida = enPartida;
    }

    /**
     * @return the turno
     */
    public boolean isTurno() {
        return turno;
    }

    /**
     * @param turno the turno to set
     */
    public void setTurno(boolean turno) {
        this.turno = turno;
    }


}
