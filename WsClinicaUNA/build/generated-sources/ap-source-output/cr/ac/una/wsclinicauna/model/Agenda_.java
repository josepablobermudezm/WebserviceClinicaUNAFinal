package cr.ac.una.wsclinicauna.model;

import cr.ac.una.wsclinicauna.model.Espacio;
import cr.ac.una.wsclinicauna.model.Medico;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-04T16:45:32")
@StaticMetamodel(Agenda.class)
public class Agenda_ { 

    public static volatile SingularAttribute<Agenda, Medico> ageMedico;
    public static volatile SingularAttribute<Agenda, Date> ageFecha;
    public static volatile SingularAttribute<Agenda, Long> ageVersion;
    public static volatile ListAttribute<Agenda, Espacio> espacioList;
    public static volatile SingularAttribute<Agenda, Long> ageId;

}