package oi.github.dev.jakki.socialfollia.domain.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("Operação inválida para esse usuário.");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
