package pl.mrs.webappbank.restapi.services;

import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.managers.EventManager;
import pl.mrs.webappbank.managers.ResourceManager;
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

    @Inject
    ResourceManager resourceManager;

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
    public void rentBox(@PathParam("clientUuid") String clientId, @PathParam("boxUuid")  String boxId) {
        eventManager.rentBox((SafeBox)resourceManager.findById(boxId), clientManager.findById(clientId));
    }

    @POST
    @Path("/returnBox/{uuid}")
    public void returnBox(@PathParam("uuid") String boxId) {
        eventManager.returnResource(resourceManager.findById(boxId), null);
    }
}
