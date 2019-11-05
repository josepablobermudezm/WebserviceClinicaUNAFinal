/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.service;

import cr.ac.una.wsclinicauna.model.Medico;
import cr.ac.una.wsclinicauna.model.MedicoDto;
import cr.ac.una.wsclinicauna.util.CampoException;
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
public class MedicoService {
    
    private static final Logger LOG = Logger.getLogger(MedicoService.class.getName());//imprime el error en payara
    @PersistenceContext(unitName = "WsClinicaUNAPU")
    private EntityManager em;
    
    public Respuesta getMedicos() {
        try {
            Query qryMedicos = em.createNamedQuery("Medico.findAll", Medico.class);
            List<Medico> Medicos = qryMedicos.getResultList();
            List<MedicoDto> MedicosDto = new ArrayList<>();
            for (Medico Medicos1 : Medicos) {
                MedicosDto.add(new MedicoDto(Medicos1));
            }
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Medicos", MedicosDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen Medicos con los criterios ingresados.", "getMedicos NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el Medico.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el Medico.", "getMedicos " + ex.getMessage());
        }
    }
    
    public Respuesta guardarMedico(MedicoDto MedicoDto) {
        try {
            Medico Medico;
            if (MedicoDto.getID() != null && MedicoDto.getID() > 0) {
                Medico = em.find(Medico.class, MedicoDto.getID());

                if (Medico == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encrontró el Medico a modificar.", "guardarMedico NoResultException");
                }

                Medico.actualizarMedico(MedicoDto);
                Medico = em.merge(Medico);

            } else {
                Medico = new Medico(MedicoDto);
                em.persist(Medico);
            }
            em.flush();
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Medico", new MedicoDto(Medico));
         } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el Medico.", ex);
            if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class){
                SQLIntegrityConstraintViolationException sqle = new SQLIntegrityConstraintViolationException(ex.getCause().getCause());
                return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el Medico. Ya existe un Medico con el mismo campo "
                        + CampoException.getCampo(sqle.getMessage(), "CLINICAUNA", "CLN",3)
                        , "guardarMedico " + sqle.getMessage());
            }
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el Medico.", "guardarMedico " + ex.getMessage());
        }
    }

    public Respuesta eliminarMedico(Long id) {
        try {
            Medico Medico;
            if (id != null && id > 0) {
                Medico = em.find(Medico.class, id);
                if (Medico == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO,"No se encontró el Medico a eliminar.", "EliminarMedico NoResultException");
                }
                em.remove(Medico);
            } else {
                return new Respuesta(false, CodigoRespuesta.ERROR_CLIENTE, "Debe cargar el Medico a eliminar.", "EliminarMedico NoResultException");
            }
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, CodigoRespuesta.ERROR_PERMISOS,"No se puede eliminar el Medico porque tiene relaciones con otros registros.", "EliminarMedico " + ex.getMessage());
            }
            Logger.getLogger(MedicoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el Medico.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO,"Ocurrio un error al eliminar el Medico.", "EliminarMedico " + ex.getMessage());
        }
    }
    
    public Respuesta getMedicos(String cod, String carne, String folio){
         try {
            Query qryEmpleado = em.createNamedQuery("Medico.findbyCodigoCarneFolio",  Medico.class);
            qryEmpleado.setParameter("MedCodigo", cod);
            qryEmpleado.setParameter("MedCarne", carne);
            qryEmpleado.setParameter("MedFolio", folio);
            
            List<Medico> medicos = qryEmpleado.getResultList();
            List<MedicoDto> medicosDto = new ArrayList<>();
            for (Medico med : medicos) {
                medicosDto.add(new MedicoDto(med)); 
            }
            
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Medicos", medicosDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen medicos con los criterios ingresados.", "getMedicos NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el medico.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el medico.", "getMedicos" + ex.getMessage());
        }
    }
}
