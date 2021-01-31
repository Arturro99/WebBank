package pl.mrs.webappbank.restapi.services;

import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.managers.ResourceManager;
import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.resources.Resource;
import pl.mrs.webappbank.model.resources.SafeBox;
import pl.mrs.webappbank.model.users.Client;
import pl.mrs.webappbank.model.users.Person;
import pl.mrs.webappbank.restapi.filters.SignatureVerifierFilterBinding;
import pl.mrs.webappbank.restapi.seurity.CustomIdentityStore;
import pl.mrs.webappbank.restapi.seurity.CustomJWTAuthenticationMechanism;
import pl.mrs.webappbank.restapi.utils.EntityIdentitySignerVerifier;
import pl.mrs.webappbank.restapi.utils.EntityIntegrationException;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("model.client")
public class ClientService {

    @Inject
    ClientManager clientManager;

//    @Inject
//    CustomIdentityStore customIdentityStore;
//
//    @Inject
//    SecurityContext securityContext;


    @GET
    @Path("{login}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@PathParam("login") String login) {
        Person person = clientManager.findByLogin(login);
        return Response.ok()
                .entity(person)
                .tag(EntityIdentitySignerVerifier.calculateSignature(person))
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

    @PUT
    @Path("{login}")
    @Consumes({MediaType.APPLICATION_JSON})
    @SignatureVerifierFilterBinding
    public void edit(@PathParam("login") String login, @HeaderParam("If-Match") @NotNull @NotEmpty String tag, Client client) throws Exception {
        if(!EntityIdentitySignerVerifier.verifyIntegration(tag,client)) {
            throw EntityIntegrationException.integrityBroken(client.toString());
        }
        clientManager.updateClient(login, client);
    }

    @DELETE
    @Path("{login}")
    public void remove(@PathParam("login") String login) {
        clientManager.removeClient((Person) clientManager.findByLogin(login));
    }
    @GET
    @Path("whoami")
    @Produces({MediaType.APPLICATION_JSON})
    public Response whoAmI(@Context SecurityContext securityContext){
        Person person = clientManager.findByLogin(securityContext.getUserPrincipal().getName());
        return Response.ok()
                .entity(person)
                .tag(EntityIdentitySignerVerifier.calculateSignature(person))
                .build();
    }
}
