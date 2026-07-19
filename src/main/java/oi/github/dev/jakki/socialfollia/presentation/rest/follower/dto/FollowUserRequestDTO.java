package oi.github.dev.jakki.socialfollia.presentation.rest.follower.dto;

import jakarta.validation.constraints.NotNull;

public record FollowUserRequestDTO(
        @NotNull
        Long followerId
) {}
