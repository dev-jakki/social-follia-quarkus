package oi.github.dev.jakki.socialfollia.presentation.rest.exception;

import jakarta.annotation.Priority;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import oi.github.dev.jakki.socialfollia.domain.exception.ConflictException;
import oi.github.dev.jakki.socialfollia.domain.exception.ForbiddenException;
import oi.github.dev.jakki.socialfollia.domain.exception.NotFoundException;
import oi.github.dev.jakki.socialfollia.presentation.rest.exception.dto.ResponseError;
import oi.github.dev.jakki.socialfollia.presentation.rest.exception.dto.ValidationResponseError;

import java.util.List;

@Provider
@Priority(1)
public class GlobalExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return ValidationResponseError
                .createFromValidation(exception.getConstraintViolations())
                .withStatusCode(ValidationResponseError.UNPROCESSABLE_ENTITY_STATUS);
    }

    @Provider
    @Priority(2)
    public static class GeneralExceptionHandler implements ExceptionMapper<Exception> {
        @Override
        public Response toResponse(Exception exception) {
            return switch (exception) {
                case NotFoundException e -> Response.status(Response.Status.NOT_FOUND)
                        .entity(new ResponseError(
                                Response.Status.NOT_FOUND.getStatusCode(),
                                e.getMessage()
                        )).build();

                case ConflictException e -> Response.status(Response.Status.CONFLICT)
                        .entity(new ResponseError(
                                Response.Status.CONFLICT.getStatusCode(),
                                e.getMessage()
                        )).build();

                case ForbiddenException e -> Response.status(Response.Status.FORBIDDEN)
                        .entity(new ResponseError(
                                Response.Status.FORBIDDEN.getStatusCode(),
                                e.getMessage()
                        )).build();

                default -> {
                    ResponseError errorBody = new ResponseError(
                            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Erro interno no servidor. Tente novamente mais tarde.",
                            exception.getMessage()
                    );

                    yield Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(errorBody)
                            .build();
                }
            };
        }
    }
}