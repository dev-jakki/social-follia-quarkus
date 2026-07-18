package oi.github.dev.jakki.socialfollia.domain.exception;

public class ConflictException extends RuntimeException{

    public ConflictException() {
        super("Conflito ao realizar essa ação.");
    }

    public ConflictException(String message) {
        super(message);
    }

}
