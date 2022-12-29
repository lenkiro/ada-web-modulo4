package br.com.bb.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import br.com.bb.dto.CursoRequest;
import br.com.bb.dto.CursoResponse;
import br.com.bb.model.Curso;
import br.com.bb.model.Professor;
import br.com.bb.dto.TitularResponse;;

@ApplicationScoped
public class CursoMapper {
    public List<CursoResponse> toResponse(List<Curso> listOfProfessors) {

        if (Objects.isNull(listOfProfessors))
            return new ArrayList<>();

        return listOfProfessors.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CursoResponse toResponse(Curso entity) {

        // if (Objects.isNull(entity)) return null;
        Objects.requireNonNull(entity, "Deu erro - entidade nula");

        var response = CursoResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .duration(entity.getDuration())
                .build();
                
        if (Objects.nonNull(entity.getTitular())) {
            response.setTitular(entity.getTitular().getName());
        }
        return response;
    }

    public TitularResponse toResponse(Professor entity) {

        if (Objects.isNull(entity))
            return null;

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        return TitularResponse.builder()
                .titular(entity.getName())
                .atualizacao(formatter.format(LocalDateTime.now()))
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
