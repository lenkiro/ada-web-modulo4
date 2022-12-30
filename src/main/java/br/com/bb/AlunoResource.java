package br.com.bb;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.bb.dto.AlunoRequest;
import br.com.bb.dto.ErrorResponse;
import br.com.bb.service.AlunoService;
import lombok.RequiredArgsConstructor;



@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class AlunoResource {

    private final AlunoService service;

    @GET
    public Response list() {
        final var response = service.retrieveAll();

        return Response.ok(response).build();
    }

    @POST
    public Response save(final AlunoRequest request) {
        try {
            final var response = service.save(request);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(response)
                    .build();

        } catch(ConstraintViolationException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponse.createFromValidation(e))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {

        final var response = service.getById(id);

        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{id}/tutor/{idProfessor}")
    public Response updateTitular(@PathParam("id") int idAluno, @PathParam("idProfessor") int idProfessor) {
        final var response = service.updateTutor(idAluno, idProfessor);

        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }
}