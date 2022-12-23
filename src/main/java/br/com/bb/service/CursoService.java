package br.com.bb.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.bb.dto.CursoRequest;
import br.com.bb.dto.CursoResponse;
import br.com.bb.mapper.CursoMapper;
import br.com.bb.model.Curso;
import br.com.bb.repository.CursoRepository;
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
}
