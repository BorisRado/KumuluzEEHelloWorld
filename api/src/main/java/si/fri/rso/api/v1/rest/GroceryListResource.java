package si.fri.rso.api.v1.rest;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.entities.GroceryList;
import si.fri.rso.entities.GroceryListUpdateHelper;
import si.fri.rso.services.GroceryListsBean;
import si.fri.rso.services.ItemListManagerBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users/{userId}/lists")
public class GroceryListResource {

    @Inject
    GroceryListsBean glb;

    @Inject
    ItemListManagerBean ilmb;

    @GET
    @Operation(summary = "Get all user lists", description = "Get all grocery lists belonging to all users")
    @APIResponses({
            @APIResponse(description = "List of all grocery lists", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GroceryList.class, type = SchemaType.ARRAY)))
        })
    public Response getAllUserLists(@PathParam(value = "userId") int userId) {
        List<GroceryList> lst = glb.getUserGroceryLists(userId);
        return Response.status(Response.Status.OK).
                entity(lst)
                .header("X-total-count", lst.size())
                .build();
    }

    @GET
    @Path("{groceryListId}")
    @Operation(summary = "Get grocery list", description = "Get the grocery list belonging to user. The ID of user and list are passed in URI")
    @APIResponses({
            @APIResponse(description = "Grocery list with given ID", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GroceryList.class, type = SchemaType.OBJECT)))
        })
    public Response getGroceryList(
            @PathParam(value = "userId") int userId,
            @PathParam(value = "groceryListId") int groceryListId) {
        GroceryList gl = glb.getGroceryList(userId, groceryListId);
        if (gl == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(gl.getItems()).build();
    }

    @POST
    @Operation(summary = "Create grocery list", description = "Creates a grocery lists and persists it to DB")
    @APIResponses({
            @APIResponse(description = "Created user", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = GroceryList.class, type = SchemaType.OBJECT))),
            @APIResponse(description = "Bad request", responseCode = "400")
    })
    public Response createGroceryList(
            @RequestBody(description = "List to be created", required = true,
                    content = @Content(schema = @Schema(implementation = GroceryList.class))) GroceryList gl) {
        if (gl == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        gl = glb.createGroceryList(gl);
        return Response.status(Response.Status.CREATED).entity(gl).build();
    }

    @DELETE
    @Path("{groceryListId}")
    @Operation(summary = "Deletes the list", description = "Deletes the list whose id is passed in the URI")
    @APIResponses({
            @APIResponse(description = "The list has successfully been deleted", responseCode = "204"),
            @APIResponse(description = "The list does not exist", responseCode = "404")
    })
    public Response deleteGroceryList(
            @PathParam(value = "userId") int userId,
            @PathParam(value = "groceryListId") int groceryListId) {
        boolean deleted = glb.deleteGroceryList(userId, groceryListId);
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PATCH
    @Path("{groceryListId}")
    @Operation(summary = "Patches the list", description = "Adds or removes the items that are listed in the request")
    @APIResponses({
            @APIResponse()
    })
    public Response patchGroceryList(
            @PathParam(value = "userId") int userId,
            @PathParam(value = "groceryListId") int listId,
            @RequestBody(description = "Object with the items to be added and removed", required = true,
                    content = @Content(schema = @Schema(implementation = GroceryListUpdateHelper.class))) GroceryListUpdateHelper gluh) {
        if (gluh.getAddList() == null && gluh.getDeleteList() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (gluh.getAddList() != null && gluh.getAddList().length > 0) {
            ilmb.addItemsToList(userId, listId, gluh.getAddList(), true);
        }
        if (gluh.getDeleteList() != null && gluh.getDeleteList().length > 0) {
            ilmb.addItemsToList(userId, listId, gluh.getDeleteList(), false);
        }
        return getGroceryList(userId, listId);
    }

}
