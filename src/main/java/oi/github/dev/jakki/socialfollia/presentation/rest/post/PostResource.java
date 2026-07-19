package oi.github.dev.jakki.socialfollia.presentation.rest.post;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import oi.github.dev.jakki.socialfollia.domain.exception.ForbiddenException;
import oi.github.dev.jakki.socialfollia.domain.exception.NotFoundException;
import oi.github.dev.jakki.socialfollia.domain.model.Post;
import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.domain.service.FollowerService;
import oi.github.dev.jakki.socialfollia.domain.service.PostService;
import oi.github.dev.jakki.socialfollia.domain.service.UserService;
import oi.github.dev.jakki.socialfollia.presentation.rest.post.dto.PostCreateRequestDTO;

import java.util.Optional;
import java.util.stream.Collectors;

@Path("users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class PostResource {

    private final PostMapper mapper;
    private final PostService postService;
    private final UserService userService;
    private final FollowerService followerService;

    @POST
    public Response create(@PathParam("userId") Long userId, @Valid PostCreateRequestDTO dto) {
        Post post = mapper.toEntity(dto, userId);

        postService.create(post);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .build();
    }

    @GET
    public Response listAll(
            @PathParam("userId") Long userId,
            @HeaderParam("followerId") Long followerId
    ) {
        User user = userService
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        User follower = userService
                .findById(followerId)
                .orElseThrow(() -> new NotFoundException("Usuário logado Inválido."));

        followerService
                .findByFollowerIdAndUserId(follower.getId(), user.getId())
                .orElseThrow(() -> new ForbiddenException("Você não pode visualizar postagens de quem não segue."));

        PanacheQuery<Post> query = postService.find(user);

        var posts = query
                .list()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return Response.ok(posts).build();
    }

}

