package cr.ac.una.wsclinicauna.model;

import cr.ac.una.wsclinicauna.model.Agenda;
import cr.ac.una.wsclinicauna.model.Cita;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-05T23:30:53")
@StaticMetamodel(Espacio.class)
public class Espacio_ { 

    public static volatile SingularAttribute<Espacio, Long> espId;
    public static volatile SingularAttribute<Espacio, Cita> espCita;
    public static volatile SingularAttribute<Espacio, Agenda> espAgenda;
    public static volatile SingularAttribute<Espacio, Date> espHoraInicio;
    public static volatile SingularAttribute<Espacio, Date> espHoraFin;
    public static volatile SingularAttribute<Espacio, Long> espVersion;

}