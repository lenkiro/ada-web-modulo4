package br.com.bb.service;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.bb.dto.ProfessorRequest;
import br.com.bb.dto.ProfessorResponse;
import br.com.bb.mapper.ProfessorMapper;
import br.com.bb.model.Professor;
import br.com.bb.repository.ProfessorRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorMapper mapper;

    private final ProfessorRepository repository;

    public List<ProfessorResponse> retrieveAll() {
        log.info("Listing professors");
        // final List<Professor> listOfProfessors = Professor.listAll();
        final List<Professor> listOfProfessors = repository.listAll();
        return  mapper.toResponse(listOfProfessors);
    }

    public ProfessorResponse getById(int id) {
        log.info("Getting professor id-{}", id);

        Professor professor = repository.findById(id);
        return mapper.toResponse(professor);
    }

    @Transactional
    public ProfessorResponse save(@Valid ProfessorRequest professorRequest) {

        log.info("Saving professor - {}", professorRequest);

        Professor entity =
                Professor.builder()
                .name(professorRequest.getName())
                .build();

        //entity.persistAndFlush();
        repository.persistAndFlush(entity);

        return mapper.toResponse(entity);
    }

    @Transactional
    public ProfessorResponse update(int id, @Valid ProfessorRequest professorRequest) {

        log.info("Updating professor id - {}, data - {}", id, professorRequest);

        Optional<Professor> professor = repository.findByIdOptional(id);

        if (professor.isPresent()) {
            var entity = professor.get();
            entity.setName(professorRequest.getName());
            repository.persistAndFlush(entity);
            return mapper.toResponse(entity);
        }

        return new ProfessorResponse();
    }

    @Transactional
    public void delete(int id) {
        log.info("Deleting professor id - {}", id);
        repository.findByIdOptional(id).ifPresent(repository::delete);
    }
}
