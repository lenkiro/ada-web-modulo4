package br.com.bb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bb.dto.AlunoDto;



@Path("/aluno")
public class AlunoResource {


    

    private static final Logger log = LoggerFactory.getLogger(AlunoResource.class);

    private final Map<Integer, AlunoDto> mapAlunos = new HashMap<>();


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveAluno(AlunoDto aluno){
        mapAlunos.put(aluno.getId(), aluno);
        return Response
            .status(Response.Status.CREATED)
            .build();
    }

    @Path("/no")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    
    public String showStudent(){
        return " guy";
    }

    @GET
    public Response listingAluno(){
        log.info("Listing Alunos");
        List<AlunoDto> alunoDtoList = new ArrayList<>(mapAlunos.values());
        return Response
                .ok(alunoDtoList)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getAluno(@PathParam("id") int id){
        log.info("Getting aluno {}", id);
        var aluno = mapAlunos.get(id);
        
        if(Objects.isNull(aluno)){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        else{
            return Response
                .ok(aluno)
                .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removeAluno(@PathParam("id") int id){
        log.info("remove aluno {}", id);
        var aluno = mapAlunos.get(id);
        
        if(Objects.isNull(aluno)){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        else{
            mapAlunos.remove(id);
            return Response
                .status(Response.Status.NO_CONTENT)
                .build();
        }
    }

    @PUT
    @Path("{id}")
    public Response updateAluno(@PathParam("id") int id, AlunoDto alunoNovo){
        log.info("updating aluno", id);
        var alunoVelho = mapAlunos.get(id);
        
        if(Objects.isNull(alunoVelho)){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }else{
            alunoVelho.setNome(alunoNovo.getNome());
            return Response
                    .status(Response.Status.OK)
                    .build();
        }
    }

    @GET
    @Path("/filter")
    public Response filterAlunoBy(@QueryParam("beginning") String beginning){
        log.info("filtering", beginning);
        List<AlunoDto> filteredList = mapAlunos.values().stream()
        .filter((v)->v.getNome().startsWith(beginning)).collect(Collectors.toList());
        return Response
                .ok(filteredList)
                .build();
    }
}
