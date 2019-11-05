package cr.ac.una.wsclinicauna.model;

import cr.ac.una.wsclinicauna.model.Medico;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-04T16:45:32")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> usPapellido;
    public static volatile SingularAttribute<Usuario, Long> usVersion;
    public static volatile SingularAttribute<Usuario, String> usCorreo;
    public static volatile SingularAttribute<Usuario, String> usIdioma;
    public static volatile SingularAttribute<Usuario, String> usSapellido;
    public static volatile SingularAttribute<Usuario, Long> usId;
    public static volatile SingularAttribute<Usuario, String> usCedula;
    public static volatile SingularAttribute<Usuario, String> usNombreUsuario;
    public static volatile SingularAttribute<Usuario, String> usContrasenatemp;
    public static volatile SingularAttribute<Usuario, String> usNombre;
    public static volatile SingularAttribute<Usuario, String> usEstado;
    public static volatile SingularAttribute<Usuario, String> usTipousuario;
    public static volatile SingularAttribute<Usuario, String> usContrasena;
    public static volatile ListAttribute<Usuario, Medico> medicoList;

}