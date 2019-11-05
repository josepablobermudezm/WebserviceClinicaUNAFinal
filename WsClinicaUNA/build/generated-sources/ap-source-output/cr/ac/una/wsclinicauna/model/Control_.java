package cr.ac.una.wsclinicauna.model;

import cr.ac.una.wsclinicauna.model.Expediente;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-04T16:45:32")
@StaticMetamodel(Control.class)
public class Control_ { 

    public static volatile SingularAttribute<Control, Double> cntTemperatura;
    public static volatile SingularAttribute<Control, String> cntObservaciones;
    public static volatile SingularAttribute<Control, String> cntRazonConsulta;
    public static volatile SingularAttribute<Control, Long> cntId;
    public static volatile SingularAttribute<Control, Double> cntImc;
    public static volatile SingularAttribute<Control, Expediente> cntExpediente;
    public static volatile SingularAttribute<Control, String> cntPlanAtencion;
    public static volatile SingularAttribute<Control, Date> cntFecha;
    public static volatile SingularAttribute<Control, Double> cntTalla;
    public static volatile SingularAttribute<Control, Double> cntFrecuenciaCardiaca;
    public static volatile SingularAttribute<Control, Long> cntVersion;
    public static volatile SingularAttribute<Control, String> cntExamenFisico;
    public static volatile SingularAttribute<Control, String> cntAnotacionEnfermeria;
    public static volatile SingularAttribute<Control, Double> cntPresion;
    public static volatile SingularAttribute<Control, Date> cntHora;
    public static volatile SingularAttribute<Control, Double> cntPeso;
    public static volatile SingularAttribute<Control, String> cntTratamiento;

}