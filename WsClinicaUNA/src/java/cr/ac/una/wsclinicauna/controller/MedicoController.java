/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.controller;

import cr.ac.una.wsclinicauna.model.MedicoDto;
import cr.ac.una.wsclinicauna.service.MedicoService;
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
@Path("/MedicoController")
public class MedicoController {
    
    @EJB
    MedicoService medicoService;

    @POST
    @Path("/guardar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarMedico(MedicoDto Medico) {
        try {
            Respuesta respuesta = medicoService.guardarMedico(Medico);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((MedicoDto) respuesta.getResultado("Medico")).build();
        } catch (Exception ex) {
            Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el Medico").build();
        }
    }
    
    @GET
    @Path("/medicos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMedicos() {
        try {
            Respuesta respuesta = medicoService.getMedicos();
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            
            ArrayList<MedicoDto> medicosDto = (ArrayList<MedicoDto>) respuesta.getResultado("Medicos");
            
            return Response.ok(new GenericEntity<List<MedicoDto>>(medicosDto){}).build();

        } catch (Exception ex) {
            Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el medico").build();
        }
    }
    
    @DELETE
    @Path("/eliminar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response EliminarMedico(@PathParam("id") Long ID) {
        try {
            Respuesta respuesta = medicoService.eliminarMedico(ID);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((MedicoDto) respuesta.getResultado("Medico")).build();
        } catch (Exception ex) {
            Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error Eliminar el Medico").build();
        }
    }
    
    @GET
    @Path("/medicos/{codigo}/{carne}/{folio}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPacientes(@PathParam("codigo")String codigo, @PathParam("carne") String carne,@PathParam("folio") String folio) {
        try {
            Respuesta respuesta = medicoService.getMedicos(codigo,carne,folio);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            ArrayList<MedicoDto> medicosDto = (ArrayList<MedicoDto>) respuesta.getResultado("Medicos");
            return Response.ok(new GenericEntity<List<MedicoDto>>(medicosDto){}).build();

        } catch (Exception ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el medico").build();
        }
    }
    
}
