package cr.ac.una.wsclinicauna.service;

import cr.ac.una.wsclinicauna.model.AgendaDto;
import cr.ac.una.wsclinicauna.model.Agenda;
import cr.ac.una.wsclinicauna.model.Medico;
import jasper.generadorJasper;
import cr.ac.una.wsclinicauna.util.CodigoRespuesta;
import cr.ac.una.wsclinicauna.util.Respuesta;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Jose Pablo Bermudez
 */
@Stateless
@LocalBean
public class AgendaService {

    private static final Logger LOG = Logger.getLogger(AgendaService.class.getName());//imprime el error en payara
    @PersistenceContext(unitName = "WsClinicaUNAPU")
    private EntityManager em;

    public Respuesta getAgendas() {
        try {
            Query qryAgendas = em.createNamedQuery("Agenda.findAll", Agenda.class);
            List<Agenda> Agendas = qryAgendas.getResultList();
            List<AgendaDto> AgendasDto = new ArrayList<>();
            for (Agenda Agendas1 : Agendas) {
                AgendasDto.add(new AgendaDto(Agendas1,true));
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Agendas", AgendasDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen Agendas con los criterios ingresados.", "getAgendas NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el Agenda.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el Agenda.", "getAgendas " + ex.getMessage());
        }
    }

    public Respuesta guardarAgenda(AgendaDto AgendaDto) {
        try {
            Agenda Agenda;
            if (AgendaDto.getAgeId() != null && AgendaDto.getAgeId() > 0) {
                Agenda = em.find(Agenda.class, AgendaDto.getAgeId());

                if (Agenda == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encrontró el Agenda a modificar.", "guardarAgenda NoResultException");
                }

                Agenda.actualizarAgenda(AgendaDto);
                Agenda = em.merge(Agenda);

            } else {
                Agenda = new Agenda(AgendaDto);
                em.persist(Agenda);
            }

            em.flush();

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Agenda", new AgendaDto(Agenda,true));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el Agenda.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el Agenda.", "guardarAgenda " + ex.getMessage());
        }
    }

    public Respuesta eliminarAgenda(Long id) {
        try {
            //Empleado empleado;
            Agenda Agenda;
            if (id != null && id > 0) {
                Agenda = em.find(Agenda.class, id);
                if (Agenda == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encontró el empleado a eliminar.", "EliminarAgenda NoResultException");
                }
                em.remove(Agenda);
            } else {
                return new Respuesta(false, CodigoRespuesta.ERROR_CLIENTE, "Debe cargar el Agenda a eliminar.", "EliminarAgenda NoResultException");
            }
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, CodigoRespuesta.ERROR_PERMISOS, "No se puede eliminar el Agenda porque tiene relaciones con otros registros.", "EliminarAgenda " + ex.getMessage());
            }
            Logger.getLogger(AgendaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el Agenda.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al eliminar el Agenda.", "EliminarAgenda " + ex.getMessage());
        }
    }

    public Respuesta getAgenda(String fecha, Long Id) {
        try {
            Query qryAgenda = em.createNamedQuery("Agenda.findByAgeFecha", Agenda.class);

            LocalDate localDate1 = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Date date = Date.from(localDate1.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());

            Medico medico = em.find(Medico.class, Id);
            if (medico == null) {
                return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encontró la Agenda a eliminar.", "EliminarAgenda NoResultException");
            }
            qryAgenda.setParameter("ageFecha", date);
            qryAgenda.setParameter("ageMedico", medico);
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Agenda", new AgendaDto(((Agenda) qryAgenda.getSingleResult()),true));
        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existe una agenda con las credenciales ingresadas.", "getAgenda NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar la Agenda.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar la Agenda.", "getAgenda NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar la Agenda.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar la Agenda.", "getAgenda " + ex.getMessage());
        }
    }

    public Respuesta getAgendas(String fechaInicio, String fechaFinal,String folio) {
        generadorJasper generador = new generadorJasper();
        Connection connection = em.unwrap(Connection.class);
        Respuesta resp = generador.generaReporte(fechaInicio, fechaFinal,folio,connection);
        if(resp.getEstado()){
            return pdf();
        }else{
            return resp;
        }
    }

    public Respuesta pdf() {
        /*
            Cargo el pdf con los datos del reporte y lo convierto a bytes para poder serializarlo y mandarlo al Cliente
         */
        try {
            File archivo;
            archivo = new File("ReporteAgenda.pdf");
            System.out.println(archivo.getAbsolutePath());
            File file = new File(archivo.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream input = new BufferedInputStream(fis);
            byte[] salida = new byte[(int) file.length()];
            input.read(salida);
            fis.close();
            input.close();
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "","","reporte", salida);
        } catch (IOException e) {
            return new Respuesta(false,CodigoRespuesta.ERROR_INTERNO,"Error en reporte",e.getMessage());
        }
    }

}
