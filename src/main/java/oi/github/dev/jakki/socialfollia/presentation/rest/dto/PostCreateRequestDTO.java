package oi.github.dev.jakki.socialfollia.presentation.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCreateRequestDTO(
    @NotBlank(message = "Campo obrigatório!")
    @Size(min = 10, max = 250, message = "Campo aceita de 10 a 250 caracteres!")
    String text
) { }
