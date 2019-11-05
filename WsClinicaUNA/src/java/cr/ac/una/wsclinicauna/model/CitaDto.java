/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jose Pablo Bermudez
 */
@XmlRootElement(name = "CitaDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class CitaDto {
    
    private Long CtID;
    private Long CtVersion;
    private PacienteDto paciente;
    private String motivo;
    private String estado;
    private String telefono;
    private String correo;
    private String correoEnviado;
    private List<EspacioDto> espacios;

    public CitaDto(){
    }
    
    public CitaDto(Cita cita) {
        this.CtID = cita.getCtId();
        this.CtVersion = cita.getCtVersion();
        this.paciente = new PacienteDto(cita.getCtPaciente());
        this.motivo = cita.getCtMotivo();
        this.estado = cita.getCtEstado();
        this.telefono = cita.getCtTelefono();
        this.correo = cita.getCtCorreo();
        this.correoEnviado = cita.getCtCorreoenviado();
        this.espacios = new ArrayList();
        /*if(cita.getEspacioList()!=null && !cita.getEspacioList().isEmpty()){
           cita.getEspacioList().stream().forEach((espacio) -> {
                this.espacios.add(new EspacioDto(espacio));
           });
        }*/
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public Long getID() {
        return CtID;
    }

    public void setID(Long CtID) {
        this.CtID = CtID;
    }

    public Long getCtVersion() {
        return CtVersion;
    }

    public void setCtVersion(Long CtVersion) {
        this.CtVersion = CtVersion;
    }

    public PacienteDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDto paciente) {
        this.paciente = paciente;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCorreoEnviado() {
        return correoEnviado;
    }

    public void setCorreoEnviado(String correoEnviado) {
        this.correoEnviado = correoEnviado;
    }

    public List<EspacioDto> getEspacios() {
        return espacios;
    }

    public void setEspacios(List<EspacioDto> espacios) {
        this.espacios = espacios;
    }
    
    
}
