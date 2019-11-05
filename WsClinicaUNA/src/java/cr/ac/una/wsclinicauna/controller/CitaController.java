/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.controller;

import cr.ac.una.wsclinicauna.model.CitaDto;
import cr.ac.una.wsclinicauna.service.CitaService;
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
@Path("/CitaController")
public class CitaController {

    @EJB
    CitaService CitaService;

    @POST
    @Path("/guardar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarCita(CitaDto Cita) {
        try {
            Respuesta respuesta = CitaService.guardarCita(Cita);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((CitaDto) respuesta.getResultado("Cita")).build();
        } catch (Exception ex) {
            Logger.getLogger(CitaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el Cita").build();
        }
    }
    
    @GET
    @Path("/Citas")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCitas() {
        try {
            Respuesta respuesta = CitaService.getCitas();
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            ArrayList<CitaDto> CitasDto = (ArrayList<CitaDto>) respuesta.getResultado("Citas");
            
            return Response.ok(new GenericEntity<List<CitaDto>>(CitasDto){}).build();

        } catch (Exception ex) {
            Logger.getLogger(CitaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el Cita").build();
        }
    }
    
    @DELETE
    @Path("/eliminar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response EliminarCita(@PathParam("id") Long ID) {
        try {
            Respuesta respuesta = CitaService.eliminarCita(ID);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((CitaDto) respuesta.getResultado("Cita")).build();
        } catch (Exception ex) {
            Logger.getLogger(CitaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error Eliminar el Cita").build();
        }
    }
    
}

