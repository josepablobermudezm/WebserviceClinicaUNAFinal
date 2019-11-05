package cr.ac.una.wsclinicauna.model;

import cr.ac.una.wsclinicauna.model.Agenda;
import cr.ac.una.wsclinicauna.model.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-04T16:45:32")
@StaticMetamodel(Medico.class)
public class Medico_ { 

    public static volatile SingularAttribute<Medico, String> medEstado;
    public static volatile SingularAttribute<Medico, Long> medVersion;
    public static volatile SingularAttribute<Medico, String> medFolio;
    public static volatile SingularAttribute<Medico, String> medCarne;
    public static volatile SingularAttribute<Medico, Integer> medEspaciosporhora;
    public static volatile SingularAttribute<Medico, Long> medId;
    public static volatile SingularAttribute<Medico, Usuario> medUsuario;
    public static volatile ListAttribute<Medico, Agenda> agendaList;
    public static volatile SingularAttribute<Medico, Date> medFinjornada;
    public static volatile SingularAttribute<Medico, String> medCodigo;
    public static volatile SingularAttribute<Medico, Date> medIniciojornada;

}