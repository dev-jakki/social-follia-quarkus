package oi.github.dev.jakki.socialfollia.presentation.rest.mapper;

import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.presentation.rest.dto.CreateUserRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    User toEntity(CreateUserRequestDTO dto);

    CreateUserRequestDTO toDTO(User user);
}
