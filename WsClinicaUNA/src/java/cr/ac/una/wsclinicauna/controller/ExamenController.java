/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.controller;

import cr.ac.una.wsclinicauna.model.ExamenDto;
import cr.ac.una.wsclinicauna.service.ExamenService;
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
@Path("/ExamenController")
public class ExamenController {

    @EJB
    ExamenService ExamenService;

    @POST
    @Path("/guardar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarExamen(ExamenDto Examen) {
        try {
            Respuesta respuesta = ExamenService.guardarExamen(Examen);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((ExamenDto) respuesta.getResultado("Examen")).build();
        } catch (Exception ex) {
            Logger.getLogger(ExamenController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el Examen").build();
        }
    }
    
    @GET
    @Path("/Examenes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getExamenes() {
        try {
            Respuesta respuesta = ExamenService.getExamenes();
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            ArrayList<ExamenDto> ExamenesDto = (ArrayList<ExamenDto>) respuesta.getResultado("Examenes");
            
            return Response.ok(new GenericEntity<List<ExamenDto>>(ExamenesDto){}).build();

        } catch (Exception ex) {
            Logger.getLogger(ExamenController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el Examen").build();
        }
    }
    
    @GET
    @Path("/Examenes/{expId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getExamenes(@PathParam("expId")Long ID) {
        try {
            Respuesta respuesta = ExamenService.getExamenes(ID);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            ArrayList<ExamenDto> ExamenesDto = (ArrayList<ExamenDto>) respuesta.getResultado("Examenes");
            return Response.ok(new GenericEntity<List<ExamenDto>>(ExamenesDto){}).build();

        } catch (Exception ex) {
            Logger.getLogger(ExamenController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el Examen").build();
        }
    }
    
    @DELETE
    @Path("/eliminar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response EliminarExamen(@PathParam("id") Long ID) {
        try {
            Respuesta respuesta = ExamenService.eliminarExamen(ID);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((ExamenDto) respuesta.getResultado("Examen")).build();
        } catch (Exception ex) {
            Logger.getLogger(ExamenController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error Eliminar el Examen").build();
        }
    }
    
}

