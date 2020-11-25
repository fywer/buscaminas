/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import modelo.Cuenta;

/**
 *
 * @author fywer
 */
@Entity
@Table(name = "entidad_cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntidadCuenta.findByUsuarioAndContrasena", query = "SELECT e FROM EntidadCuenta e WHERE e.usuario = :usuario AND e.contrasena = :contrasena")
    , @NamedQuery(name = "EntidadCuenta.findAll", query = "SELECT e FROM EntidadCuenta e")
    , @NamedQuery(name = "EntidadCuenta.findByUsuario", query = "SELECT e FROM EntidadCuenta e WHERE e.usuario = :usuario")
    , @NamedQuery(name = "EntidadCuenta.findByContrasena", query = "SELECT e FROM EntidadCuenta e WHERE e.contrasena = :contrasena")})
public class EntidadCuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "contrasena")
    private String contrasena;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "entidadCuenta")
    private EntidadPerfil entidadPerfil;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "entidadCuenta")
    private EntidadPartida entidadPartida;

    public EntidadCuenta() {
    }

    public EntidadCuenta(String usuario) {
        this.usuario = usuario;
    }

    public EntidadCuenta(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public EntidadPerfil getEntidadPerfil() {
        return entidadPerfil;
    }

    public void setEntidadPerfil(EntidadPerfil entidadPerfil) {
        this.entidadPerfil = entidadPerfil;
    }

    public EntidadPartida getEntidadPartida() {
        return entidadPartida;
    }

    public void setEntidadPartida(EntidadPartida entidadPartida) {
        this.entidadPartida = entidadPartida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadCuenta)) {
            return false;
        }
        EntidadCuenta other = (EntidadCuenta) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.EntidadCuenta[ usuario=" + usuario + " ]";
    }
    
    public Cuenta obtenerCuenta() {
        return new Cuenta(
                this.usuario,
                this.contrasena,
                this.entidadPartida.obtenerPartida(),
                this.entidadPerfil.obtenerPerfil());
    }
    
    public EntidadCuenta(Cuenta cuenta) {
        this.usuario = cuenta.getUsuario();
        this.contrasena = cuenta.getContrasena();
        this.entidadPartida = new EntidadPartida(cuenta.getPartida());
        this.entidadPerfil = new EntidadPerfil(cuenta.getPerfil());
    } 
}
