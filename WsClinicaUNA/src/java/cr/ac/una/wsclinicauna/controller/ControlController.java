/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.controller;

import cr.ac.una.wsclinicauna.model.ControlDto;
import cr.ac.una.wsclinicauna.service.ControlService;
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
@Path("/ControlController")
public class ControlController {

    @EJB
    ControlService ControlService;

    @POST
    @Path("/guardar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarControl(ControlDto Control) {
        try {
            Respuesta respuesta = ControlService.guardarControl(Control);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((ControlDto) respuesta.getResultado("Control")).build();
        } catch (Exception ex) {
            Logger.getLogger(ControlController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el Control").build();
        }
    }
    
    @GET
    @Path("/controles")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getControles() {
        try {
            Respuesta respuesta = ControlService.getControles();
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            ArrayList<ControlDto> ControlesDto = (ArrayList<ControlDto>) respuesta.getResultado("controles");
            
            return Response.ok(new GenericEntity<List<ControlDto>>(ControlesDto){}).build();

        } catch (Exception ex) {
            Logger.getLogger(ControlController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el Control").build();
        }
    }
    @GET
    @Path("/Control/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getControles(@PathParam("id") Long ID) {
        try {
            Respuesta respuesta = ControlService.getControles(ID);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            ArrayList<ControlDto> ControlsDto = (ArrayList<ControlDto>) respuesta.getResultado("Controles");
            
            return Response.ok(new GenericEntity<List<ControlDto>>(ControlsDto){}).build();

        } catch (Exception ex) {
            Logger.getLogger(ControlController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el Control").build();
        }
    }
    
    @DELETE
    @Path("/eliminar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response EliminarControl(@PathParam("id") Long ID) {
        try {
            Respuesta respuesta = ControlService.eliminarControl(ID);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((ControlDto) respuesta.getResultado("Control")).build();
        } catch (Exception ex) {
            Logger.getLogger(ControlController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error Eliminar el Control").build();
        }
    }
    
}

