package oi.github.dev.jakki.socialfollia.presentation.rest.user;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import oi.github.dev.jakki.socialfollia.domain.exception.NotFoundException;
import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.domain.service.UserService;
import oi.github.dev.jakki.socialfollia.presentation.rest.user.dto.UserCreateRequestDTO;

import java.util.Optional;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserResource {

    private final UserService service;
    private final UserMapper mapper;

    @POST
    public Response create(@Valid UserCreateRequestDTO dto) {
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
        User user = service
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        service.deleteById(user.getId());
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, UserCreateRequestDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

}
