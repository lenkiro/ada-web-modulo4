package br.com.bb.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.ws.rs.GET;

import br.com.bb.dto.CursoRequest;
import br.com.bb.dto.CursoResponse;
import br.com.bb.dto.TitularResponse;
import br.com.bb.exception.InvalidStateException;
import br.com.bb.mapper.CursoMapper;
import br.com.bb.model.Curso;
import br.com.bb.repository.CursoRepository;
import br.com.bb.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor
@ApplicationScoped
@Slf4j
public class CursoService {

    
    // public CursoDto getById(int id){
    // log.info(("Curso id"));
    // }

    // public List<CursoDto> retrieveAll() {
    //     log.info("Listing cursos");
    //     return List.of(new CursoDto(1, "Joao"), CursoDto.builder().id(2).nome("Maria").build());
    // }
    
    private final CursoRepository repo;
    private final CursoMapper mapper;
    private final ProfessorRepository profRepo;

    public CursoResponse getById(int id) {
        log.info("Getting curso id-{}", id);
        Curso curso = repo.findById(id);
        
        return mapper.toResponse(curso);
        // return CursoResponse.builder()
        //         .id(id) 
        //         .name("Name of curso")
        //         .description("The Description")
        //         .duration(1)
        //         .build();
    }

    @Transactional
    public CursoResponse save(CursoRequest cursoRequest) {
        log.info("Saving curso - {}", cursoRequest);
        Curso entity = mapper.toEntity(cursoRequest);
                // Curso.builder()
                // .name(cursoRequest.getName())
                
                // .build();

        //entity.persistAndFlush();
        repo.persistAndFlush(entity);

        return mapper.toResponse(entity);

    }

    public CursoResponse update(int id, CursoRequest cursoRequest) {
        log.info("Updating curso id - {}, data - {}", id, cursoRequest);
        Optional<Curso> curso = repo.findByIdOptional(id);

        if (curso.isPresent()) {
            var entity = curso.get();
            entity.setName(cursoRequest.getName());
            entity.setDescription(cursoRequest.getDescription());
            entity.setDuration(cursoRequest.getDuration());
            repo.persistAndFlush(entity);
            return mapper.toResponse(entity);
        }

        return new CursoResponse();
    }

    @Transactional
    public void delete(int id) {
        log.info("Deleting curso id - {}", id);
        repo.findByIdOptional(id).ifPresent(repo::delete);
    }

    @Transactional
    public TitularResponse updateTitular(int idDisciplina, int idProfessor) {

        log.info("Updating titular disciplina-id: {}, professor-id: {}", idDisciplina, idProfessor);

        //find entities
        var disciplina = repo.findById(idDisciplina);
        var professor = profRepo.findById(idProfessor);

        //validate is not empty
        if (Objects.isNull(disciplina)) throw new EntityNotFoundException("Disciplina not found");
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor not found");

        //verify if Professor has no Disciplina
        var query = repo.find("titular", professor);
        if (query.count() > 0) throw new InvalidStateException("Professor must have at most one Disciplina as titular");


        //Update
        disciplina.setTitular(professor);
        repo.persist(disciplina);

        return mapper.toResponse(professor);
    }

    public CursoResponse getDisciplinaByProfessorId(int idProfessor) {

        log.info("Getting disciplina by professor-id: {}", idProfessor);

        var professor = profRepo.findById(idProfessor);
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor not found");

        var query = repo.find("titular", professor);
        if (query.count() == 0) throw new EntityNotFoundException("Disciplina not found");
        if (query.count() > 1) throw new InvalidStateException("Professor must have at most one Disciplina as titular");

        var disciplina = query.singleResult();

        return mapper.toResponse(disciplina);

    }

    @GET
    public List<CursoResponse> retrieveAll() {

        log.info("listing cursos");
        final var resposts = repo.listAll();
        return mapper.toResponse(resposts);
    }
}

