/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.model;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Olivares
 */
@Entity
@Table(name = "CLN_EXAMENES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Examen.findAll", query = "SELECT e FROM Examen e", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Examen.findByExmId", query = "SELECT e FROM Examen e WHERE e.exmId = :exmId", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Examen.findByExmNombreExamen", query = "SELECT e FROM Examen e WHERE e.exmNombreExamen = :exmNombreExamen")
    , @NamedQuery(name = "Examen.findByExmFecha", query = "SELECT e FROM Examen e WHERE e.exmFecha = :exmFecha")
    , @NamedQuery(name = "Examen.findByExmAnotaciones", query = "SELECT e FROM Examen e WHERE e.exmAnotaciones = :exmAnotaciones")
    , @NamedQuery(name = "Examen.findByExmVersion", query = "SELECT e FROM Examen e WHERE e.exmVersion = :exmVersion")})
public class Examen implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "EXM_ID_GENERATOR", sequenceName = "ClinicaUNA.SEQ_EXAMENES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXM_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "EXM_ID")
    private Long exmId;
    @Basic(optional = false)
    @Column(name = "EXM_NOMBRE_EXAMEN")
    private String exmNombreExamen;
    @Basic(optional = false)
    @Column(name = "EXM_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exmFecha;
    @Basic(optional = false)
    @Column(name = "EXM_ANOTACIONES")
    private String exmAnotaciones;
    @Basic(optional = false)
    @Column(name = "EXM_VERSION")
    private Long exmVersion;
    @JoinColumn(name = "EXM_EXPEDIENTE", referencedColumnName = "EXP_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Expediente exmExpediente;

    public Examen() {
    }
    
    public void actualizarExamen(ExamenDto examen){
        this.exmVersion = examen.getExmVersion();
        this.exmNombreExamen = examen.getNombreExamen();
        this.exmFecha = java.util.Date.from(examen.getFecha().atStartOfDay()
      .atZone(ZoneId.systemDefault())
      .toInstant());
        this.exmAnotaciones = examen.getAnotaciones();
        this.exmExpediente = new Expediente(examen.getExpediente());
    }
    
    public Examen(ExamenDto examenDto) {
        this.exmId = examenDto.getExmID();
        actualizarExamen(examenDto);
    }

    public Examen(Long exmId) {
        this.exmId = exmId;
    }

    public Examen(Long exmId, String exmNombreExamen, Date exmFecha, String exmAnotaciones, Long exmVersion) {
        this.exmId = exmId;
        this.exmNombreExamen = exmNombreExamen;
        this.exmFecha = exmFecha;
        this.exmAnotaciones = exmAnotaciones;
        this.exmVersion = exmVersion;
    }

    public Long getExmId() {
        return exmId;
    }

    public void setExmId(Long exmId) {
        this.exmId = exmId;
    }

    public String getExmNombreExamen() {
        return exmNombreExamen;
    }

    public void setExmNombreExamen(String exmNombreExamen) {
        this.exmNombreExamen = exmNombreExamen;
    }

    public Date getExmFecha() {
        return exmFecha;
    }

    public void setExmFecha(Date exmFecha) {
        this.exmFecha = exmFecha;
    }

    public String getExmAnotaciones() {
        return exmAnotaciones;
    }

    public void setExmAnotaciones(String exmAnotaciones) {
        this.exmAnotaciones = exmAnotaciones;
    }

    public Long getExmVersion() {
        return exmVersion;
    }

    public void setExmVersion(Long exmVersion) {
        this.exmVersion = exmVersion;
    }

    public Expediente getExmExpediente() {
        return exmExpediente;
    }

    public void setExmExpediente(Expediente exmExpediente) {
        this.exmExpediente = exmExpediente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exmId != null ? exmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Examen)) {
            return false;
        }
        Examen other = (Examen) object;
        if ((this.exmId == null && other.exmId != null) || (this.exmId != null && !this.exmId.equals(other.exmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillaws2.model.Examen[ exmId=" + exmId + " ]";
    }
    
}
