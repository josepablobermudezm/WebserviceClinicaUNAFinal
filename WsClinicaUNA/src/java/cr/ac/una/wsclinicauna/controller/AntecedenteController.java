/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.controller;

import cr.ac.una.wsclinicauna.model.AntecedenteDto;
import cr.ac.una.wsclinicauna.service.AntecedenteService;
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
 * @author Carlos Olivares
 */
@Path("/AntecedenteController")
public class AntecedenteController {
    @EJB
    AntecedenteService antecedenteService;

    @POST
    @Path("/guardar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarAntecedente(AntecedenteDto Antecedente) {
        try {
            Respuesta respuesta = antecedenteService.guardarAntecedente(Antecedente);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((AntecedenteDto) respuesta.getResultado("Antecedente")).build();
        } catch (Exception ex) {
            Logger.getLogger(AntecedenteController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el Antecedente").build();
        }
    }
    
    @DELETE
    @Path("/eliminar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response EliminarAntecedente(@PathParam("id") Long ID) {
        try {
            Respuesta respuesta = antecedenteService.eliminarAntecendente(ID);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            return Response.ok((AntecedenteDto) respuesta.getResultado("Antecedente")).build();
        } catch (Exception ex) {
            Logger.getLogger(AntecedenteController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error Eliminar el Antecedente").build();
        }
    }
    
    @GET
    @Path("/antecedentes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAntecedentes() {
        try {
            Respuesta respuesta = antecedenteService.getAntecedentes();
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            ArrayList<AntecedenteDto> antecedentesDto = (ArrayList<AntecedenteDto>) respuesta.getResultado("Antecedentes");
            
            return Response.ok(new GenericEntity<List<AntecedenteDto>>(antecedentesDto){}).build();

        } catch (Exception ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el antecedente").build();
        }
    }
    
    @GET
    @Path("/antecedentes/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAntecedentes(@PathParam("id")Long ID) {
        try {
            Respuesta respuesta = antecedenteService.getAntecedentes(ID);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            ArrayList<AntecedenteDto> antecedentesDto = (ArrayList<AntecedenteDto>) respuesta.getResultado("Antecedentes");
            
            return Response.ok(new GenericEntity<List<AntecedenteDto>>(antecedentesDto){}).build();

        } catch (Exception ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el antecedente").build();
        }
    }
}
