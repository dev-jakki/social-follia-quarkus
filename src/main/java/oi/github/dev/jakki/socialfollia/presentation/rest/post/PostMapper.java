package oi.github.dev.jakki.socialfollia.presentation.rest.post;

import jakarta.inject.Inject;
import oi.github.dev.jakki.socialfollia.domain.model.Post;
import oi.github.dev.jakki.socialfollia.domain.repository.UserRepository;
import oi.github.dev.jakki.socialfollia.presentation.rest.post.dto.PostCreateRequestDTO;
import oi.github.dev.jakki.socialfollia.presentation.rest.post.dto.PostResponseDTO;
import oi.github.dev.jakki.socialfollia.presentation.rest.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi", uses = UserMapper.class)
public abstract class PostMapper {

    @Inject
    protected UserRepository userRepository;

    @Mapping(target = "id", ignore = true) // Ignora no mapeamento, pois é um campo de preenchimento automatico
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", expression = "java( userRepository.findByIdOptional(userId).orElse(null) )")
    public abstract Post toEntity(PostCreateRequestDTO dto, Long userId);

    public abstract PostResponseDTO toDTO(Post entity);
}