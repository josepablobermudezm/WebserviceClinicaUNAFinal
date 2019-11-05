/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.service;

import cr.ac.una.wsclinicauna.model.Examen;
import cr.ac.una.wsclinicauna.model.ExamenDto;
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
public class ExamenService {
    
    private static final Logger LOG = Logger.getLogger(ExamenService.class.getName());//imprime el error en payara
    @PersistenceContext(unitName = "WsClinicaUNAPU")
    private EntityManager em;
    private EntityTransaction et;
    
    public Respuesta getExamenes() {
        try {
            Query qryExamens = em.createNamedQuery("Examen.findAll", Examen.class);
            List<Examen> examens = qryExamens.getResultList();
            List<ExamenDto> ExamensDto = new ArrayList<>();
            for (Examen Examens1 : examens) {
                ExamensDto.add(new ExamenDto(Examens1));
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Examenes", ExamensDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen Examenes con los criterios ingresados.", "getExamenes NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el Examen.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el Examen.", "getExamenes " + ex.getMessage());
        }
    }
    
    public Respuesta getExamenes(Long ID) {
        try {
            Expediente expediente = em.find(Expediente.class,ID);
            ArrayList <ExamenDto> examenes = new ArrayList();
            
            expediente.getExamenList().stream().forEach((t) -> {
                examenes.add(new ExamenDto(t));
            });
            
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Examenes", examenes);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen Examenes con los criterios ingresados.", "getExamenes NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el Examen.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el Examen.", "getExamenes " + ex.getMessage());
        }
    }
    
    public Respuesta guardarExamen(ExamenDto ExamenDto) {
        try {
            Examen Examen;
            if (ExamenDto.getExmID()!= null && ExamenDto.getExmID()> 0) {
                Examen = em.find(Examen.class, ExamenDto.getExmID());

                if (Examen == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encrontró el Examen a modificar.", "guardarExamen NoResultException");
                }

                Examen.actualizarExamen(ExamenDto);
                Examen = em.merge(Examen);

            } else {
                Examen = new Examen(ExamenDto);
                em.persist(Examen);
            }

            em.flush();

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Examen", new ExamenDto(Examen));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el Examen.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el Examen.", "guardarExamen " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarExamen(Long id) {
        try {
            //Empleado empleado;
            Examen Examen;
            if (id != null && id > 0) {
                Examen = em.find(Examen.class, id);
                if (Examen == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO,"No se encontró el empleado a eliminar.", "EliminarExamen NoResultException");
                }
                em.remove(Examen);
            } else {
                return new Respuesta(false,CodigoRespuesta.ERROR_CLIENTE, "Debe cargar el Examen a eliminar.", "EliminarExamen NoResultException");
            }
            return new Respuesta(true,CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, CodigoRespuesta.ERROR_PERMISOS,"No se puede eliminar el Examen porque tiene relaciones con otros registros.", "EliminarExamen " + ex.getMessage());
            }
            Logger.getLogger(ExamenService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el Examen.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,"Ocurrio un error al eliminar el Examen.", "EliminarExamen " + ex.getMessage());
        }
    }
    
    
}
