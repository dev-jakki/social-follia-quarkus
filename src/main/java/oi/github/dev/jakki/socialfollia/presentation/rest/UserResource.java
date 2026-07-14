package oi.github.dev.jakki.socialfollia.presentation.rest;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.domain.service.UserService;
import oi.github.dev.jakki.socialfollia.presentation.rest.dto.ResponseError;
import oi.github.dev.jakki.socialfollia.presentation.rest.dto.UserDTO;
import oi.github.dev.jakki.socialfollia.presentation.rest.mapper.UserMapper;

import java.util.Optional;
import java.util.Set;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserResource {

    private final UserService service;
    private final UserMapper mapper;
    private final Validator validator;

    @POST
    @Transactional
    public Response create(UserDTO dto) {

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            return ResponseError.createFromValidation(violations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

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
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Optional<User> userOptional = service.findById(id);

        if (userOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        service.deleteById(userOptional.get().getId());
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, UserDTO dto) {
        Optional<User> userOptional = service.findById(id);

        if (userOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        User user = userOptional.get();

        user.setName(dto.name());
        user.setAge(dto.age());

        // Não precisa, pois como tem a annotation @Transactional, ele commit tudo ao final do função

        return Response.ok(user).build();
    }

}
