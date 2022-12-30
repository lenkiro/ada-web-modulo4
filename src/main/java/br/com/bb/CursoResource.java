package br.com.bb;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.bb.dto.CursoRequest;
import br.com.bb.service.CursoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("/curso")
@Slf4j
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class CursoResource {

    
    CursoService service;

    @Inject
    public CursoResource(CursoService service) {
        this.service = service;
    }

    
    
    
    private Mensagem mensagem;

    public CursoResource(final Mensagem mensagem){
        this.mensagem = mensagem;
    }

    @GET
    @Path("/mensagem")
    public Response mensagem(){

        return Response
            .ok(mensagem.boasvindas("guys"))
            .build();
    
    }

    
    @GET
    public Response list() {
        final var response = service.retrieveAll();

        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getCurso(@PathParam("id") int id){
        log.info("WHATT "+ service.toString());
        final var response = service.getById(id);

        return Response.ok(response).build();
    }

    // @GET
    // public Response listingAluno(){
    //     log.info("Listing Alunos");
    //     List<CursoDto> alunoDtoList = new ArrayList<>(mapCursos.values());
    //     return Response
    //             .ok(alunoDtoList)
    //             .build();
    // }

    @POST
    public Response saveCurso(final CursoRequest cursoRequest){
        log.info("a post", cursoRequest);
        final var response = service.save(cursoRequest);
        return Response
            .status(Response.Status.CREATED)
            .entity(response)
            .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCurso(@PathParam("id") int id){
        // var target = mapCursos.remove(id);
        // log.info("removing", target);
        service.delete(id);
        log.info("removing the thing");
        return Response.status(Status.NO_CONTENT).build();
        
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateCurso(@PathParam("id") int id, CursoRequest cursoRequest){
        
        var response = service.update(id, cursoRequest);
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{id}/titular/{idProfessor}")
    public Response updateTitular(@PathParam("id") int idCurso, @PathParam("idProfessor") int idProfessor) {
        final var response = service.updateTitular(idCurso, idProfessor);

        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }








    @Path("/no")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    
    public String showStudent(){
        return " gal";
    }
}   
