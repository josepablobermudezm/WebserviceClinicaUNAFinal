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
@Table(name = "CLN_CONTROLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Control.findAll", query = "SELECT c FROM Control c", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Control.findByCntId", query = "SELECT c FROM Control c WHERE c.cntId = :cntId", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
    , @NamedQuery(name = "Control.findByCntFecha", query = "SELECT c FROM Control c WHERE c.cntFecha = :cntFecha")
    , @NamedQuery(name = "Control.findByCntHora", query = "SELECT c FROM Control c WHERE c.cntHora = :cntHora")
    , @NamedQuery(name = "Control.findByCntPresion", query = "SELECT c FROM Control c WHERE c.cntPresion = :cntPresion")
    , @NamedQuery(name = "Control.findByCntFrecuenciaCardiaca", query = "SELECT c FROM Control c WHERE c.cntFrecuenciaCardiaca = :cntFrecuenciaCardiaca")
    , @NamedQuery(name = "Control.findByCntPeso", query = "SELECT c FROM Control c WHERE c.cntPeso = :cntPeso")
    , @NamedQuery(name = "Control.findByCntTalla", query = "SELECT c FROM Control c WHERE c.cntTalla = :cntTalla")
    , @NamedQuery(name = "Control.findByCntTemperatura", query = "SELECT c FROM Control c WHERE c.cntTemperatura = :cntTemperatura")
    , @NamedQuery(name = "Control.findByCntImc", query = "SELECT c FROM Control c WHERE c.cntImc = :cntImc")
    , @NamedQuery(name = "Control.findByCntAnotacionEnfermeria", query = "SELECT c FROM Control c WHERE c.cntAnotacionEnfermeria = :cntAnotacionEnfermeria")
    , @NamedQuery(name = "Control.findByCntRazonConsulta", query = "SELECT c FROM Control c WHERE c.cntRazonConsulta = :cntRazonConsulta")
    , @NamedQuery(name = "Control.findByCntPlanAtencion", query = "SELECT c FROM Control c WHERE c.cntPlanAtencion = :cntPlanAtencion")
    , @NamedQuery(name = "Control.findByCntObservaciones", query = "SELECT c FROM Control c WHERE c.cntObservaciones = :cntObservaciones")
    , @NamedQuery(name = "Control.findByCntExamenFisico", query = "SELECT c FROM Control c WHERE c.cntExamenFisico = :cntExamenFisico")
    , @NamedQuery(name = "Control.findByCntTratamiento", query = "SELECT c FROM Control c WHERE c.cntTratamiento = :cntTratamiento")
    , @NamedQuery(name = "Control.findByCntVersion", query = "SELECT c FROM Control c WHERE c.cntVersion = :cntVersion")})
