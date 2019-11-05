/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.model;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos Olivares
 */
@Entity
@Table(name = "CLN_AGENDAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agenda.findAll", query = "SELECT a FROM Agenda a", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Agenda.findByAgeId", query = "SELECT a FROM Agenda a WHERE a.ageId = :ageId", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Agenda.findByAgeFecha", query = "SELECT a FROM Agenda a WHERE a.ageFecha = :ageFecha AND a.ageMedico = :ageMedico", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Agenda.findByAgeVersion", query = "SELECT a FROM Agenda a WHERE a.ageVersion = :ageVersion")
    , @NamedQuery(name = "Agenda.findByAgendas", query = "SELECT a FROM Agenda a WHERE a.ageFecha >= :fechaInicio AND a.ageFecha <= :fechaFinal", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))    
})
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "AGE_ID_GENERATOR", sequenceName = "ClinicaUNA.SEQ_AGENDAS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AGE_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "AGE_ID")
    private Long ageId;
    @Basic(optional = false)
    @Column(name = "AGE_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ageFecha;
    @Basic(optional = false)
    @Column(name = "AGE_VERSION")
    private Long ageVersion;
    @JoinColumn(name = "AGE_MEDICO", referencedColumnName = "MED_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Medico ageMedico;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "espAgenda", fetch = FetchType.LAZY)
    private List<Espacio> espacioList;

    public Agenda() {
    }
    
    public Agenda(AgendaDto agendaDto) {
        this.ageId = agendaDto.getAgeId();
        actualizarAgenda(agendaDto);
    }

    public void actualizarAgenda(AgendaDto agenda) {
        this.ageMedico = new Medico(agenda.getAgeMedico());
        this.ageFecha = Date.from(agenda.getAgeFecha().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        this.ageVersion = agenda.getAgeVersion();
    }

    public Agenda(Long ageId) {
        this.ageId = ageId;
    }

    public Agenda(Long ageId, Date ageFecha, Long ageVersion) {
        this.ageId = ageId;
        this.ageFecha = ageFecha;
        this.ageVersion = ageVersion;
    }

    public Long getAgeId() {
        return ageId;
    }

    public void setAgeId(Long ageId) {
        this.ageId = ageId;
    }

    public Date getAgeFecha() {
        return ageFecha;
    }

    public void setAgeFecha(Date ageFecha) {
        this.ageFecha = ageFecha;
    }

    public Long getAgeVersion() {
        return ageVersion;
    }

    public void setAgeVersion(Long ageVersion) {
        this.ageVersion = ageVersion;
    }

    public Medico getAgeMedico() {
        return ageMedico;
    }

    public void setAgeMedico(Medico ageMedico) {
        this.ageMedico = ageMedico;
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
        hash += (ageId != null ? ageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agenda)) {
            return false;
        }
        Agenda other = (Agenda) object;
        if ((this.ageId == null && other.ageId != null) || (this.ageId != null && !this.ageId.equals(other.ageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillaws2.model.Agenda[ ageId=" + ageId + " ]";
    }
    
}
