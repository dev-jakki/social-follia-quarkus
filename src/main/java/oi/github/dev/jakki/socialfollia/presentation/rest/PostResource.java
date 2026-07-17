package oi.github.dev.jakki.socialfollia.presentation.rest;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import oi.github.dev.jakki.socialfollia.domain.model.Post;
import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.domain.service.PostService;
import oi.github.dev.jakki.socialfollia.domain.service.UserService;
import oi.github.dev.jakki.socialfollia.presentation.rest.dto.PostCreateRequestDTO;
import oi.github.dev.jakki.socialfollia.presentation.rest.mapper.PostMapper;

import java.util.Optional;
import java.util.stream.Collectors;

@Path("users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Data
public class PostResource {

    private final PostMapper mapper;
    private final PostService postService;
    private final UserService userService;

    @POST
    public Response create(@PathParam("userId") Long userId, @Valid PostCreateRequestDTO dto) {
        Post post = mapper.toEntity(dto, userId);

        postService.create(post);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .build();
    }

    @GET
    public Response listAll(@PathParam("userId") Long userId) {
        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        PanacheQuery<Post> query = postService.find(userOptional.get());

        var posts = query
                .list()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return Response.ok(posts).build();
    }

}

