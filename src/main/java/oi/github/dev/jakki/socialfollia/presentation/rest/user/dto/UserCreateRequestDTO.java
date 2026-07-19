package oi.github.dev.jakki.socialfollia.presentation.rest.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequestDTO (
        @NotBlank(message = "Field is required")
        String name,

        @NotNull(message = "Field is required")
        Integer age
) { }