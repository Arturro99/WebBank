package pl.mrs.webappbank.restapi.services;

import pl.mrs.webappbank.managers.ResourceManager;
import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.resources.Resource;
import pl.mrs.webappbank.model.resources.SafeBox;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("model.resource")
public class ResourceService {

    @Inject
    ResourceManager resourceManager;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Resource get(@PathParam("id") String id) {
        return resourceManager.findById(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Resource> getAll() {
        return resourceManager.getAllResources();
    }

    @POST
    @Path("/addLoan")
    @Consumes({MediaType.APPLICATION_JSON})
    public void add(Loan loan) {
        resourceManager.add(loan);
    }

    @POST
    @Path("/addBox")
    @Consumes({MediaType.APPLICATION_JSON})
    public void add(SafeBox box) {
        resourceManager.add(box);
    }

    @POST
    @Path("/editLoan/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void editResource(@PathParam("uuid") String id, Loan loan) {
        resourceManager.editResource(resourceManager.findById(id));
    }

    @POST
    @Path("/editBox/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void editResource(@PathParam("uuid") String id, SafeBox safeBox) {
        resourceManager.editResource(resourceManager.findById(id));
    }

    @DELETE
    @Path("{id}")
    public void removeResource(@PathParam("id") String id) {
        resourceManager.removeResource(resourceManager.findById(id));
    }


}
