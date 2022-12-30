package br.com.bb;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.bb.dto.ProfessorRequest;
import br.com.bb.service.AlunoService;
import br.com.bb.service.CursoService;
import br.com.bb.service.ProfessorService;
import lombok.RequiredArgsConstructor;

@Path("/professores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class ProfessorResource {

    private final ProfessorService service;
    private final CursoService cursoService;
    private final AlunoService alunoService;
    


    @GET
    public Response listProfessors() {
        final var response = service.retrieveAll();

        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getProfessor(@PathParam("id") int id) {

        final var response = service.getById(id);

        return Response.ok(response).build();
    }

    @POST
    public Response saveProfessor(final ProfessorRequest professor) {

        final var response = service.save(professor);

        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProfessor(@PathParam("id") int id, ProfessorRequest professor) {

        var response = service.update(id, professor);

        return Response
                .ok(response)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeProfessor(@PathParam("id") int id) {

        service.delete(id);

        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/{id}/curso")
    public Response getCurso(@PathParam("id") int id){
        return Response
        .ok(cursoService.getDisciplinaByProfessorId(id))
        .build();
    }

    @GET
    @Path("/{id}/tutorados")
    public Response getTutorados(@PathParam("id") int id){
        return Response
            .ok(alunoService.getTutoradosByProfessorId(id))
            .build();
    }
}
