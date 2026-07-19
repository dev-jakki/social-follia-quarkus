package oi.github.dev.jakki.socialfollia.presentation.rest.follower.dto;

import java.util.List;

public record FollowersPerUserResponseDTO(
        Integer followersCount,
        List<FollowerResponseDTO> content
) { }
