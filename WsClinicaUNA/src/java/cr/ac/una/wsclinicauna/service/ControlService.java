/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.service;

import cr.ac.una.wsclinicauna.model.Control;
import cr.ac.una.wsclinicauna.model.ControlDto;
import cr.ac.una.wsclinicauna.model.Expediente;
import cr.ac.una.wsclinicauna.util.CodigoRespuesta;
import cr.ac.una.wsclinicauna.util.Respuesta;
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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jose Pablo Bermudez
 */
@Stateless
@LocalBean
public class ControlService {
    private static final Logger LOG = Logger.getLogger(ControlService.class.getName());//imprime el error en payara
    @PersistenceContext(unitName = "WsClinicaUNAPU")
    private EntityManager em;
    
    public Respuesta getControles() {
        try {
            Query qryControles = em.createNamedQuery("Control.findAll", Control.class);
            List<Control> Controls = qryControles.getResultList();
            List<ControlDto> ControlesDto = new ArrayList<>();
            for (Control Controls1 : Controls) {
                ControlesDto.add(new ControlDto(Controls1));
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "controles", ControlesDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen Controls con los criterios ingresados.", "getControls NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el Control.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el Control.", "getControls " + ex.getMessage());
        }
    }
    
    public Respuesta guardarControl(ControlDto ControlDto) {
        try {
            Control Control;
            if (ControlDto.getCntId()!= null && ControlDto.getCntId()> 0) {
                Control = em.find(Control.class, ControlDto.getCntId());

                if (Control == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encrontró el Control a modificar.", "guardarControl NoResultException");
                }

                Control.actualizarControl(ControlDto);
                Control = em.merge(Control);

            } else {
                Control = new Control(ControlDto);
                em.persist(Control);
            }

            em.flush();

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Control", new ControlDto(Control));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el Control.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el Control.", "guardarControl " + ex.getMessage());
        }
    }
    public Respuesta eliminarControl(Long id) {
        try {
            //Empleado empleado;
            Control Control;
            if (id != null && id > 0) {
                Control = em.find(Control.class, id);
                if (Control == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO,"No se encontró el Control a eliminar.", "EliminarControl NoResultException");
                }
                em.remove(Control);
            } else {
                return new Respuesta(false,CodigoRespuesta.ERROR_CLIENTE, "Debe cargar el Control a eliminar.", "EliminarControl NoResultException");
            }
            return new Respuesta(true,CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, CodigoRespuesta.ERROR_PERMISOS,"No se puede eliminar el Control porque tiene relaciones con otros registros.", "EliminarControl " + ex.getMessage());
            }
            Logger.getLogger(ControlService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el .", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,"Ocurrio un error al eliminar el Control.", "EliminarControl " + ex.getMessage());
        }
    }

    public Respuesta getControles(Long ID) {
        try {
            Expediente exp = em.find(Expediente.class, ID);
            ArrayList <ControlDto> controles = new ArrayList<>();
            exp.getControlList().stream().forEach((t) -> {
                controles.add(new ControlDto(t));
            });
            

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Controles", controles);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen Controls con los criterios ingresados.", "getControls NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el Control.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el Control.", "getControls " + ex.getMessage());
        }
    }
}
