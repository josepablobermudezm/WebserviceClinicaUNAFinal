/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.service;

import cr.ac.una.wsclinicauna.model.Espacio;
import cr.ac.una.wsclinicauna.model.EspacioDto;
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
public class EspacioService {
    
    private static final Logger LOG = Logger.getLogger(EspacioService.class.getName());//imprime el error en payara
    @PersistenceContext(unitName = "WsClinicaUNAPU")
    private EntityManager em;
    private EntityTransaction et;
    
    public Respuesta getEspacios() {
        try {
            Query qryEspacios = em.createNamedQuery("Espacio.findAll", Espacio.class);
            List<Espacio> Espacios = qryEspacios.getResultList();
            List<EspacioDto> EspaciosDto = new ArrayList<>();
            for (Espacio Espacios1 : Espacios) {
                EspaciosDto.add(new EspacioDto(Espacios1));
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Espacios", EspaciosDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen Espacios con los criterios ingresados.", "getEspacios NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el Espacio.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el Espacio.", "getEspacios " + ex.getMessage());
        }
    }
    
    public Respuesta guardarEspacio(EspacioDto EspacioDto) {
        try {
            Espacio Espacio;
            if (EspacioDto.getEspId()!= null && EspacioDto.getEspId()> 0) {
                Espacio = em.find(Espacio.class, EspacioDto.getEspId());
                if (Espacio == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encrontró el Espacio a modificar.", "guardarEspacio NoResultException");
                }
                Espacio.actualizarEspacio(EspacioDto);
                Espacio = em.merge(Espacio);
            } else {
                Espacio = new Espacio(EspacioDto);
                em.persist(Espacio);
            }
            em.flush();
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Espacio", new EspacioDto(Espacio));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el Espacio.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el Espacio.", "guardarEspacio " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarEspacio(Long id) {
        try {
            //Empleado empleado;
            Espacio Espacio;
            if (id != null && id > 0) {
                Espacio = em.find(Espacio.class, id);
                if (Espacio == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO,"No se encontró el empleado a eliminar.", "EliminarEspacio NoResultException");
                }
                em.remove(Espacio);
            } else {
                return new Respuesta(false,CodigoRespuesta.ERROR_CLIENTE, "Debe cargar el Espacio a eliminar.", "EliminarEspacio NoResultException");
            }
            return new Respuesta(true,CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, CodigoRespuesta.ERROR_PERMISOS,"No se puede eliminar el Espacio porque tiene relaciones con otros registros.", "EliminarEspacio " + ex.getMessage());
            }
            Logger.getLogger(EspacioService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el Espacio.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,"Ocurrio un error al eliminar el Espacio.", "EliminarEspacio " + ex.getMessage());
        }
    }
}