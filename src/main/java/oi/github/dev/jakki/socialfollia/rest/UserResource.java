package oi.github.dev.jakki.socialfollia.rest;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import oi.github.dev.jakki.socialfollia.domain.model.User;
import oi.github.dev.jakki.socialfollia.rest.dto.CreateUserRequest;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @POST
    @Transactional // Sempre usar quando for fazer alguma ALTERAÇÃO no banco de dados (Visualizar/Listar informações não se encaixam)
    public Response create(CreateUserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());

        user.persist();

        return Response.ok(user).build();
    }

    @GET
    public Response listAll() {
        PanacheQuery<User> query = User.findAll();
        return Response.ok(query.list()).build();
    }

}
