/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Olivares
 */
@Entity
@Table(name = "CLN_USUARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Usuario.findByUsId", query = "SELECT u FROM Usuario u WHERE u.usId = :usId", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Usuario.findByUsNombre", query = "SELECT u FROM Usuario u WHERE u.usNombre = :usNombre")
    , @NamedQuery(name = "Usuario.findByUsPapellido", query = "SELECT u FROM Usuario u WHERE u.usPapellido = :usPapellido")
    , @NamedQuery(name = "Usuario.findByUsSapellido", query = "SELECT u FROM Usuario u WHERE u.usSapellido = :usSapellido")
    , @NamedQuery(name = "Usuario.findByUsCedula", query = "SELECT u FROM Usuario u WHERE u.usCedula = :usCedula")
    , @NamedQuery(name = "Usuario.findByUsCorreo", query = "SELECT u FROM Usuario u WHERE u.usCorreo = :usCorreo")
    , @NamedQuery(name = "Usuario.findByUsTipousuario", query = "SELECT u FROM Usuario u WHERE u.usTipousuario = :usTipousuario")
    , @NamedQuery(name = "Usuario.findByUsIdioma", query = "SELECT u FROM Usuario u WHERE u.usIdioma = :usIdioma")
    , @NamedQuery(name = "Usuario.findByUsEstado", query = "SELECT u FROM Usuario u WHERE u.usEstado = :usEstado")
    , @NamedQuery(name = "Usuario.findByUsNombreUsuario", query = "SELECT u FROM Usuario u WHERE u.usNombreUsuario = :usNombreUsuario")
    , @NamedQuery(name = "Usuario.findByUsContrasena", query = "SELECT u FROM Usuario u WHERE u.usContrasena = :usContrasena")
    , @NamedQuery(name = "Usuario.findByUsContrasenatemp", query = "SELECT u FROM Usuario u WHERE u.usContrasenatemp = :usContrasenatemp", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Usuario.findByUsVersion", query = "SELECT u FROM Usuario u WHERE u.usVersion = :usVersion")
    , @NamedQuery(name = "Usuario.findByUsuClave", query = "SELECT u FROM Usuario u WHERE u.usNombreUsuario = :usUsuario and (u.usContrasena = :usClave OR u.usContrasenatemp =:usClaveTemp)", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Usuario.findByUsNombreCedulaApellido", query = "SELECT u FROM Usuario u WHERE UPPER(u.usNombre) like :UsNombre and UPPER(u.usCedula) like :UsCedula and UPPER(u.usPapellido) like :UsPapellido",hints = @QueryHint(name = "eclipselink.refresh", value = "true"))})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "USU_ID_GENERATOR", sequenceName = "ClinicaUNA.SEQ_USUARIOS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USU_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "US_ID")
    private Long usId;
    @Basic(optional = false)
    @Column(name = "US_NOMBRE")
    private String usNombre;
    @Basic(optional = false)
    @Column(name = "US_PAPELLIDO")
    private String usPapellido;
    @Basic(optional = false)
    @Column(name = "US_SAPELLIDO")
    private String usSapellido;
    @Basic(optional = false)
    @Column(name = "US_CEDULA")
    private String usCedula;
    @Basic(optional = false)
    @Column(name = "US_CORREO")
    private String usCorreo;
    @Basic(optional = false)
    @Column(name = "US_TIPOUSUARIO")
    private String usTipousuario;
    @Basic(optional = false)
    @Column(name = "US_IDIOMA")
    private String usIdioma;
    @Basic(optional = false)
    @Column(name = "US_ESTADO")
    private String usEstado;
    @Basic(optional = false)
    @Column(name = "US_NOMBRE_USUARIO")
    private String usNombreUsuario;
    @Basic(optional = false)
    @Column(name = "US_CONTRASENA")
    private String usContrasena;
    @Column(name = "US_CONTRASENATEMP")
    private String usContrasenatemp;
    @Basic(optional = false)
    @Column(name = "US_VERSION")
    private Long usVersion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medUsuario", fetch = FetchType.LAZY)
    private List<Medico> medicoList;

    public Usuario() {
    }

    public Usuario(UsuarioDto UsuarioDto) {
        this.usId = UsuarioDto.getID();
        actualizarUsuario(UsuarioDto);
    }

    public void actualizarUsuario(UsuarioDto UsuarioDto) {
        this.usCedula = UsuarioDto.getCedula();
        this.usContrasena = UsuarioDto.getContrasenna();
        this.usContrasenatemp = UsuarioDto.getContrasennaTemp();
        this.usCorreo = UsuarioDto.getCorreo();
        this.usEstado = UsuarioDto.getEstado();
        this.usIdioma = UsuarioDto.getIdioma();
        this.usNombre = UsuarioDto.getNombre();
        this.usNombreUsuario = UsuarioDto.getNombreUsuario();
        this.usPapellido = UsuarioDto.getpApellido();
        this.usSapellido = UsuarioDto.getsApellido();
        this.usTipousuario = UsuarioDto.getTipoUsuario();
        this.usVersion = UsuarioDto.getUsVersion();
        //this.medicoList = UsuarioDto.get
    }

    public Usuario(Long usId) {
        this.usId = usId;
    }

    public Usuario(Long usId, String usNombre, String usPapellido, String usSapellido, String usCedula, String usCorreo, String usTipousuario, String usIdioma, String usEstado, String usNombreUsuario, String usContrasena, Long usVersion) {
        this.usId = usId;
        this.usNombre = usNombre;
        this.usPapellido = usPapellido;
        this.usSapellido = usSapellido;
        this.usCedula = usCedula;
        this.usCorreo = usCorreo;
        this.usTipousuario = usTipousuario;
        this.usIdioma = usIdioma;
        this.usEstado = usEstado;
        this.usNombreUsuario = usNombreUsuario;
        this.usContrasena = usContrasena;
        this.usVersion = usVersion;
    }

    public Long getUsId() {
        return usId;
    }

    public void setUsId(Long usId) {
        this.usId = usId;
    }

    public String getUsNombre() {
        return usNombre;
    }

    public void setUsNombre(String usNombre) {
        this.usNombre = usNombre;
    }

    public String getUsPapellido() {
        return usPapellido;
    }

    public void setUsPapellido(String usPapellido) {
        this.usPapellido = usPapellido;
    }

    public String getUsSapellido() {
        return usSapellido;
    }

    public void setUsSapellido(String usSapellido) {
        this.usSapellido = usSapellido;
    }

    public String getUsCedula() {
        return usCedula;
    }

    public void setUsCedula(String usCedula) {
        this.usCedula = usCedula;
    }

    public String getUsCorreo() {
        return usCorreo;
    }

    public void setUsCorreo(String usCorreo) {
        this.usCorreo = usCorreo;
    }

    public String getUsTipousuario() {
        return usTipousuario;
    }

    public void setUsTipousuario(String usTipousuario) {
        this.usTipousuario = usTipousuario;
    }

    public String getUsIdioma() {
        return usIdioma;
    }

    public void setUsIdioma(String usIdioma) {
        this.usIdioma = usIdioma;
    }

    public String getUsEstado() {
        return usEstado;
    }

    public void setUsEstado(String usEstado) {
        this.usEstado = usEstado;
    }

    public String getUsNombreUsuario() {
        return usNombreUsuario;
    }

    public void setUsNombreUsuario(String usNombreUsuario) {
        this.usNombreUsuario = usNombreUsuario;
    }

    public String getUsContrasena() {
        return usContrasena;
    }

    public void setUsContrasena(String usContrasena) {
        this.usContrasena = usContrasena;
    }

    public String getUsContrasenatemp() {
        return usContrasenatemp;
    }

    public void setUsContrasenatemp(String usContrasenatemp) {
        this.usContrasenatemp = usContrasenatemp;
    }

    public Long getUsVersion() {
        return usVersion;
    }

    public void setUsVersion(Long usVersion) {
        this.usVersion = usVersion;
    }

    public List<Medico> getMedicoList() {
        return medicoList;
    }

    public void setMedicoList(List<Medico> medicoList) {
        this.medicoList = medicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usId != null ? usId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usId == null && other.usId != null) || (this.usId != null && !this.usId.equals(other.usId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillaws2.model.Usuario[ usId=" + usId + " ]";
    }

}
