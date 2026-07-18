package oi.github.dev.jakki.socialfollia.presentation.rest;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.domain.service.UserService;
import oi.github.dev.jakki.socialfollia.presentation.rest.dto.UserDTO;
import oi.github.dev.jakki.socialfollia.presentation.rest.mapper.UserMapper;

import java.util.Optional;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserResource {

    private final UserService service;
    private final UserMapper mapper;

    @POST
    public Response create(@Valid UserDTO dto) {
        User user = mapper.toEntity(dto);

        service.create(user);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(user)
                .build();
    }

    @GET
    public Response listAll() {
        PanacheQuery<User> query = service.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        Optional<User> userOptional = service.findById(id);

        if (userOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        service.deleteById(userOptional.get().getId());
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, UserDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

}
