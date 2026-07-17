package oi.github.dev.jakki.socialfollia.presentation.rest.dto;

import java.time.LocalDateTime;

public record PostResponseDTO(
    String text,
    LocalDateTime createdAt
) { }
