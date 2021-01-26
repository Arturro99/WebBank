package pl.mrs.webappbank.restapi.services;

import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.managers.EventManager;
import pl.mrs.webappbank.model.events.Event;
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
    public List<Event> get(@PathParam("uuid") String uuid) {
        return eventManager.getE(uuid);
    }

    @POST
    @Path("/rentBox/{clientUuid}/{boxUuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void rentBox(@PathParam("clientUuid") String clientId, @PathParam("boxUuid")  String boxId) {
        eventManager.rentBox(boxId, clientManager.findById(clientId));
    }

    @POST
    @Path("/returnBox/{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void returnBox(@PathParam("uuid") String boxId, SafeBox safeBox) {
        eventManager.returnResource(safeBox, null);
    }
}
