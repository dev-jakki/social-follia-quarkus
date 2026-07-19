package oi.github.dev.jakki.socialfollia.presentation.rest.user;

import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.presentation.rest.user.dto.UserCreateRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserCreateRequestDTO dto);
}
