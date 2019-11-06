package cr.ac.una.wsclinicauna.model;

import cr.ac.una.wsclinicauna.model.Expediente;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-05T23:30:53")
@StaticMetamodel(Examen.class)
public class Examen_ { 

    public static volatile SingularAttribute<Examen, Long> exmId;
    public static volatile SingularAttribute<Examen, String> exmAnotaciones;
    public static volatile SingularAttribute<Examen, Long> exmVersion;
    public static volatile SingularAttribute<Examen, Expediente> exmExpediente;
    public static volatile SingularAttribute<Examen, String> exmNombreExamen;
    public static volatile SingularAttribute<Examen, Date> exmFecha;

}