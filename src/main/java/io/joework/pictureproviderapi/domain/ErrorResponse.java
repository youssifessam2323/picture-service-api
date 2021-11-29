package io.joework.pictureproviderapi.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse { 
    private final HttpStatus status;
    private final String message;
    private String stackTrace;
    private List<ValidationError> errors;

    public void addValidationError(String field, String message){
        if(Objects.isNull(errors)){
            errors = new ArrayList<>();
        }
        errors.add(new ValidationError(field, message));
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    static class ValidationError{
        private final String field;
        private final String message;
    }
}