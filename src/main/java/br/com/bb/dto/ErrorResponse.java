package br.com.bb.dto;

import java.util.Collection;
import java.util.stream.Collectors;

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
        // var violations = 
        //     constraintViolationException
        //     .getConstraintViolations()
        //     .stream().map(cv -> ErrorMessage(cv.getMessage() , cv.getMessageTemplate()))
        //     .collect(Collectors.toList());
        //     return new ErrorResponse("Validation Errors", violations);
        return null;
    }
}
