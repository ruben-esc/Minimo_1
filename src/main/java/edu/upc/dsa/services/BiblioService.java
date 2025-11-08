package edu.upc.dsa.services;

import edu.upc.dsa.BiblioManager;
import edu.upc.dsa.BiblioManagerImpl;

import edu.upc.dsa.models.Prestec;
import edu.upc.dsa.models.Lector;
import edu.upc.dsa.models.LlibreEmmagatzemat;
import edu.upc.dsa.models.LlibreCatalogat;

import edu.upc.dsa.exceptions.LectorNoExisteixException;
import edu.upc.dsa.exceptions.LlibreNoExisteixException;
import edu.upc.dsa.exceptions.ExemplarsInsuficientsException;
import edu.upc.dsa.exceptions.NoHiHaLlibresACatalogarException;

import edu.upc.dsa.models.dto.PrestecRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/biblioteca", description = "Endpoint per a la gestió de la biblioteca")
@Path("/biblioteca")
public class BiblioService {

    private BiblioManager bm;

    public BiblioService() {
        this.bm = BiblioManagerImpl.getInstance();
    }

    // --- OPERACIÓ 1: LECTORS (POST per Crear/Actualitzar) ---

    @POST
    @ApiOperation(value = "Afegir o actualitzar dades d'un lector", notes = "Si l'ID ja existeix, s'actualitzen les dades.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lector afegit (Nou)", response = Lector.class),
            @ApiResponse(code = 200, message = "Lector actualitzat", response = Lector.class),
            @ApiResponse(code = 500, message = "Error de Validació/Dades")
    })
    @Path("/lectors")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response afegirNouLector(Lector lector) {
        if (lector.getId() == null || lector.getNom() == null) {
            return Response.status(500).entity("Dades de lector incompletes").build();
        }
        boolean isNew = this.bm.afegirNouLector(lector);

        // Retornem 201 si és nou, 200 si s'actualitza
        if (isNew) {
            return Response.status(201).entity(lector).build();
        } else {
            return Response.status(200).entity(lector).build();
        }
    }

    // --- OPERACIÓ 2: EMMAGATZEMAR LLIBRE (POST) ---

    @POST
    @ApiOperation(value = "Emmagatzema un nou llibre per a catalogació (en un munt).", notes = "Requereix ID d'exemplar i dades d'ISBN.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Llibre apilat correctament"),
            @ApiResponse(code = 500, message = "Error de Validació/Dades")
    })
    @Path("/llibres/emmagatzemar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response emmagatzemarLlibre(LlibreEmmagatzemat llibre) {
        if (llibre.getId() == null || llibre.getIsbn() == null) {
            return Response.status(500).entity("Dades de llibre incompletes").build();
        }
        this.bm.emmagatzemarLlibre(llibre);
        return Response.status(201).build();
    }

    // --- OPERACIÓ 3: CATALOGAR LLIBRE (POST) ---

    @POST
    @ApiOperation(value = "Extreu un llibre del munt i el cataloga.", notes = "Incrementa exemplars si l'ISBN ja existeix.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Llibre catalogat correctament", response = LlibreCatalogat.class),
            @ApiResponse(code = 404, message = "No hi ha llibres pendents de catalogar"),
            @ApiResponse(code = 500, message = "Error intern")
    })
    @Path("/llibres/catalogar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response catalogarLlibre() {
        try {
            LlibreCatalogat lc = this.bm.catalogarLlibre();
            return Response.status(201).entity(lc).build();
        } catch (NoHiHaLlibresACatalogarException e) {
            return Response.status(404).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    // --- OPERACIÓ 4: PRESTAR LLIBRE (POST) ---

    @POST
    @ApiOperation(value = "Realitza un nou préstec d'un llibre catalogat.", notes = "Decrementa exemplars disponibles.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Préstec realitzat correctament"),
            @ApiResponse(code = 400, message = "Error en dades (Lector/Llibre no existeix o exemplars insuficients)"),
            @ApiResponse(code = 500, message = "Error de Validació/Dades")
    })
    @Path("/prestecs")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response prestarLlibre(PrestecRequest prestec) {
        if (prestec.getIdLector() == null || prestec.getIdLector() == null || prestec.getIsbnLlibreCatalogat() == null) {
            return Response.status(500).entity("Dades del préstec incompletes").build();
        }

        try {
            this.bm.prestarLlibre(
                    prestec.getIdLector(),
                    prestec.getIsbnLlibreCatalogat()
            );
            return Response.status(201).build();

        } catch (LectorNoExisteixException | LlibreNoExisteixException | ExemplarsInsuficientsException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    // --- OPERACIÓ 5: CONSULTAR PRÉSTECS D'UN LECTOR (GET) ---

    @GET
    @ApiOperation(value = "Consulta tots els préstecs d'un lector.", notes = "Se sap que l'ID del lector existeix.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta exitosa", response = Prestec.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Lector no trobat o sense préstecs")
    })
    @Path("/prestecs/{idLector}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarPrestecsLector(@PathParam("idLector") String idLector) {
        List<Prestec> prestecs = this.bm.consultarPrestecsLector(idLector);

        if (prestecs.isEmpty()) {
            // Si el lector no té préstecs, es retorna 404 (No trobat) o 200 amb llista buida.
            // S'escull 404 per indicar que "no es van trobar resultats" (common practice).
            return Response.status(404).entity("Lector " + idLector + " sense préstecs registrats.").build();
        }

        GenericEntity<List<Prestec>> entity = new GenericEntity<List<Prestec>>(prestecs) {};
        return Response.status(200).entity(entity).build();
    }
}