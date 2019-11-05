/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.controller;

import cr.ac.una.wsclinicauna.model.PacienteDto;
import cr.ac.una.wsclinicauna.model.UsuarioDto;
import cr.ac.una.wsclinicauna.service.PacienteService;
import cr.ac.una.wsclinicauna.service.UsuarioService;
import cr.ac.una.wsclinicauna.util.CodigoRespuesta;
import cr.ac.una.wsclinicauna.util.Respuesta;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jose Pablo Bermudez
 */
@Path("/PacienteController")
public class PacienteController {

    @EJB
    PacienteService pacienteService;

    @POST
    @Path("/guardar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarPaciente(PacienteDto Paciente) {
        try {
            Respuesta respuesta = pacienteService.guardarPaciente(Paciente);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((PacienteDto) respuesta.getResultado("Paciente")).build();
        } catch (Exception ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el Paciente").build();
        }
    }
    
    @GET
    @Path("/pacientes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPacientes() {
        try {
            Respuesta respuesta = pacienteService.getPacientes();
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            ArrayList<PacienteDto> pacientesDto = (ArrayList<PacienteDto>) respuesta.getResultado("Pacientes");
            
            return Response.ok(new GenericEntity<List<PacienteDto>>(pacientesDto){}).build();

        } catch (Exception ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el paciente").build();
        }
    }
    
    @GET
    @Path("/pacientes/{cedula}/{nombre}/{pApellido}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPacientes(@PathParam("cedula")String cedula, @PathParam("nombre") String nombre,@PathParam("pApellido") String pApellido) {
        try {
            Respuesta respuesta = pacienteService.getPacientes(cedula,nombre,pApellido);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            ArrayList<PacienteDto> pacientesDto = (ArrayList<PacienteDto>) respuesta.getResultado("Pacientes");
            return Response.ok(new GenericEntity<List<PacienteDto>>(pacientesDto){}).build();

        } catch (Exception ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el paciente").build();
        }
    }
    
    
    @DELETE
    @Path("/eliminar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response EliminarPaciente(@PathParam("id") Long ID) {
        try {
            Respuesta respuesta = pacienteService.eliminarPaciente(ID);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((PacienteDto) respuesta.getResultado("Paciente")).build();
        } catch (Exception ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error Eliminar el Paciente").build();
        }
    }
    
    @GET
    @Path("/reporte/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public byte[] getAgendas(@PathParam("cedula") String cedula) {
        try {
            Respuesta respuesta = pacienteService.getControl(cedula);
            if (!respuesta.getEstado()) {
                return null;
            }
            return (byte[]) respuesta.getResultado("reporte");
        } catch (Exception ex) {
            Logger.getLogger(AgendaController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
