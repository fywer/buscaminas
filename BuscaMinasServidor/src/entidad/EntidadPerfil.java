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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import modelo.Perfil;

/**
 *
 * @author fywer
 */
@Entity
@Table(name = "entidad_perfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntidadPerfil.findAll", query = "SELECT e FROM EntidadPerfil e")
    , @NamedQuery(name = "EntidadPerfil.findByGenero", query = "SELECT e FROM EntidadPerfil e WHERE e.genero = :genero")
    , @NamedQuery(name = "EntidadPerfil.findByCorreo", query = "SELECT e FROM EntidadPerfil e WHERE e.correo = :correo")
    , @NamedQuery(name = "EntidadPerfil.findByFechaNacimiento", query = "SELECT e FROM EntidadPerfil e WHERE e.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "EntidadPerfil.findByIdioma", query = "SELECT e FROM EntidadPerfil e WHERE e.idioma = :idioma")
    , @NamedQuery(name = "EntidadPerfil.findByDificultad", query = "SELECT e FROM EntidadPerfil e WHERE e.dificultad = :dificultad")
    , @NamedQuery(name = "EntidadPerfil.findByFechaCreacion", query = "SELECT e FROM EntidadPerfil e WHERE e.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "EntidadPerfil.findByCuentaUsuario", query = "SELECT e FROM EntidadPerfil e WHERE e.cuentaUsuario = :cuentaUsuario")})
public class EntidadPerfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "genero")
    private String genero;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "fechaNacimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column(name = "idioma")
    private String idioma;
    @Column(name = "dificultad")
    private String dificultad;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @Basic(optional = false)
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Id
    @Basic(optional = false)
    @Column(name = "cuenta_usuario")
    private String cuentaUsuario;
    @JoinColumn(name = "cuenta_usuario", referencedColumnName = "usuario", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private EntidadCuenta entidadCuenta;

    public EntidadPerfil() {
    }

    public EntidadPerfil(String cuentaUsuario) {
        this.cuentaUsuario = cuentaUsuario;
    }

    public EntidadPerfil(String cuentaUsuario, String genero, String correo, Date fechaNacimiento, Date fechaCreacion) {
        this.cuentaUsuario = cuentaUsuario;
        this.genero = genero;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaCreacion = fechaCreacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
        if (!(object instanceof EntidadPerfil)) {
            return false;
        }
        EntidadPerfil other = (EntidadPerfil) object;
        if ((this.cuentaUsuario == null && other.cuentaUsuario != null) || (this.cuentaUsuario != null && !this.cuentaUsuario.equals(other.cuentaUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.EntidadPerfil[ cuentaUsuario=" + cuentaUsuario + " ]";
    }
    
    public Perfil obtenerPerfil() {
        return new Perfil(
                this.genero,
                this.correo,
                this.fechaNacimiento,
                this.dificultad,
                this.idioma,                
                this.foto,
                this.fechaCreacion,
                this.cuentaUsuario
            );
    }
    
    public EntidadPerfil(Perfil perfil) {
        this.correo = perfil.getCorreo();
        this.cuentaUsuario = perfil.getCuentaUsuario();
        this.dificultad = perfil.getDificultad();
        this.fechaCreacion = perfil.getFechaCreacion();
        this.fechaNacimiento = perfil.getFechaNacimiento();
        this.foto = perfil.getFoto();
        this.genero = perfil.getGenero();
        this.idioma = perfil.getIdioma();
    }
    
}
