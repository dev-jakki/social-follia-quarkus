package oi.github.dev.jakki.socialfollia.presentation.rest.exception.dto;

public record ResponseError(
        int status,
        String message,
        String error
) {
    public ResponseError(int status, String message) {
        this(status, message, null);
    }
}
