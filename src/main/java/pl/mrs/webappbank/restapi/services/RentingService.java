package pl.mrs.webappbank.restapi.services;

import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.managers.EventManager;
import pl.mrs.webappbank.managers.ResourceManager;
import pl.mrs.webappbank.model.events.Event;
import pl.mrs.webappbank.model.events.LoansLedger;
import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.resources.SafeBox;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("model.rent")
public class RentingService {

    @Inject
    EventManager eventManager;

    @Inject
    ClientManager clientManager;

    @Inject
    ResourceManager resourceManager;

    @Inject
    AccountManager accountManager;

    @GET
    @Path("/rentByClient/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Event> getAllByClient(@PathParam("uuid") String uuid) {
        return eventManager.getEventsByClientId(uuid);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Event> getAll() {
        return eventManager.getAll();
    }

    @GET
    @Path("{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Event get(@PathParam("uuid") String uuid) {
        return eventManager.getEventById(uuid);
    }

    @POST
    @Path("/rentBox/{clientUuid}/{boxUuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Event rentBox(@PathParam("clientUuid") String clientId, @PathParam("boxUuid")  String boxId) {
        return eventManager.rentBox((SafeBox)resourceManager.findById(boxId), clientManager.findById(clientId));
    }

    @POST
    @Path("/takeLoan/{clientUuid}/{accountUuid}/{loanUuid}")
    public void rentBox(@PathParam("clientUuid") String clientId, @PathParam("accountUuid") String accountId, @PathParam("loanUuid")  String loanId) {
        eventManager.takeLoan((Loan) resourceManager.findById(loanId), accountManager.findById(accountId), clientManager.findById(clientId));
    }

    @POST
    @Path("/returnBox/{eventUuid}")
    public void returnBox(@PathParam("eventUuid") String eventId) {
        eventManager.returnResource(eventManager.getEventById(eventId).getResource(), null);
    }

    @POST
    @Path("/payLoan/{eventUuid}")
    public void payLoan(@PathParam("eventUuid") String eventId) {
        eventManager.returnResource(eventManager.getEventById(eventId).getResource(), ((LoansLedger)(eventManager.getEventById(eventId))).getAccount());
    }
}
