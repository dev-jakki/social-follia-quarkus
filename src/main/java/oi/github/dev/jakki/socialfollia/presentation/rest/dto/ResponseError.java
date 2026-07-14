package oi.github.dev.jakki.socialfollia.presentation.rest.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record ResponseError(String message, List<FieldError> errors) {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;

    public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>> violations) {
        List<FieldError> erros = violations
                .stream()
                .map(cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());

        String message = "Validation failed";
        var responseError = new ResponseError(message, erros);
        return responseError;
    }

    public Response withStatusCode(int code) {
        return Response.status(code).entity(this).build();
    }

//    public static ResponseError responseDefault(String message) {
//        return new ResponseError(Response.Status.BAD_REQUEST.getStatusCode(), message, List.of());
//    }
//
//    public static ResponseError conflict(String message) {
//        return new ResponseError(Response.Status.CONFLICT.getStatusCode(), message, List.of());
//    }
}
