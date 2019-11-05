package cr.ac.una.wsclinicauna.model;

import cr.ac.una.wsclinicauna.model.Cita;
import cr.ac.una.wsclinicauna.model.Expediente;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-04T16:45:32")
@StaticMetamodel(Paciente.class)
public class Paciente_ { 

    public static volatile SingularAttribute<Paciente, String> pacGenero;
    public static volatile SingularAttribute<Paciente, String> pacCedula;
    public static volatile SingularAttribute<Paciente, Long> pacVersion;
    public static volatile SingularAttribute<Paciente, String> pacCorreo;
    public static volatile SingularAttribute<Paciente, Date> pacFechanacimiento;
    public static volatile SingularAttribute<Paciente, Long> pacId;
    public static volatile ListAttribute<Paciente, Expediente> expedienteList;
    public static volatile ListAttribute<Paciente, Cita> citaList;
    public static volatile SingularAttribute<Paciente, String> pacNombre;
    public static volatile SingularAttribute<Paciente, String> pacPapellido;
    public static volatile SingularAttribute<Paciente, String> pacSapellido;

}