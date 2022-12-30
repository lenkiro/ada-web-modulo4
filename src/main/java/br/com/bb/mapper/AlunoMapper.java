
package br.com.bb.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import br.com.bb.dto.AlunoResponse;
import br.com.bb.dto.TutorResponse;
import br.com.bb.model.Aluno;
import br.com.bb.model.Professor;

@ApplicationScoped
public class AlunoMapper {

    public List<AlunoResponse> toResponse(List<Aluno> listOfEntities) {

        if (Objects.isNull(listOfEntities)) return new ArrayList<>();

        return listOfEntities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

    }

    public AlunoResponse toResponse(Aluno entity) {

        Objects.requireNonNull(entity, "entity must not be null");

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        var response =
                AlunoResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .dateTime(formatter.format(entity.getDateTime()))
                    .build();

        if (Objects.nonNull(entity.getTutor())) {
            response.setTutor(entity.getTutor().getName());
        }

        return response;
    }

    public TutorResponse toResponse(Professor entity) {

        Objects.requireNonNull(entity, "entity must not be null");

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        return TutorResponse.builder()
                .tutor(entity.getName())
                .atualizacao(formatter.format(LocalDateTime.now()))
                .build();

    }
}