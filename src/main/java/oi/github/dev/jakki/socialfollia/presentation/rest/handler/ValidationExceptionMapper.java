package oi.github.dev.jakki.socialfollia.presentation.rest.handler;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import oi.github.dev.jakki.socialfollia.presentation.rest.dto.ResponseError;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // O metodo exception.getConstraintViolations() retorna exatamente Set<ConstraintViolation<?>>
        // Agora o compilador do Java aceita sem nenhum warning ou erro!
        return ResponseError
                .createFromValidation(exception.getConstraintViolations())
                .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS); // Retorna HTTP 422
    }
}