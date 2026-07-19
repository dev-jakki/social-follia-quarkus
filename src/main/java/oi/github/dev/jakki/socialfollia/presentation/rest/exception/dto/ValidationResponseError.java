package oi.github.dev.jakki.socialfollia.presentation.rest.exception.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record ValidationResponseError(String message, List<FieldError> errors) {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;

    public static ValidationResponseError createFromValidation(Set<ConstraintViolation<?>> violations) {
        List<FieldError> erros = violations
                .stream()
                .map(cv -> {
                    String campoNome = "";

                    for (jakarta.validation.Path.Node node : cv.getPropertyPath()) {
                        campoNome = node.getName();
                    }

                    return new FieldError(campoNome, cv.getMessage());
                })
                .collect(Collectors.toList());

        String message = "Validation failed";
        return new ValidationResponseError(message, erros);
    }

    public Response withStatusCode(int code) {
        return Response.status(code).entity(this).build();
    }
}