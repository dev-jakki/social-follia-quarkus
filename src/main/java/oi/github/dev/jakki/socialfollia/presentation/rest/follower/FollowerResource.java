package oi.github.dev.jakki.socialfollia.presentation.rest.follower;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor; // Trocado @Data por @RequiredArgsConstructor
import oi.github.dev.jakki.socialfollia.domain.exception.ConflictException;
import oi.github.dev.jakki.socialfollia.domain.exception.NotFoundException;
import oi.github.dev.jakki.socialfollia.domain.model.Follower;
import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.domain.service.FollowerService;
import oi.github.dev.jakki.socialfollia.domain.service.UserService;
import oi.github.dev.jakki.socialfollia.presentation.rest.follower.dto.FollowerResponseDTO;
import oi.github.dev.jakki.socialfollia.presentation.rest.follower.dto.FollowersPerUserResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor // Construtor para injeção de dependência dos atributos final
public class FollowerResource {

    private final UserService userService;
    private final FollowerService followerService;
    private final FollowerMapper mapper;

    // Um record simples para transportar os dois usuários validados
    private record ValidatedRelationship(User user, User follower) {}

    private ValidatedRelationship validateAndGetUsers(Long userId, Long followerId) {
        if (userId.equals(followerId)) {
            throw new ConflictException("Você não pode seguir a si mesmo.");
        }

        User user = userService
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        User follower = userService
                .findById(followerId)
                .orElseThrow(() -> new NotFoundException("Usuário que deseja seguir/unfollow não encontrado."));

        return new ValidatedRelationship(user, follower);
    }

    @PUT
    @Path("{followerId}")
    public Response follow(
            @PathParam("userId") Long userId,
            @PathParam("followerId") Long followerId
    ) {
        ValidatedRelationship relationship = validateAndGetUsers(userId, followerId);

        Optional<Follower> isFollower = followerService.findByFollowerIdAndUserId(
                relationship.follower().getId(),
                relationship.user().getId()
        );

        if (isFollower.isPresent()) {
            return Response.status(Response.Status.OK).build();
        }

        Follower follower = new Follower();
        follower.setFollower(relationship.follower());
        follower.setUser(relationship.user());

        followerService.followUser(follower);

        return Response.status(Response.Status.OK).build();
    }

    @GET
    public Response listFollowersPerUser(@PathParam("userId") Long userId) {
        userService.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        List<Follower> listFollowers = followerService.findByUserId(userId);

        List<FollowerResponseDTO> list = listFollowers
                .stream()
                .map(mapper::followerToFollowerResponseDTO)
                .collect(Collectors.toList());

        FollowersPerUserResponseDTO responseObject = new FollowersPerUserResponseDTO(
                listFollowers.size(),
                list
        );

        return Response.status(Response.Status.OK).entity(responseObject).build();
    }

    @DELETE
    @Path("{followerId}")
    public Response unfollow(
            @PathParam("userId") Long userId,
            @PathParam("followerId") Long followerId
    ) {
        ValidatedRelationship relationship = validateAndGetUsers(userId, followerId);

        Optional<Follower> isFollower = followerService.findByFollowerIdAndUserId(
                relationship.follower().getId(),
                relationship.user().getId()
        );

        isFollower.ifPresent(followerService::unfollowUser);

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}