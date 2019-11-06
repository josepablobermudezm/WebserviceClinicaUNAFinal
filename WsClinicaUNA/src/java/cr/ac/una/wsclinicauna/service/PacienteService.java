/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.service;

import cr.ac.una.wsclinicauna.model.Paciente;
import cr.ac.una.wsclinicauna.model.PacienteDto;
import cr.ac.una.wsclinicauna.model.Paciente;
import cr.ac.una.wsclinicauna.model.PacienteDto;
import cr.ac.una.wsclinicauna.model.Paciente;
import cr.ac.una.wsclinicauna.util.CampoException;
import cr.ac.una.wsclinicauna.util.CodigoRespuesta;
import cr.ac.una.wsclinicauna.util.Respuesta;
import jasper.generadorJasper;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
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
 * @author Jose Pablo Bermudez
 */
@Stateless
@LocalBean
public class PacienteService {

    private static final Logger LOG = Logger.getLogger(PacienteService.class.getName());//imprime el error en payara
    @PersistenceContext(unitName = "WsClinicaUNAPU")
    private EntityManager em;
    private EntityTransaction et;

    public Respuesta getPacientes() {
        try {
            Query qrypacientes = em.createNamedQuery("Paciente.findAll", Paciente.class);
            List<Paciente> pacientes = qrypacientes.getResultList();
            List<PacienteDto> pacientesDto = new ArrayList<>();
            for (Paciente pacientes1 : pacientes) {
                pacientesDto.add(new PacienteDto(pacientes1));
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Pacientes", pacientesDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen pacientes con los criterios ingresados.", "getPacientes NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el paciente.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el paciente.", "getPacientes " + ex.getMessage());
        }
    }

    public Respuesta guardarPaciente(PacienteDto PacienteDto) {
        try {
            Paciente Paciente;
            if (PacienteDto.getID() != null && PacienteDto.getID() > 0) {
                Paciente = em.find(Paciente.class, PacienteDto.getID());

                if (Paciente == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encrontró el Paciente a modificar.", "guardarPaciente NoResultException");
                }

                Paciente.actualizarPaciente(PacienteDto);
                Paciente = em.merge(Paciente);

            } else {
                Paciente = new Paciente(PacienteDto);
                em.persist(Paciente);
            }

            em.flush();

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Paciente", new PacienteDto(Paciente));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el Paciente.", ex);
            if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {

                SQLIntegrityConstraintViolationException sqle = new SQLIntegrityConstraintViolationException(ex.getCause().getCause());
                return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el Paciente. Ya existe un Paciente con el mismo campo "
                        + CampoException.getCampo(sqle.getMessage(), "CLINICAUNA", "CLN", 3),
                        "guardarPaciente " + sqle.getMessage());
            }
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el Paciente.", "guardarPaciente " + ex.getMessage());
        }
    }

    public Respuesta eliminarPaciente(Long id) {
        Paciente Paciente;
        if (id != null && id > 0) {
            Paciente = em.find(Paciente.class, id);
            System.out.println("VALOR PACIENTE " + Paciente);
            if (Paciente == null) {
                return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No se encontró el empleado a eliminar.", "EliminarPaciente NoResultException");
            }
            System.out.println("CONDICION");
            if (!Paciente.getCitaList().isEmpty()) {

                Paciente.getCitaList().stream().forEach((cita) -> {
                    if (!cita.getEspacioList().isEmpty()) {
                        System.out.println("CITA");
                        cita.getEspacioList().stream().forEach((espacio) -> {
                            System.out.println("ESPACIO");
                            em.remove(espacio);
                        });
                    }
                    em.remove(cita);
                });
            }
            em.remove(Paciente);
        } else {
            return new Respuesta(false, CodigoRespuesta.ERROR_CLIENTE, "Debe cargar el Paciente a eliminar.", "EliminarPaciente NoResultException");
        }
        return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "");
        /*try {
            
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, CodigoRespuesta.ERROR_PERMISOS, "No se puede eliminar el Paciente porque tiene relaciones con otros registros.", "EliminarPaciente " + ex.getMessage());
            }
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el Paciente.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al eliminar el Paciente.", "EliminarPaciente " + ex.getMessage());
        }*/
    }

    public Respuesta getPacientes(String cedula, String nombre, String pApellido) {
        try {
            Query qryEmpleado = em.createNamedQuery("Paciente.findByPacCedulaNombrePapellido", Paciente.class);
            qryEmpleado.setParameter("pacCedula", cedula);
            qryEmpleado.setParameter("pacNombre", nombre);
            qryEmpleado.setParameter("pacPapellido", pApellido);

            List<Paciente> pacientes = qryEmpleado.getResultList();
            List<PacienteDto> pacientesDto = new ArrayList<>();
            for (Paciente paciente : pacientes) {
                pacientesDto.add(new PacienteDto(paciente));
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Pacientes", pacientesDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "No existen pacientes con los criterios ingresados.", "getPacientes NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el paciente.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al consultar el paciente.", "getPacientes " + ex.getMessage());
        }
    }

    public Respuesta getControl(String cedula) {
        generadorJasper generador = new generadorJasper();
        Connection connection = em.unwrap(Connection.class);
        Respuesta resp = generador.generaReporteControlMed(cedula, connection);
        if (resp.getEstado()) {
            return pdf();
        } else {
            return resp;
        }
    }

    public Respuesta pdf() {
        /*
            Cargo el pdf con los datos del reporte y lo convierto a bytes para poder serializarlo y mandarlo al Cliente
         */
        try {
            File archivo;
            archivo = new File("ReportePaciente.pdf");
            System.out.println(archivo.getAbsolutePath());
            File file = new File(archivo.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream input = new BufferedInputStream(fis);
            byte[] salida = new byte[(int) file.length()];
            input.read(salida);
            fis.close();
            input.close();
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "reporte", salida);
        } catch (IOException e) {
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Error en reporte", e.getMessage());
        }
    }
}
