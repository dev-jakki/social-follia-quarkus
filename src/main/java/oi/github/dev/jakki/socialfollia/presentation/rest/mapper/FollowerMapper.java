package oi.github.dev.jakki.socialfollia.presentation.rest.mapper;

import oi.github.dev.jakki.socialfollia.domain.model.Follower;
import oi.github.dev.jakki.socialfollia.presentation.rest.dto.FollowUserRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface FollowerMapper {

    Follower toEntity(FollowUserRequestDTO dto);
}
