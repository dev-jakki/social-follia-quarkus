package oi.github.dev.jakki.socialfollia.presentation.rest.handler;

import jakarta.annotation.Priority;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import oi.github.dev.jakki.socialfollia.domain.exception.ConflictException;
import oi.github.dev.jakki.socialfollia.domain.exception.NotFoundException;
import oi.github.dev.jakki.socialfollia.presentation.rest.dto.ResponseError;

import java.util.List;

@Provider
@Priority(1)
public class GlobalExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return ResponseError
                .createFromValidation(exception.getConstraintViolations())
                .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
    }

    @Provider
    @Priority(2)
    public static class GeneralExceptionHandler implements ExceptionMapper<Exception> {
        @Override
        public Response toResponse(Exception exception) {
            return switch (exception) {
                case NotFoundException e -> Response.status(Response.Status.NOT_FOUND)
                        .entity(new ResponseError(e.getMessage(), List.of()))
                        .build();

                case ConflictException e -> Response.status(Response.Status.CONFLICT)
                        .entity(new ResponseError(e.getMessage(), List.of()))
                        .build();

                default -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new ResponseError("Erro interno no servidor. Tente novamente mais tarde.", List.of()))
                        .build();
            };
        }
    }
}