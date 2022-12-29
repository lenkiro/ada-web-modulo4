package br.com.bb.mapper;


import javax.enterprise.context.ApplicationScoped;

import br.com.bb.dto.ProfessorRequest;
import br.com.bb.dto.ProfessorResponse;
import br.com.bb.model.Professor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProfessorMapper {
    public List<ProfessorResponse> toResponse(List<Professor> listOfProfessors) {

        if (Objects.isNull(listOfProfessors)) return new ArrayList<>();

        return listOfProfessors.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProfessorResponse toResponse(Professor entity) {

        //if (Objects.isNull(entity)) return null;
        Objects.requireNonNull(entity, "Deu erro - entidade nula");

        var formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY hh:mm:ss");
        return  ProfessorResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .dateTime(formatter.format(entity.getDateTime()))
                    .build();
    }

    public Professor toEntity(ProfessorRequest request) {
         if (Objects.isNull(request)) {
             return null;
         } else {
             return Professor.builder()
                     .name(request.getName())
                     .build();
         }
    }
}
