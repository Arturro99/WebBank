package pl.mrs.webappbank.restapi.services;

import pl.mrs.webappbank.exceptions.NonexistentAccountException;
import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.model.accounts.Account;
import pl.mrs.webappbank.restapi.utils.EntityIdentitySignerVerifier;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("model.account")
public class AccountService {

    @Inject
    AccountManager accountManager;

    @Inject
    ClientManager clientManager;

    @GET
    @Path("{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@PathParam("uuid") String uuid) {
        Account account = accountManager.findById(uuid);
        return Response.ok()
                .entity(account)
                .tag(EntityIdentitySignerVerifier.calculateSignature(account))
                .build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Account> getAll() {
        return accountManager.getAllAccounts();
    }

    @POST
    @Path("{clientUuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void add(@PathParam("clientUuid") String clientId) {
        accountManager.registerCommonAccount(clientManager.findById(clientId));
    }

    @DELETE
    @Path("{clientUuid}/{accountUuid}")
    public void remove(@PathParam("clientUuid") String clientId, @PathParam("accountUuid") String accountId) throws NonexistentAccountException {
        accountManager.removeAccount(clientManager.findById(clientId), accountManager.findById(accountId));
    }
}

