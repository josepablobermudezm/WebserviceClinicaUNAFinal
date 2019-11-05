/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.model;

import cr.ac.una.wsclinicauna.util.LocalDateAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Jose Pablo Bermudez
 */
@XmlRootElement(name = "ControlDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class ControlDto {
    
    
    private Long cntId;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate cntFecha;
    private String cntHora;
    private Double cntPresion;
    private Double cntFrecuenciaCardiaca;
    private Double cntPeso;
    private Double cntTalla;
    private Double cntTemperatura;
    private Double cntImc;
    private String cntAnotacionEnfermeria;
    private String cntRazonConsulta;
    private String cntPlanAtencion;
    private String cntObservaciones;
    private String cntExamenFisico;
    private String cntTratamiento;
    private Long cntVersion;
    private ExpedienteDto cntExpediente;
    
    public ControlDto(){
    }
    
    public ControlDto(Control control) {
        this.cntId = control.getCntId();
        this.cntVersion = control.getCntVersion();
        this.cntFecha = control.getCntFecha().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        if (control.getCntHora()!= null) {
            LocalDateTime localDateTime1 = control.getCntHora().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            this.cntHora = localDateTime1.toLocalTime().toString();
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
        this.cntExpediente = new ExpedienteDto(control.getCntExpediente(),false);
    }

    public Long getCntId() {
        return cntId;
    }

    public void setCntId(Long cntId) {
        this.cntId = cntId;
    }

    public LocalDate getCntFecha() {
        return cntFecha;
    }

    public void setCntFecha(LocalDate cntFecha) {
        this.cntFecha = cntFecha;
    }

    public String getCntHora() {
        return cntHora;
    }

    public void setCntHora(String cntHora) {
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

    public ExpedienteDto getCntExpediente() {
        return cntExpediente;
    }

    public void setCntExpediente(ExpedienteDto cntExpediente) {
        this.cntExpediente = cntExpediente;
    }
    
}
