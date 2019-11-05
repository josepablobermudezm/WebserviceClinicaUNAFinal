/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
@Table(name = "CLN_ESPACIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Espacio.findAll", query = "SELECT e FROM Espacio e", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Espacio.findByEspId", query = "SELECT e FROM Espacio e WHERE e.espId = :espId", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Espacio.findByEspHoraFin", query = "SELECT e FROM Espacio e WHERE e.espHoraFin = :espHoraFin")
    , @NamedQuery(name = "Espacio.findByEspHoraInicio", query = "SELECT e FROM Espacio e WHERE e.espHoraInicio = :espHoraInicio")
    , @NamedQuery(name = "Espacio.findByEspVersion", query = "SELECT e FROM Espacio e WHERE e.espVersion = :espVersion")})
public class Espacio implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "ESP_ID_GENERATOR", sequenceName = "ClinicaUNA.SEQ_ESPACIOS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESP_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "ESP_ID")
    private Long espId;
    @Basic(optional = false)
    @Column(name = "ESP_HORA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date espHoraFin;
    @Basic(optional = false)
    @Column(name = "ESP_HORA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date espHoraInicio;
    @Basic(optional = false)
    @Column(name = "ESP_VERSION")
    private Long espVersion;
    @JoinColumn(name = "ESP_CITA", referencedColumnName = "CT_ID")
    @ManyToOne( optional = false, fetch = FetchType.LAZY)
    private Cita espCita;
    @JoinColumn(name = "ESP_AGENDA", referencedColumnName = "AGE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Agenda espAgenda;

    public Espacio() {
    }
    
    public Espacio(EspacioDto espacioHoraDto) {
        this.espId = espacioHoraDto.getEspId();
        actualizarEspacio(espacioHoraDto);
    }
    
    public void actualizarEspacio(EspacioDto espacioh){
        //Arreglar 
        this.espVersion = espacioh.getEspVersion();
        //Agregar conversion de fecha 
        this.espAgenda = new Agenda(espacioh.getEspAgenda());
        if (espacioh.getEspHoraInicio() != null && espacioh.getEspHoraFin() != null) {
            LocalDateTime inicioJornada = LocalDateTime.parse(espacioh.getEspHoraInicio(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            LocalDateTime finJornada = LocalDateTime.parse( espacioh.getEspHoraFin(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            this.espHoraInicio = Date.from(inicioJornada.atZone(ZoneId.systemDefault()).toInstant());
            this.espHoraFin = Date.from(finJornada.atZone(ZoneId.systemDefault()).toInstant());
        }
        this.espCita = new Cita(espacioh.getEspCita());
    }

    public Espacio(Long espId) {
        this.espId = espId;
    }

    public Espacio(Long espId, Date espHoraFin, Date espHoraInicio, Long espVersion) {
        this.espId = espId;
        this.espHoraFin = espHoraFin;
        this.espHoraInicio = espHoraInicio;
        this.espVersion = espVersion;
    }

    public Long getEspId() {
        return espId;
    }

    public void setEspId(Long espId) {
        this.espId = espId;
    }

    public Date getEspHoraFin() {
        return espHoraFin;
    }

    public void setEspHoraFin(Date espHoraFin) {
        this.espHoraFin = espHoraFin;
    }

    public Date getEspHoraInicio() {
        return espHoraInicio;
    }

    public void setEspHoraInicio(Date espHoraInicio) {
        this.espHoraInicio = espHoraInicio;
    }

    public Long getEspVersion() {
        return espVersion;
    }

    public void setEspVersion(Long espVersion) {
        this.espVersion = espVersion;
    }

    public Cita getEspCita() {
        return espCita;
    }

    public void setEspCita(Cita espCita) {
        this.espCita = espCita;
    }

    

    public Agenda getEspAgenda() {
        return espAgenda;
    }

    public void setEspAgenda(Agenda espAgenda) {
        this.espAgenda = espAgenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (espId != null ? espId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Espacio)) {
            return false;
        }
        Espacio other = (Espacio) object;
        if ((this.espId == null && other.espId != null) || (this.espId != null && !this.espId.equals(other.espId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillaws2.model.Espacio[ espId=" + espId + " ]";
    }
    
}
