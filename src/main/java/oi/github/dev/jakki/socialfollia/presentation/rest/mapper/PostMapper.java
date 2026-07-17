package oi.github.dev.jakki.socialfollia.presentation.rest.mapper;

import jakarta.inject.Inject;
import oi.github.dev.jakki.socialfollia.domain.model.Post;
import oi.github.dev.jakki.socialfollia.domain.repository.UserRepository;
import oi.github.dev.jakki.socialfollia.presentation.rest.dto.PostCreateRequestDTO;
import oi.github.dev.jakki.socialfollia.presentation.rest.dto.PostResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi", uses = UserMapper.class)
public abstract class PostMapper {

    @Inject
    protected UserRepository userRepository;

    @Mapping(target = "user", expression = "java( userRepository.findByIdOptional(userId).orElse(null) )")
    public abstract Post toEntity(PostCreateRequestDTO dto, Long userId);

    public abstract PostResponseDTO toDTO(Post entity);
}