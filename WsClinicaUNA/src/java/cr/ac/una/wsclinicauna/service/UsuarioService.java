/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.service;

import cr.ac.una.wsclinicauna.model.Usuario;
import cr.ac.una.wsclinicauna.model.UsuarioDto;
import cr.ac.una.wsclinicauna.util.CampoException;
import cr.ac.una.wsclinicauna.util.CodigoRespuesta;
import cr.ac.una.wsclinicauna.util.Respuesta;
import cr.ac.una.wsclinicauna.util.generadorContrasennas;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Olivares
 */
@Stateless
@LocalBean
public class UsuarioService {

    private static final Logger LOG = Logger.getLogger(UsuarioService.class.getName());
    @PersistenceContext(unitName = "WsClinicaUNAPU")
    private EntityManager em;

    public Respuesta validarUsuario(String usuario, String clave) {
        try {
            Query qryActividad = em.createNamedQuery("Usuario.findByUsuClave", Usuario.class);
            qryActividad.setParameter("usUsuario", usuario);
            qryActividad.setParameter("usClave", clave);
            qryActividad.setParameter("usClaveTemp", clave);

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Usuario", new UsuarioDto((Usuario) qryActividad.getSingleResult()));

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existe un usuario con las credenciales ingresadas.", "validarUsuario NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el usuario.", "validarUsuario NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el usuario.", "validarUsuario " + ex.getMessage());
        }
    }

    public Respuesta getUsuarios() {
        try {
            Query qryUsuario = em.createNamedQuery("Usuario.findAll", Usuario.class);
            List<Usuario> usuario = qryUsuario.getResultList();
            List<UsuarioDto> usuariosDto = new ArrayList<>();
            for (Usuario usuario1 : usuario) {
                usuariosDto.add(new UsuarioDto(usuario1));
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Usuarios", usuariosDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen usuarios con los criterios ingresados.", "getUsuarios NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el usuario.", "getUsuario " + ex.getMessage());
        }
    }

    public Respuesta guardarUsuario(UsuarioDto UsuarioDto) {
        try {
            Usuario Usuario;
            if (UsuarioDto.getID() != null && UsuarioDto.getID() > 0) {
                Usuario = em.find(Usuario.class, UsuarioDto.getID());

                if (Usuario == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encontró el Usuario a modificar.", "guardarUsuario NoResultException");
                }

                Usuario.actualizarUsuario(UsuarioDto);
                Usuario = em.merge(Usuario);

            } else {
                Usuario = new Usuario(UsuarioDto);
                //Crea una contrasena temporal para luego activar el usuario
                Usuario.setUsContrasenatemp(generadorContrasennas.getInstance().getPassword());
                em.persist(Usuario);
            }

            em.flush();

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Usuario", new UsuarioDto(Usuario));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el Usuario.", ex);
            if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                SQLIntegrityConstraintViolationException sqle = new SQLIntegrityConstraintViolationException(ex.getCause().getCause());
                return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el Usuario. Ya existe un Usuario con el mismo campo "
                        + CampoException.getCampo(sqle.getMessage(), "CLINICAUNA", "CLN", 2),
                        "guardarUsuario " + sqle.getMessage());
            }
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el Usuario.", "guardarUsuario " + ex.getMessage());
        }
    }

    public Respuesta eliminarUsuario(Long id) {
        try {
            //Empleado empleado;
            Usuario usuario;
            if (id != null && id > 0) {
                usuario = em.find(Usuario.class, id);
                if (usuario == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encontró el usuario a eliminar.", "EliminarUsuario NoResultException");
                }
                em.remove(usuario);
            } else {
                return new Respuesta(false, CodigoRespuesta.ERROR_CLIENTE, "Debe cargar el usuario a eliminar.", "EliminarUsuario NoResultException");
            }
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, CodigoRespuesta.ERROR_PERMISOS, "No se puede eliminar el usuario porque tiene relaciones con otros registros.", "EliminarUsuario " + ex.getMessage());
            }
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al eliminar el usuario.", "EliminarUsuario " + ex.getMessage());
        }
    }

    public Respuesta activarUsuario(String codigo) {
        try {
            Query qryUsuario = em.createNamedQuery("Usuario.findByUsContrasenatemp", Usuario.class);
            qryUsuario.setParameter("usContrasenatemp", codigo);
            Usuario usuario = (Usuario) qryUsuario.getSingleResult();

            usuario.setUsEstado("A");
            usuario.setUsContrasenatemp(null);
            usuario = em.merge(usuario);
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Usuario", new UsuarioDto(usuario));
        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existe un usuario con las credenciales ingresadas.", "validarUsuario NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el usuario.", "validarUsuario NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el usuario.", "validarUsuario " + ex.getMessage());
        }
    }

    public Respuesta recuperarContrasenna(String correo) {
        try {
            Query qryUsuario = em.createNamedQuery("Usuario.findByUsCorreo", Usuario.class);
            qryUsuario.setParameter("usCorreo", correo);
            Usuario usuario = (Usuario) qryUsuario.getSingleResult();
            if (!usuario.getUsEstado().equals("I")) {
                usuario.setUsContrasenatemp(generadorContrasennas.getInstance().getPassword());
                usuario = em.merge(usuario);
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Usuario", new UsuarioDto(usuario));
        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existe un usuario con las credenciales ingresadas.", "validarUsuario NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el usuario.", "validarUsuario NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el usuario.", "validarUsuario " + ex.getMessage());
        }
    }

    public Respuesta getUsuarios(String cedula, String nombre, String pApellido) {
        try {
            Query qryEmpleado = em.createNamedQuery("Usuario.findByUsNombreCedulaApellido", Usuario.class);
            qryEmpleado.setParameter("UsCedula", cedula);
            qryEmpleado.setParameter("UsNombre", nombre);
            qryEmpleado.setParameter("UsPapellido", pApellido);

            List<Usuario> usuarios = qryEmpleado.getResultList();
            List<UsuarioDto> usuariosDto = new ArrayList<>();
            for (Usuario us : usuarios) {
                usuariosDto.add(new UsuarioDto(us));
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Usuarios", usuariosDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen usuarios con los criterios ingresados.", "getUsuarios NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el usuario.", "getUsuarios " + ex.getMessage());
        }
    }
}
