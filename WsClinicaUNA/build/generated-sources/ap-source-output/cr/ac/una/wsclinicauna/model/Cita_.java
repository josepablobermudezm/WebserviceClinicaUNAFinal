package cr.ac.una.wsclinicauna.model;

import cr.ac.una.wsclinicauna.model.Espacio;
import cr.ac.una.wsclinicauna.model.Paciente;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-05T23:30:53")
@StaticMetamodel(Cita.class)
public class Cita_ { 

    public static volatile SingularAttribute<Cita, Long> ctId;
    public static volatile SingularAttribute<Cita, String> ctEstado;
    public static volatile SingularAttribute<Cita, Paciente> ctPaciente;
    public static volatile ListAttribute<Cita, Espacio> espacioList;
    public static volatile SingularAttribute<Cita, String> ctCorreo;
    public static volatile SingularAttribute<Cita, String> ctCorreoenviado;
    public static volatile SingularAttribute<Cita, Long> ctVersion;
    public static volatile SingularAttribute<Cita, String> ctTelefono;
    public static volatile SingularAttribute<Cita, String> ctMotivo;

}