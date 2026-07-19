package oi.github.dev.jakki.socialfollia.presentation.rest.follower;

import oi.github.dev.jakki.socialfollia.domain.model.Follower;
import oi.github.dev.jakki.socialfollia.presentation.rest.follower.dto.FollowerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface FollowerMapper {

    @Mapping(source = "follower.name", target = "name")
    @Mapping(source = "follower.id", target = "id")
    FollowerResponseDTO followerToFollowerResponseDTO(Follower follower);
}
