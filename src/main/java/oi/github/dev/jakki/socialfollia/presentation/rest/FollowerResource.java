package oi.github.dev.jakki.socialfollia.presentation.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import oi.github.dev.jakki.socialfollia.domain.exception.ConflictException;
import oi.github.dev.jakki.socialfollia.domain.exception.NotFoundException;
import oi.github.dev.jakki.socialfollia.domain.model.Follower;
import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.domain.service.FollowerService;
import oi.github.dev.jakki.socialfollia.domain.service.UserService;
import oi.github.dev.jakki.socialfollia.presentation.rest.mapper.FollowerMapper;

import java.util.Optional;

@Path("users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Data
public class FollowerResource {

    private final UserService userService;
    private final FollowerService followerService;
    private final FollowerMapper mapper;

    @PUT
    @Path("{followerId}")
    public Response follow(
            @PathParam("userId") Long userId,
            @PathParam("followerId") Long followerId
    ) {
        if (userId.equals(followerId)) {
            throw new ConflictException("Você não pode seguir a sí mesmo.");
        }

        Optional<User> userOptional = userService.findById(userId);
        Optional<User> followerOptional = userService.findById(followerId);

        if (userOptional.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        if (followerOptional.isEmpty()) {
            throw new NotFoundException("Usuário que deseja seguir não encontrado.");
        }

        Optional<Follower> isFollower = followerService.findByFollowerIdAndUserId(
                followerOptional.get().getId(),
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

    @GET
    public Response listFollowersPerUser(@PathParam("userId") Long userId) {
        return Response.status(Response.Status.OK).build();
    }

}
