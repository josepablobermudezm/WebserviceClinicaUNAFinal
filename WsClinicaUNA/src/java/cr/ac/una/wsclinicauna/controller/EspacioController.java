/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.controller;

import cr.ac.una.wsclinicauna.model.EspacioDto;
import cr.ac.una.wsclinicauna.service.EspacioService;
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
@Path("/EspacioController")
public class EspacioController {

    @EJB
    EspacioService EspacioService;

    @POST
    @Path("/guardar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarEspacio(EspacioDto Espacio) {
        System.out.println("hola");
        try {
            Respuesta respuesta = EspacioService.guardarEspacio(Espacio);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((EspacioDto) respuesta.getResultado("Espacio")).build();
        } catch (Exception ex) {
            Logger.getLogger(EspacioController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el Espacio").build();
        }
    }
    
    @GET
    @Path("/Espacios")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getEspacios() {
        try {
            Respuesta respuesta = EspacioService.getEspacios();
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            ArrayList<EspacioDto> EspaciosDto = (ArrayList<EspacioDto>) respuesta.getResultado("Espacios");
            
            return Response.ok(new GenericEntity<List<EspacioDto>>(EspaciosDto){}).build();

        } catch (Exception ex) {
            Logger.getLogger(EspacioController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el Espacio").build();
        }
    }
    
    @DELETE
    @Path("/eliminar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response EliminarEspacio(@PathParam("id") Long ID) {
        try {
            Respuesta respuesta = EspacioService.eliminarEspacio(ID);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((EspacioDto) respuesta.getResultado("Espacio")).build();
        } catch (Exception ex) {
            Logger.getLogger(EspacioController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error Eliminar el Espacio").build();
        }
    }
    
}