public class Control implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "CNT_ID_GENERATOR", sequenceName = "ClinicaUNA.SEQ_CONTROL_PACIENTES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CNT_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "CNT_ID")
    private Long cntId;
    @Basic(optional = false)
    @Column(name = "CNT_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cntFecha;
    @Basic(optional = false)
    @Column(name = "CNT_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cntHora;
    @Basic(optional = false)
    @Column(name = "CNT_PRESION")
    private Double cntPresion;
    @Basic(optional = false)
    @Column(name = "CNT_FRECUENCIA_CARDIACA")
    private Double cntFrecuenciaCardiaca;
    @Basic(optional = false)
    @Column(name = "CNT_PESO")
    private Double cntPeso;
    @Basic(optional = false)
    @Column(name = "CNT_TALLA")
    private Double cntTalla;
    @Basic(optional = false)
    @Column(name = "CNT_TEMPERATURA")
    private Double cntTemperatura;
    @Basic(optional = false)
    @Column(name = "CNT_IMC")
    private Double cntImc;
    @Basic(optional = false)
    @Column(name = "CNT_ANOTACION_ENFERMERIA")
    private String cntAnotacionEnfermeria;
    @Basic(optional = false)
    @Column(name = "CNT_RAZON_CONSULTA")
    private String cntRazonConsulta;
    @Basic(optional = false)
    @Column(name = "CNT_PLAN_ATENCION")
    private String cntPlanAtencion;
    @Basic(optional = false)
    @Column(name = "CNT_OBSERVACIONES")
    private String cntObservaciones;
    @Basic(optional = false)
    @Column(name = "CNT_EXAMEN_FISICO")
    private String cntExamenFisico;
    @Basic(optional = false)
    @Column(name = "CNT_TRATAMIENTO")
    private String cntTratamiento;
    @Basic(optional = false)
    @Column(name = "CNT_VERSION")
    private Long cntVersion;
    @JoinColumn(name = "CNT_EXPEDIENTE", referencedColumnName = "EXP_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Expediente cntExpediente;

    public Control() {
    }
    
     public Control(ControlDto control) {
        this.cntId = control.getCntId();
        this.actualizarControl(control);
    }

    public void actualizarControl(ControlDto control) {

        this.cntVersion = control.getCntVersion();
        this.cntFecha = Date.from(control.getCntFecha().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        if (control.getCntHora()!= null) {
            LocalDateTime hora = LocalDateTime.parse(control.getCntHora(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            this.cntHora = Date.from(hora.atZone(ZoneId.systemDefault()).toInstant());
        }
        this.cntPresion = control.getCntPresion();
        this.cntFrecuenciaCardiaca = control.getCntFrecuenciaCardiaca();
        this.cntPeso = control.getCntPeso();
        this.cntTalla = control.getCntTalla();
        this.cntTemperatura = control.getCntTemperatura();
        this.cntImc = control.getCntImc();
        this.cntAnotacionEnfermeria = control.getCntAnotacionEnfermeria();
        this.cntRazonConsulta = control.getCntRazonConsulta();
        this.cntPlanAtencion = control.getCntPlanAtencion();
        this.cntObservaciones = control.getCntObservaciones();
        this.cntExamenFisico = control.getCntExamenFisico();
        this.cntTratamiento = control.getCntTratamiento();
        this.cntExpediente = new Expediente(control.getCntExpediente());
    }

    
    public Control(Long cntId) {
        this.cntId = cntId;
    }

    public Control(Long cntId, Date cntFecha, Date cntHora, Double cntPresion, Double cntFrecuenciaCardiaca, Double cntPeso, Double cntTalla, Double cntTemperatura, Double cntImc, String cntAnotacionEnfermeria, String cntRazonConsulta, String cntPlanAtencion, String cntObservaciones, String cntExamenFisico, String cntTratamiento, Long cntVersion, Expediente cntExpediente) {
        this.cntId = cntId;
        this.cntFecha = cntFecha;
        this.cntHora = cntHora;
        this.cntPresion = cntPresion;
        this.cntFrecuenciaCardiaca = cntFrecuenciaCardiaca;
        this.cntPeso = cntPeso;
        this.cntTalla = cntTalla;
        this.cntTemperatura = cntTemperatura;
        this.cntImc = cntImc;
        this.cntAnotacionEnfermeria = cntAnotacionEnfermeria;
        this.cntRazonConsulta = cntRazonConsulta;
        this.cntPlanAtencion = cntPlanAtencion;
        this.cntObservaciones = cntObservaciones;
        this.cntExamenFisico = cntExamenFisico;
        this.cntTratamiento = cntTratamiento;
        this.cntVersion = cntVersion;
        this.cntExpediente = cntExpediente;
    }

    

    public Long getCntId() {
        return cntId;
    }

    public void setCntId(Long cntId) {
        this.cntId = cntId;
    }

    public Date getCntFecha() {
        return cntFecha;
    }

    public void setCntFecha(Date cntFecha) {
        this.cntFecha = cntFecha;
    }

    public Date getCntHora() {
        return cntHora;
    }

    public void setCntHora(Date cntHora) {
        this.cntHora = cntHora;
    }

    public Double getCntPresion() {
        return cntPresion;
    }

    public void setCntPresion(Double cntPresion) {
        this.cntPresion = cntPresion;
    }

    public Double getCntFrecuenciaCardiaca() {
        return cntFrecuenciaCardiaca;
    }

    public void setCntFrecuenciaCardiaca(Double cntFrecuenciaCardiaca) {
        this.cntFrecuenciaCardiaca = cntFrecuenciaCardiaca;
    }

    public Double getCntPeso() {
        return cntPeso;
    }

    public void setCntPeso(Double cntPeso) {
        this.cntPeso = cntPeso;
    }

    public Double getCntTalla() {
        return cntTalla;
    }

    public void setCntTalla(Double cntTalla) {
        this.cntTalla = cntTalla;
    }

    public Double getCntTemperatura() {
        return cntTemperatura;
    }

    public void setCntTemperatura(Double cntTemperatura) {
        this.cntTemperatura = cntTemperatura;
    }

    public Double getCntImc() {
        return cntImc;
    }

    public void setCntImc(Double cntImc) {
        this.cntImc = cntImc;
    }

    

    public String getCntAnotacionEnfermeria() {
        return cntAnotacionEnfermeria;
    }

    public void setCntAnotacionEnfermeria(String cntAnotacionEnfermeria) {
        this.cntAnotacionEnfermeria = cntAnotacionEnfermeria;
    }

    public String getCntRazonConsulta() {
        return cntRazonConsulta;
    }

    public void setCntRazonConsulta(String cntRazonConsulta) {
        this.cntRazonConsulta = cntRazonConsulta;
    }

    public String getCntPlanAtencion() {
        return cntPlanAtencion;
    }

    public void setCntPlanAtencion(String cntPlanAtencion) {
        this.cntPlanAtencion = cntPlanAtencion;
    }

    public String getCntObservaciones() {
        return cntObservaciones;
    }

    public void setCntObservaciones(String cntObservaciones) {
        this.cntObservaciones = cntObservaciones;
    }

    public String getCntExamenFisico() {
        return cntExamenFisico;
    }

    public void setCntExamenFisico(String cntExamenFisico) {
        this.cntExamenFisico = cntExamenFisico;
    }

    public String getCntTratamiento() {
        return cntTratamiento;
    }

    public void setCntTratamiento(String cntTratamiento) {
        this.cntTratamiento = cntTratamiento;
    }

    public Long getCntVersion() {
        return cntVersion;
    }

    public void setCntVersion(Long cntVersion) {
        this.cntVersion = cntVersion;
    }

    public Expediente getCntExpediente() {
        return cntExpediente;
    }

    public void setCntExpediente(Expediente cntExpediente) {
        this.cntExpediente = cntExpediente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cntId != null ? cntId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Control)) {
            return false;
        }
        Control other = (Control) object;
        if ((this.cntId == null && other.cntId != null) || (this.cntId != null && !this.cntId.equals(other.cntId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillaws2.model.Control[ cntId=" + cntId + " ]";
    }

}
