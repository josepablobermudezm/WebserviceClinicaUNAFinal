/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.model;

import cr.ac.una.wsclinicauna.util.LocalDateAdapter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Jose Pablo Bermudez
 */
@XmlRootElement(name = "AgendaDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class AgendaDto {

    private Long ageId;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate ageFecha;
    private Long ageVersion;
    private MedicoDto ageMedico;
    private List<EspacioDto> espacioList;

    public AgendaDto() {
    }

    public AgendaDto(Agenda agenda, boolean nuevo) {
        this.ageId = agenda.getAgeId();
        this.ageMedico = new MedicoDto(agenda.getAgeMedico());
        this.ageVersion = agenda.getAgeVersion();
        this.ageFecha = agenda.getAgeFecha().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        this.espacioList = new ArrayList();
        if (nuevo) {
            if (agenda.getEspacioList() != null && !agenda.getEspacioList().isEmpty()) {
                agenda.getEspacioList().stream().forEach((espacio) -> {
                    this.espacioList.add(new EspacioDto(espacio));
                });
            }
        }

    }

    public Long getAgeId() {
        return ageId;
    }

    public void setAgeId(Long ageId) {
        this.ageId = ageId;
    }

    public LocalDate getAgeFecha() {
        return ageFecha;
    }

    public void setAgeFecha(LocalDate ageFecha) {
        this.ageFecha = ageFecha;
    }

    public Long getAgeVersion() {
        return ageVersion;
    }

    public void setAgeVersion(Long ageVersion) {
        this.ageVersion = ageVersion;
    }

    public MedicoDto getAgeMedico() {
        return ageMedico;
    }

    public void setAgeMedico(MedicoDto ageMedico) {
        this.ageMedico = ageMedico;
    }

    public List<EspacioDto> getEspacioList() {
        return espacioList;
    }

    public void setEspacioList(List<EspacioDto> espacioList) {
        this.espacioList = espacioList;
    }
}
