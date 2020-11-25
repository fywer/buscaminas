/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import modelo.Partida;

/**
 *
 * @author fywer
 */
@Entity
@Table(name = "entidad_partida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntidadPartida.findAll", query = "SELECT e FROM EntidadPartida e")
    , @NamedQuery(name = "EntidadPartida.findByGanadas", query = "SELECT e FROM EntidadPartida e WHERE e.ganadas = :ganadas")
    , @NamedQuery(name = "EntidadPartida.findByPerdidas", query = "SELECT e FROM EntidadPartida e WHERE e.perdidas = :perdidas")
    , @NamedQuery(name = "EntidadPartida.findByUltimaActualizacion", query = "SELECT e FROM EntidadPartida e WHERE e.ultimaActualizacion = :ultimaActualizacion")
    , @NamedQuery(name = "EntidadPartida.findByCuentaUsuario", query = "SELECT e FROM EntidadPartida e WHERE e.cuentaUsuario = :cuentaUsuario")})
public class EntidadPartida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ganadas")
    private int ganadas;
    @Basic(optional = false)
    @Column(name = "perdidas")
    private int perdidas;
    @Basic(optional = false)
    @Column(name = "ultimaActualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaActualizacion;
    @Id
    @Basic(optional = false)
    @Column(name = "cuenta_usuario")
    private String cuentaUsuario;
    @JoinColumn(name = "cuenta_usuario", referencedColumnName = "usuario", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private EntidadCuenta entidadCuenta;

    public EntidadPartida() {
    }

    public EntidadPartida(String cuentaUsuario) {
        this.cuentaUsuario = cuentaUsuario;
    }

    public EntidadPartida(String cuentaUsuario, int ganadas, int perdidas, Date ultimaActualizacion) {
        this.cuentaUsuario = cuentaUsuario;
        this.ganadas = ganadas;
        this.perdidas = perdidas;
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public int getGanadas() {
        return ganadas;
    }

    public void setGanadas(int ganadas) {
        this.ganadas = ganadas;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }

    public Date getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(Date ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public String getCuentaUsuario() {
        return cuentaUsuario;
    }

    public void setCuentaUsuario(String cuentaUsuario) {
        this.cuentaUsuario = cuentaUsuario;
    }

    public EntidadCuenta getEntidadCuenta() {
        return entidadCuenta;
    }

    public void setEntidadCuenta(EntidadCuenta entidadCuenta) {
        this.entidadCuenta = entidadCuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cuentaUsuario != null ? cuentaUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadPartida)) {
            return false;
        }
        EntidadPartida other = (EntidadPartida) object;
        if ((this.cuentaUsuario == null && other.cuentaUsuario != null) || (this.cuentaUsuario != null && !this.cuentaUsuario.equals(other.cuentaUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.EntidadPartida[ cuentaUsuario=" + cuentaUsuario + " ]";
    }
    
    public Partida obtenerPartida() {
        return new Partida(
                this.ganadas,
                this.perdidas,
                this.ultimaActualizacion,
                this.cuentaUsuario
            );
    }
    
    public EntidadPartida(Partida partida) {
        this.ganadas = partida.getGanadas();
        this.perdidas = partida.getPerdidas();
        this.ultimaActualizacion = partida.getUltimaActualizacion();
        this.cuentaUsuario = partida.getCuentaUsuario();
    }
}
