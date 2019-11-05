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
@XmlRootElement(name = "UsuarioDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioDto {
    private Long ID;
    private String nombre,pApellido,estado,sApellido, cedula, correo,nombreUsuario ,contrasennaTemp, contrasenna, tipoUsuario,idioma;
    private Long usVersion;
    public UsuarioDto() {     
    }

    public UsuarioDto(Usuario usuario) {
        this.ID = usuario.getUsId();
        this.cedula = usuario.getUsCedula();
        this.nombreUsuario = usuario.getUsNombreUsuario();
        this.contrasenna = usuario.getUsContrasena();
        this.contrasennaTemp = usuario.getUsContrasenatemp();
        this.correo = usuario.getUsCorreo();
        this.estado = usuario.getUsEstado();
        this.idioma = usuario.getUsIdioma();
        this.nombre = usuario.getUsNombre();
        this.pApellido = usuario.getUsPapellido();
        this.sApellido = usuario.getUsSapellido();
        this.tipoUsuario = usuario.getUsTipousuario();
        this.usVersion = usuario.getUsVersion();
        
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getpApellido() {
        return pApellido;
    }

    public void setpApellido(String pApellido) {
        this.pApellido = pApellido;
    }

    public String getsApellido() {
        return sApellido;
    }

    public void setsApellido(String sApellido) {
        this.sApellido = sApellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasennaTemp() {
        return contrasennaTemp;
    }

    public void setContrasennaTemp(String contrasennaTemp) {
        this.contrasennaTemp = contrasennaTemp;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Long getUsVersion() {
        return usVersion;
    }

    public void setUsVersion(Long usVersion) {
        this.usVersion = usVersion;
    }

    
}
