package cr.ac.una.wsclinicauna.model;

import cr.ac.una.wsclinicauna.model.Antecedente;
import cr.ac.una.wsclinicauna.model.Control;
import cr.ac.una.wsclinicauna.model.Examen;
import cr.ac.una.wsclinicauna.model.Paciente;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-05T23:30:53")
@StaticMetamodel(Expediente.class)
public class Expediente_ { 

    public static volatile ListAttribute<Expediente, Control> controlList;
    public static volatile ListAttribute<Expediente, Examen> examenList;
    public static volatile SingularAttribute<Expediente, Long> expVersion;
    public static volatile SingularAttribute<Expediente, Paciente> expPaciente;
    public static volatile ListAttribute<Expediente, Antecedente> antecedenteList;
    public static volatile SingularAttribute<Expediente, String> expAntecedentePatologicos;
    public static volatile SingularAttribute<Expediente, String> expTratamientos;
    public static volatile SingularAttribute<Expediente, String> expAlergias;
    public static volatile SingularAttribute<Expediente, String> expHospitalizaciones;
    public static volatile SingularAttribute<Expediente, Long> expId;
    public static volatile SingularAttribute<Expediente, String> expOperaciones;

}