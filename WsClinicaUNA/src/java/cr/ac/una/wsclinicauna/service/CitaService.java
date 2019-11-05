/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.service;

import cr.ac.una.wsclinicauna.model.Cita;
import cr.ac.una.wsclinicauna.model.CitaDto;
import cr.ac.una.wsclinicauna.model.Cita;
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
public class CitaService {
    private static final Logger LOG = Logger.getLogger(CitaService.class.getName());//imprime el error en payara
    @PersistenceContext(unitName = "WsClinicaUNAPU")
    private EntityManager em;
    private EntityTransaction et;
    
    public Respuesta getCitas() {
        try {
            Query qryCitas = em.createNamedQuery("Cita.findAll", Cita.class);
            List<Cita> Citas = qryCitas.getResultList();
            List<CitaDto> CitasDto = new ArrayList<>();
            for (Cita Citas1 : Citas) {
                CitasDto.add(new CitaDto(Citas1));
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Citas", CitasDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen Citas con los criterios ingresados.", "getCitas NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el Cita.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el Cita.", "getCitas " + ex.getMessage());
        }
    }
    
    public Respuesta guardarCita(CitaDto CitaDto) {
        try {
            Cita Cita;
            if (CitaDto.getID() != null && CitaDto.getID() > 0) {
                Cita = em.find(Cita.class, CitaDto.getID());

                if (Cita == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encrontró el Cita a modificar.", "guardarCita NoResultException");
                }

                Cita.actualizarCita(CitaDto);
                Cita = em.merge(Cita);

            } else {
                Cita = new Cita(CitaDto);
                em.persist(Cita);
            }

            em.flush();

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Cita", new CitaDto(Cita));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el Cita.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el Cita.", "guardarCita " + ex.getMessage());
        }
    }
    public Respuesta eliminarCita(Long id) {
        try {
            //Empleado empleado;
            Cita Cita;
            if (id != null && id > 0) {
                Cita = em.find(Cita.class, id);
                if (Cita == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO,"No se encontró el empleado a eliminar.", "EliminarCita NoResultException");
                }
                em.remove(Cita);
            } else {
                return new Respuesta(false,CodigoRespuesta.ERROR_CLIENTE, "Debe cargar el Cita a eliminar.", "EliminarCita NoResultException");
            }
            return new Respuesta(true,CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, CodigoRespuesta.ERROR_PERMISOS,"No se puede eliminar el Cita porque tiene relaciones con otros registros.", "EliminarCita " + ex.getMessage());
            }
            Logger.getLogger(CitaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el Cita.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,"Ocurrio un error al eliminar el Cita.", "EliminarCita " + ex.getMessage());
        }
    }
}
