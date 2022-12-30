package br.com.bb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.bb.dto.AlunoRequest;
import br.com.bb.dto.AlunoResponse;
import br.com.bb.dto.TutorResponse;
import br.com.bb.mapper.AlunoMapper;
import br.com.bb.model.Aluno;
import br.com.bb.repository.AlunoRepository;
import br.com.bb.repository.ProfessorRepository;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoMapper mapper;
    private final AlunoRepository repository;
    private final ProfessorRepository professorRepository;

    public List<AlunoResponse> retrieveAll() {
        log.info("Listing alunos");
        final var listOfEntities = repository.listAll();
        return  mapper.toResponse(listOfEntities);
    }

    @Transactional
    public AlunoResponse save(@Valid AlunoRequest request) {
        Objects.requireNonNull(request, "request must not be null");

        log.info("Saving aluno - {}", request);

        var entity =
                Aluno.builder()
                        .name(request.getName())
                        .build();

        repository.persist(entity);

        return mapper.toResponse(entity);
    }

    public AlunoResponse getById(int id) {
        log.info("Getting aluno id-{}", id);

        var entity = repository.findById(id);

        if (Objects.isNull(entity)) throw new EntityNotFoundException("Aluno not found");

        return mapper.toResponse(entity);
    }

    @Transactional
    public TutorResponse updateTutor(int idAluno, int idProfessor) {

        log.info("Updating tutor aluno-id: {}, professor-id: {}", idAluno, idProfessor);

        //find entities
        var aluno = repository.findById(idAluno);
        var professor = professorRepository.findById(idProfessor);

        //validate is not empty
        if (Objects.isNull(aluno)) throw new EntityNotFoundException("Aluno not found");
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor not found");

        //Update
        aluno.setTutor(professor);
        repository.persist(aluno);

        return mapper.toResponse(professor);
    }

    public List<AlunoResponse> getTutoradosByProfessorId(int idProfessor) {

        log.info("Getting tutorados by professor-id: {}", idProfessor);

        var professor = professorRepository.findById(idProfessor);
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor not found");

        List<Aluno> listOfEntities = repository.getTutoradosByProfessor(professor);

        return mapper.toResponse(listOfEntities);
    }
}
