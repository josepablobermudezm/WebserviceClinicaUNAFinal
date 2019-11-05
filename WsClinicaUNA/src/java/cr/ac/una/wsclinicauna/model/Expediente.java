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
@Table(name = "CLN_EXPEDIENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Expediente.findAll", query = "SELECT e FROM Expediente e", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Expediente.findByExpId", query = "SELECT e FROM Expediente e WHERE e.expId = :expId" ,hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Expediente.findByExpHospitalizaciones", query = "SELECT e FROM Expediente e WHERE e.expHospitalizaciones = :expHospitalizaciones")
    , @NamedQuery(name = "Expediente.findByExpOperaciones", query = "SELECT e FROM Expediente e WHERE e.expOperaciones = :expOperaciones")
    , @NamedQuery(name = "Expediente.findByExpAlergias", query = "SELECT e FROM Expediente e WHERE e.expAlergias = :expAlergias")
    , @NamedQuery(name = "Expediente.findByExpTratamientos", query = "SELECT e FROM Expediente e WHERE e.expTratamientos = :expTratamientos")
    , @NamedQuery(name = "Expediente.findByExpVersion", query = "SELECT e FROM Expediente e WHERE e.expVersion = :expVersion")
    , @NamedQuery(name = "Expediente.findByExpAntecedentePatologicos", query = "SELECT e FROM Expediente e WHERE e.expAntecedentePatologicos = :expAntecedentePatologicos")})
public class Expediente implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "EXP_ID_GENERATOR", sequenceName = "ClinicaUNA.SEQ_EXPEDIENTES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXP_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "EXP_ID")
    private Long expId;
    @Basic(optional = false)
    @Column(name = "EXP_HOSPITALIZACIONES")
    private String expHospitalizaciones;
    @Basic(optional = false)
    @Column(name = "EXP_OPERACIONES")
    private String expOperaciones;
    @Column(name = "EXP_ALERGIAS")
    private String expAlergias;
    @Column(name = "EXP_TRATAMIENTOS")
    private String expTratamientos;
    @Basic(optional = false)
    @Column(name = "EXP_VERSION")
    private Long expVersion;
    @Column(name = "EXP_ANTECEDENTE_PATOLOGICOS")
    private String expAntecedentePatologicos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "antExpediente", fetch = FetchType.LAZY)
    private List<Antecedente> antecedenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cntExpediente", fetch = FetchType.LAZY)
    private List<Control> controlList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exmExpediente", fetch = FetchType.LAZY)
    private List<Examen> examenList;
    @JoinColumn(name = "EXP_PACIENTE", referencedColumnName = "PAC_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Paciente expPaciente;

    public Expediente() {
    }
    
    public Expediente(ExpedienteDto expedienteDto) {
        this.expId = expedienteDto.getExpID();
        actualizarExpediente(expedienteDto);
    }
    
    public void actualizarExpediente(ExpedienteDto expediente) {
        this.expAlergias = expediente.getAlergias();
        this.expAntecedentePatologicos = expediente.getAntecedentesPatologicos();
        this.expHospitalizaciones = expediente.getHospitalizaciones();
        this.expOperaciones = expediente.getOperaciones();
        this.expTratamientos = expediente.getTratamientos();
        this.expVersion = expediente.getExpVersion();
        this.expPaciente = new Paciente(expediente.getPaciente());
    }

    public Expediente(Long expId) {
        this.expId = expId;
    }

    public Expediente(Long expId, String expHospitalizaciones, String expOperaciones, Long expVersion) {
        this.expId = expId;
        this.expHospitalizaciones = expHospitalizaciones;
        this.expOperaciones = expOperaciones;
        this.expVersion = expVersion;
    }

    public Long getExpId() {
        return expId;
    }

    public void setExpId(Long expId) {
        this.expId = expId;
    }

    public String getExpHospitalizaciones() {
        return expHospitalizaciones;
    }

    public void setExpHospitalizaciones(String expHospitalizaciones) {
        this.expHospitalizaciones = expHospitalizaciones;
    }

    public String getExpOperaciones() {
        return expOperaciones;
    }

    public void setExpOperaciones(String expOperaciones) {
        this.expOperaciones = expOperaciones;
    }

    public String getExpAlergias() {
        return expAlergias;
    }

    public void setExpAlergias(String expAlergias) {
        this.expAlergias = expAlergias;
    }

    public String getExpTratamientos() {
        return expTratamientos;
    }

    public void setExpTratamientos(String expTratamientos) {
        this.expTratamientos = expTratamientos;
    }

    public Long getExpVersion() {
        return expVersion;
    }

    public void setExpVersion(Long expVersion) {
        this.expVersion = expVersion;
    }

    public String getExpAntecedentePatologicos() {
        return expAntecedentePatologicos;
    }

    public void setExpAntecedentePatologicos(String expAntecedentePatologicos) {
        this.expAntecedentePatologicos = expAntecedentePatologicos;
    }

    public List<Antecedente> getAntecedenteList() {
        return antecedenteList;
    }

    public void setAntecedenteList(List<Antecedente> antecedenteList) {
        this.antecedenteList = antecedenteList;
    }

    public List<Control> getControlList() {
        return controlList;
    }

    public void setControlList(List<Control> controlList) {
        this.controlList = controlList;
    }

    public List<Examen> getExamenList() {
        return examenList;
    }

    public void setExamenList(List<Examen> examenList) {
        this.examenList = examenList;
    }

    public Paciente getExpPaciente() {
        return expPaciente;
    }

    public void setExpPaciente(Paciente expPaciente) {
        this.expPaciente = expPaciente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (expId != null ? expId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Expediente)) {
            return false;
        }
        Expediente other = (Expediente) object;
        if ((this.expId == null && other.expId != null) || (this.expId != null && !this.expId.equals(other.expId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillaws2.model.Expediente[ expId=" + expId + " ]";
    }
    
}
