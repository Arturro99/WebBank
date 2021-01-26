package pl.mrs.webappbank.restapi.services;

import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.managers.ResourceManager;
import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.resources.Resource;
import pl.mrs.webappbank.model.resources.SafeBox;
import pl.mrs.webappbank.model.users.Client;
import pl.mrs.webappbank.restapi.utils.EntityIdentitySignerVerifier;

import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("model.client")
public class ClientService {

    @Inject
    ClientManager clientManager;

    @GET
    @Path("{login}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@PathParam("login") String login) {
        Client client = clientManager.findByLogin(login);
        return Response.ok()
                .entity(client)
                .tag(EntityIdentitySignerVerifier.calculateSignature(client))
                .build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Client> getAll() {
        return clientManager.getAllClients();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void add(Client client) {
        clientManager.addClient(client);
    }

    @POST
    @Path("{login}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("login") String login, @HeaderParam("If-Match") @NotNull @NotEmpty String tag, Client client) {
        clientManager.updateClient(login, client);
    }

    @DELETE
    @Path("{login}")
    public void remove(@PathParam("login") String login) {
        clientManager.removeClient(clientManager.findByLogin(login));
    }
}
