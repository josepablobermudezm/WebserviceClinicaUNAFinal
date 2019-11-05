/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.service;

import cr.ac.una.wsclinicauna.model.Expediente;
import cr.ac.una.wsclinicauna.model.ExpedienteDto;
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
public class ExpedienteService {
    
    private static final Logger LOG = Logger.getLogger(ExpedienteService.class.getName());//imprime el error en payara
    @PersistenceContext(unitName = "WsClinicaUNAPU")
    private EntityManager em;
    
    public Respuesta getExpedientes() {
        try {
            Query qryExpedientes = em.createNamedQuery("Expediente.findAll", Expediente.class);
            List<Expediente> Expedientes = qryExpedientes.getResultList();
            List<ExpedienteDto> ExpedientesDto = new ArrayList<>();
            
            for (Expediente expediente : Expedientes) {
                ExpedientesDto.add(new ExpedienteDto(expediente,true));
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Expedientes", ExpedientesDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen Expedientes con los criterios ingresados.", "getExpedientes NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el Expediente.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el Expediente.", "getExpedientes " + ex.getMessage());
        }
    }
    
    public Respuesta guardarExpediente(ExpedienteDto ExpedienteDto) {
        try {
            Expediente Expediente;
            if (ExpedienteDto.getExpID()!= null && ExpedienteDto.getExpID() > 0) {
                Expediente = em.find(Expediente.class, ExpedienteDto.getExpID());

                if (Expediente == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encontró el Expediente a modificar.", "guardarExpediente NoResultException");
                }

                Expediente.actualizarExpediente(ExpedienteDto);
                Expediente = em.merge(Expediente);

            } else {
                Expediente = new Expediente(ExpedienteDto);
                em.persist(Expediente);
            }

            em.flush();

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Expediente", new ExpedienteDto(Expediente,true));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el Expediente.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el Expediente.", "guardarExpediente " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarExpediente(Long id) {
        try {
            //Empleado empleado;
            Expediente Expediente;
            if (id != null && id > 0) {
                Expediente = em.find(Expediente.class, id);
                if (Expediente == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO,"No se encontró el empleado a eliminar.", "EliminarExpediente NoResultException");
                }
                em.remove(Expediente);
            } else {
                return new Respuesta(false,CodigoRespuesta.ERROR_CLIENTE, "Debe cargar el Expediente a eliminar.", "EliminarExpediente NoResultException");
            }
            return new Respuesta(true,CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, CodigoRespuesta.ERROR_PERMISOS,"No se puede eliminar el Expediente porque tiene relaciones con otros registros.", "EliminarExpediente " + ex.getMessage());
            }
            Logger.getLogger(ExpedienteService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el Expediente.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,"Ocurrio un error al eliminar el Expediente.", "EliminarExpediente " + ex.getMessage());
        }
    }
}
