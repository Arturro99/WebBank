package pl.mrs.webappbank.restapi.services;

import pl.mrs.webappbank.managers.ResourceManager;
import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.resources.Resource;
import pl.mrs.webappbank.model.resources.SafeBox;
import pl.mrs.webappbank.restapi.filters.SignatureVerifierFilterBinding;
import pl.mrs.webappbank.restapi.utils.EntityIdentitySignerVerifier;
import pl.mrs.webappbank.restapi.utils.EntityIntegrationException;

import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("model.resource")
public class ResourceService {

    @Inject
    ResourceManager resourceManager;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@PathParam("id") String id) {
        Resource res = resourceManager.findById(id);
        return Response.ok()
                .entity(res)
                .tag(EntityIdentitySignerVerifier.calculateSignature(res))
                .build();
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

    @PUT
    @Path("/editLoan/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @SignatureVerifierFilterBinding
    public void editResource(@PathParam("uuid") String id, @HeaderParam("If-Match") @NotNull @NotEmpty String tag, Loan loan) throws Exception {
        if(!EntityIdentitySignerVerifier.verifyIntegration(tag,loan)) {
            throw EntityIntegrationException.integrityBroken(loan.toString());
        }
        resourceManager.editResource(id, loan);
    }

    @PUT
    @Path("/editBox/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    @SignatureVerifierFilterBinding
    public void editResource(@PathParam("uuid") String id, @HeaderParam("If-Match") @NotNull @NotEmpty String tag, SafeBox safeBox) throws Exception {
        if(!EntityIdentitySignerVerifier.verifyIntegration(tag,safeBox)) {
            throw EntityIntegrationException.integrityBroken(safeBox.toString());
        }
        resourceManager.editResource(id, safeBox);
    }

    @DELETE
    @Path("{id}")
    public void removeResource(@PathParam("id") String id) {
        resourceManager.removeResource(resourceManager.findById(id));
    }


}
