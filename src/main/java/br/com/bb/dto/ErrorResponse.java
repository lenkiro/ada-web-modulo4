package br.com.bb.dto;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class ErrorResponse {
    private String message;
    private Collection<ErrorMessage> errors;
    

    public static ErrorResponse createFromValidation(ConstraintViolationException constraintViolationException){
        return null;
    }
}
