package br.com.bb.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import br.com.bb.dto.CursoRequest;
import br.com.bb.dto.CursoResponse;
import br.com.bb.model.Curso;

@ApplicationScoped
public class CursoMapper {
    public List<CursoResponse> toResponse(List<Curso> listOfProfessors) {

        if (Objects.isNull(listOfProfessors)) return new ArrayList<>();

        return listOfProfessors.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CursoResponse toResponse(Curso entity) {

        //if (Objects.isNull(entity)) return null;
        Objects.requireNonNull(entity, "Deu erro - entidade nula");

        return  CursoResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
    }

    public Curso toEntity(CursoRequest request) {
         if (Objects.isNull(request)) {
             return null;
         } else {
             return Curso.builder()
                     .name(request.getName())
                     .duration(request.getDuration())
                     .description(request.getDescription())
                     .build();
         }
    }
}
