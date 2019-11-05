/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.controller;

import cr.ac.una.wsclinicauna.model.ExpedienteDto;
import cr.ac.una.wsclinicauna.service.ExpedienteService;
import cr.ac.una.wsclinicauna.util.CodigoRespuesta;
import cr.ac.una.wsclinicauna.util.Respuesta;
import java.util.ArrayList;
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
@Path("/ExpedienteController")
public class ExpedienteController {

    @EJB
    ExpedienteService ExpedienteService;

    @POST
    @Path("/guardar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarExpediente(ExpedienteDto Expediente) {
        try {
            Respuesta respuesta = ExpedienteService.guardarExpediente(Expediente);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((ExpedienteDto) respuesta.getResultado("Expediente")).build();
        } catch (Exception ex) {
            Logger.getLogger(ExpedienteController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el Expediente").build();
        }
    }
    
    @GET
    @Path("/Expedientes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getExpedientes() {
        try {
            Respuesta respuesta = ExpedienteService.getExpedientes();
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            
            ArrayList<ExpedienteDto> expedientes = (ArrayList<ExpedienteDto>) respuesta.getResultado("Expedientes");
            return Response.ok(new GenericEntity<ArrayList<ExpedienteDto>>(expedientes){}).build();
        } catch (Exception ex) {
            Logger.getLogger(ExpedienteController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el Expediente").build();
        }
    }
    
    @DELETE
    @Path("/eliminar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response EliminarExpediente(@PathParam("id") Long ID) {
        try {
            Respuesta respuesta = ExpedienteService.eliminarExpediente(ID);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((ExpedienteDto) respuesta.getResultado("Expediente")).build();
        } catch (Exception ex) {
            Logger.getLogger(ExpedienteController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error Eliminar el Expediente").build();
        }
    }
}

