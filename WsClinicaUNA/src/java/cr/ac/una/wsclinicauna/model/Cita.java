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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "CLN_CITAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cita.findAll", query = "SELECT c FROM Cita c", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Cita.findByCtId", query = "SELECT c FROM Cita c WHERE c.ctId = :ctId", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Cita.findByCtCorreo", query = "SELECT c FROM Cita c WHERE c.ctCorreo = :ctCorreo")
    , @NamedQuery(name = "Cita.findByCtEstado", query = "SELECT c FROM Cita c WHERE c.ctEstado = :ctEstado")
    , @NamedQuery(name = "Cita.findByCtTelefono", query = "SELECT c FROM Cita c WHERE c.ctTelefono = :ctTelefono")
    , @NamedQuery(name = "Cita.findByCtMotivo", query = "SELECT c FROM Cita c WHERE c.ctMotivo = :ctMotivo")
    , @NamedQuery(name = "Cita.findByCtCorreoenviado", query = "SELECT c FROM Cita c WHERE c.ctCorreoenviado = :ctCorreoenviado")
    , @NamedQuery(name = "Cita.findByCtVersion", query = "SELECT c FROM Cita c WHERE c.ctVersion = :ctVersion")})
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "CT_ID_GENERATOR", sequenceName = "ClinicaUNA.SEQ_CITAS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CT_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "CT_ID")
    private Long ctId;
    @Basic(optional = false)
    @Column(name = "CT_CORREO")
    private String ctCorreo;
    @Basic(optional = false)
    @Column(name = "CT_ESTADO")
    private String ctEstado;
    @Basic(optional = false)
    @Column(name = "CT_TELEFONO")
    private String ctTelefono;
    @Column(name = "CT_MOTIVO")
    private String ctMotivo;
    @Basic(optional = false)
    @Column(name = "CT_CORREOENVIADO")
    private String ctCorreoenviado;
    @Basic(optional = false)
    @Column(name = "CT_VERSION")
    private Long ctVersion;
    @JoinColumn(name = "CT_PACIENTE", referencedColumnName = "PAC_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Paciente ctPaciente;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "espCita", fetch = FetchType.LAZY)
    private List<Espacio> espacioList;

    public Cita() {
    }

    public Cita(CitaDto citaDto) {
        this.ctId = citaDto.getID();
        actualizarCita(citaDto);
    }

    public void actualizarCita(CitaDto cita) {
        this.ctVersion = cita.getCtVersion();
        this.ctPaciente = new Paciente(cita.getPaciente());
        this.ctMotivo = cita.getMotivo();
        this.ctEstado = cita.getEstado();
        this.ctCorreo = cita.getCorreo();
        this.ctTelefono = cita.getTelefono();
        this.ctCorreoenviado = cita.getCorreoEnviado();
    }

    public Cita(Long ctId) {
        this.ctId = ctId;
    }

    public Cita(Long ctId, String ctCorreo, String ctEstado, String ctTelefono, String ctCorreoenviado, Long ctVersion) {
        this.ctId = ctId;
        this.ctCorreo = ctCorreo;
        this.ctEstado = ctEstado;
        this.ctTelefono = ctTelefono;
        this.ctCorreoenviado = ctCorreoenviado;
        this.ctVersion = ctVersion;
    }

    public Long getCtId() {
        return ctId;
    }

    public void setCtId(Long ctId) {
        this.ctId = ctId;
    }

    public String getCtCorreo() {
        return ctCorreo;
    }

    public void setCtCorreo(String ctCorreo) {
        this.ctCorreo = ctCorreo;
    }

    public String getCtEstado() {
        return ctEstado;
    }

    public void setCtEstado(String ctEstado) {
        this.ctEstado = ctEstado;
    }

    public String getCtTelefono() {
        return ctTelefono;
    }

    public void setCtTelefono(String ctTelefono) {
        this.ctTelefono = ctTelefono;
    }

    public String getCtMotivo() {
        return ctMotivo;
    }

    public void setCtMotivo(String ctMotivo) {
        this.ctMotivo = ctMotivo;
    }

    public String getCtCorreoenviado() {
        return ctCorreoenviado;
    }

    public void setCtCorreoenviado(String ctCorreoenviado) {
        this.ctCorreoenviado = ctCorreoenviado;
    }

    public Long getCtVersion() {
        return ctVersion;
    }

    public void setCtVersion(Long ctVersion) {
        this.ctVersion = ctVersion;
    }

    public Paciente getCtPaciente() {
        return ctPaciente;
    }

    public void setCtPaciente(Paciente ctPaciente) {
        this.ctPaciente = ctPaciente;
    }

    public List<Espacio> getEspacioList() {
        return espacioList;
    }

    public void setEspacioList(List<Espacio> espacioList) {
        this.espacioList = espacioList;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctId != null ? ctId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cita)) {
            return false;
        }
        Cita other = (Cita) object;
        if ((this.ctId == null && other.ctId != null) || (this.ctId != null && !this.ctId.equals(other.ctId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillaws2.model.Cita[ ctId=" + ctId + " ]";
    }

}
