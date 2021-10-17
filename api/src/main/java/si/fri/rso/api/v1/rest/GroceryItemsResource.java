package si.fri.rso.api.v1.rest;

import si.fri.rso.entities.GroceryItem;
import si.fri.rso.services.GroceryItemsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("items")
public class GroceryItemsResource {

    @Inject
    GroceryItemsBean gib;

    @GET
    public Response getGroceryItems(){
        return Response.status(Response.Status.OK)
                .entity(gib.getGroceryItems()).build();
    }

    @GET
    @Path(value = "{id}")
    public Response getGroceryItem(@PathParam(value = "id") int id) {
        GroceryItem gi = gib.getGroceryItem(id);
        if (gi == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.OK).entity(gi).build();
        }
    }

    @POST
    public Response createGroceryItem(GroceryItem gi) {
        if (gi.getItemName() == null || gi.getItemDescription() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            return Response.status(Response.Status.OK).entity(gib.createGroceryItem(gi)).build();
        }
    }

    @DELETE
    @Path(value = "{id}")
    public Response deleteGroceryItem(@PathParam("id") int id) {
        if (gib.deleteGroceryItem(id) == false) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @PATCH
    @Path(value = "{id}")
    public Response updateGroceryItem(GroceryItem gi, @PathParam("id") int id) {
        gi = gib.updateGroceryItem(gi, id);
        if (gi == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.OK).entity(gi).build();
        }
    }

}
