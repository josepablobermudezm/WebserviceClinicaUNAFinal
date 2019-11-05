/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Olivares
 */
@XmlRootElement(name = "AntecedenteDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class AntecedenteDto {
    private Long antId;
    private String antEnfermedad;
    private String antParentesco;
    private Long antVersion;
    private ExpedienteDto antExpediente;

    public AntecedenteDto() {
    }
    public AntecedenteDto(Antecedente antecedente) {
        this.antEnfermedad = antecedente.getAntEnfermedad();
        this.antParentesco = antecedente.getAntParentesco();
        this.antExpediente = new ExpedienteDto(antecedente.getAntExpediente(),false);
        //this.antExpediente = new ExpedienteDto(antecedente.getAntExpediente());
        this.antId = antecedente.getAntId();
        this.antVersion = antecedente.getAntVersion();
    }

    public Long getAntId() {
        return antId;
    }

    public void setAntId(Long antId) {
        this.antId = antId;
    }

    public String getAntEnfermedad() {
        return antEnfermedad;
    }

    public void setAntEnfermedad(String antEnfermedad) {
        this.antEnfermedad = antEnfermedad;
    }

    public String getAntParentesco() {
        return antParentesco;
    }

    public void setAntParentesco(String antParentesco) {
        this.antParentesco = antParentesco;
    }

    public Long getAntVersion() {
        return antVersion;
    }

    public void setAntVersion(Long antVersion) {
        this.antVersion = antVersion;
    }

    public ExpedienteDto getAntExpediente() {
        return antExpediente;
    }

    public void setAntExpediente(ExpedienteDto antExpediente) {
        this.antExpediente = antExpediente;
    }
    
    
    
}
