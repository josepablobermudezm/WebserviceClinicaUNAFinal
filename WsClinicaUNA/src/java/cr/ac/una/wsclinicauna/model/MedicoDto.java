package cr.ac.una.wsclinicauna.model;


import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Olivares
 */
@XmlRootElement(name = "MedicoDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class MedicoDto {

    private Long ID;
    private String Codigo;
    private String Folio;
    private String carne;
    private String Estado;
    private Integer Espacios;
    private String InicioJornada;
    private String FinJornada;
    private UsuarioDto us;
    private Long medVersion;

    public MedicoDto() {
    }

    public MedicoDto(Medico medico) {
        this.ID = medico.getMedId();
        this.Codigo = medico.getMedCodigo();
        this.Folio = medico.getMedFolio();
        this.carne = medico.getMedCarne();
        this.Estado = medico.getMedEstado();
        this.Espacios = medico.getMedEspaciosporhora();
        if (medico.getMedIniciojornada() != null && medico.getMedFinjornada() != null) {
            LocalDateTime localDateTime = medico.getMedIniciojornada().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            this.InicioJornada = localDateTime.toLocalTime().toString();

            LocalDateTime localDateTime2 = medico.getMedFinjornada().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            this.FinJornada = localDateTime2.toLocalTime().toString();
        }

        this.us = new UsuarioDto(medico.getMedUsuario());
        this.medVersion = medico.getMedVersion();
    }

    public Long getMedVersion() {
        return medVersion;
    }

    public void setMedVersion(Long medVersion) {
        this.medVersion = medVersion;
    }

    public UsuarioDto getUs() {
        return us;
    }

    public void setUs(UsuarioDto us) {
        this.us = us;
    }

    public Integer getEspacios() {
        return Espacios;
    }

    public void setEspacios(Integer Espacios) {
        this.Espacios = Espacios;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String Folio) {
        this.Folio = Folio;
    }

    public String getCarne() {
        return carne;
    }

    public void setCarne(String carne) {
        this.carne = carne;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getInicioJornada() {
        return InicioJornada;
    }

    public void setInicioJornada(String InicioJornada) {
        this.InicioJornada = InicioJornada;
    }

    public String getFinJornada() {
        return FinJornada;
    }

    public void setFinJornada(String FinJornada) {
        this.FinJornada = FinJornada;
    }

    @Override
    public String toString() {
        return "MedicoDto{" + "ID=" + ID + ", Codigo=" + Codigo + ", Folio=" + Folio + ", carne=" + carne + ", Estado=" + Estado + ", Espacios=" + Espacios + ", InicioJornada=" + InicioJornada + ", FinJornada=" + FinJornada + ", us=" + us + ", medVersion=" + medVersion + '}';
    }
    
}
