package oi.github.dev.jakki.socialfollia.domain.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Registro ou informação não encontrada.");
    }

    public NotFoundException(String message) {
        super(message);
    }

}
