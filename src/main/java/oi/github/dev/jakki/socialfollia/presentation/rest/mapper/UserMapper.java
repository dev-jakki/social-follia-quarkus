package oi.github.dev.jakki.socialfollia.presentation.rest.mapper;

import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.presentation.rest.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    User toEntity(UserDTO dto);

    UserDTO toDTO(User user);
}
