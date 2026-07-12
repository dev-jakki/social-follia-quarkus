package oi.github.dev.jakki.socialfollia.rest;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @POST
    public Response create() {
        return Response.ok().build();
    }

}
