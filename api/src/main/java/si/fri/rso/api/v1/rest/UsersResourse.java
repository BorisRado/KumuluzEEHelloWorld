package si.fri.rso.api.v1.rest;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.entities.User;
import si.fri.rso.services.UsersBean;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResourse {

    private Logger log = Logger.getLogger(this.getClass().getName());

    @Context
    private UriInfo uriInfo;
    @Inject
    UsersBean usersBean;

    @GET
    @Operation(summary = "Get all users", description = "Retrieves a list of all users registered on the page")
    @APIResponses({
            @APIResponse(description = "List of all users", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = User.class, type = SchemaType.ARRAY)))
        })
    public Response getAllUsers() {
        List<User> users = usersBean.getUsers();
        return Response.status(Response.Status.OK).entity(users).build();
    }

    @GET
    @Path("{userId}")
    @Operation(summary = "Get user", description = "Retrieves the user whose id is passed in the URI")
    @APIResponses({
            @APIResponse(description = "User JSON", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = User.class, type = SchemaType.OBJECT))),
            @APIResponse(description = "User not found", responseCode = "404")
        })
    public Response getUser(@PathParam(value = "userId") int userId) {
        User u = usersBean.getUser(userId);
        return u == null ?
                Response.status(Response.Status.NOT_FOUND).build() :
                Response.status(Response.Status.OK).entity(u).build();
    }

    @POST
    @Operation(summary = "Create user", description = "Creates a new user")
    @APIResponses({
            @APIResponse(description = "Created user", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = User.class, type = SchemaType.OBJECT))),
            @APIResponse(description = "Bad request - user missing fields", responseCode = "400")
        })
    public Response createUser(
            @RequestBody(description = "User to be created", required = true,
                    content = @Content(schema = @Schema(implementation = User.class))) User user) {
        log.info("" + user.getAge());
        if (user.getName() == null || user.getSurname() == null || user.getNickname() == null || user.getAge() < 10) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        user = usersBean.createtUser(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }
}
