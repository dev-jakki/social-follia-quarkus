package oi.github.dev.jakki.socialfollia.presentation.rest;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import oi.github.dev.jakki.socialfollia.domain.exception.ConflictException;
import oi.github.dev.jakki.socialfollia.domain.exception.NotFoundException;
import oi.github.dev.jakki.socialfollia.domain.model.Follower;
import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.domain.service.FollowerService;
import oi.github.dev.jakki.socialfollia.domain.service.UserService;
import oi.github.dev.jakki.socialfollia.presentation.rest.dto.FollowUserRequestDTO;
import oi.github.dev.jakki.socialfollia.presentation.rest.mapper.FollowerMapper;

import java.util.Optional;

@Slf4j
@Path("users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Data
public class FollowerResource {

    private final UserService userService;
    private final FollowerService followerService;
    private final FollowerMapper mapper;

    @PUT
    public Response follow(
            @PathParam("userId") Long userId,
            @Valid FollowUserRequestDTO dto
    ) {
        if (userId.equals(dto.followerId())) {
            throw new ConflictException("Você não pode seguir a sí mesmo.");
        }

        Optional<User> userOptional = userService.findById(userId);
        Optional<User> followerOptional = userService.findById(dto.followerId());

        if (userOptional.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        if (followerOptional.isEmpty()) {
            throw new NotFoundException("Usuário que deseja seguir não encontrado.");
        }

        Optional<Follower> isFollower = followerService.findByFollowerIdAndUserId(
                dto.followerId(),
                userOptional.get().getId()
        );

        if (isFollower.isPresent()) {
            return Response.status(Response.Status.OK).build();
        }

        Follower follower = new Follower();
        follower.setFollower(followerOptional.get());
        follower.setUser(userOptional.get());

        followerService.followerUser(follower);

        return Response.status(Response.Status.OK).build();
    }

}
