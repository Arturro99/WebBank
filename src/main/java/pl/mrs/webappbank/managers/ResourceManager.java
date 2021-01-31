package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.resources.Resource;
import pl.mrs.webappbank.model.resources.SafeBox;
import pl.mrs.webappbank.model.users.Client;
import pl.mrs.webappbank.repositories.ResourceRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Named
public class ResourceManager {
    private final ResourceRepository resourceRepository;

    public ResourceManager() {
        this.resourceRepository = new ResourceRepository();
    }

    public void add(Resource resource) {
            resourceRepository.add(resource);

    }

    public void editResource(String id, Resource resource) {
        if (resource instanceof SafeBox) {
            ((SafeBox)resourceRepository.findAll().get(resourceRepository.find(UUID.fromString(id)))).setPosition(((SafeBox)resource).getPosition());
        }
        else {
            ((Loan)resourceRepository.findAll().get(resourceRepository.find(UUID.fromString(id)))).setValue(((Loan)resource).getValue());
        }
        resourceRepository.findAll().get(resourceRepository.find(UUID.fromString(id))).setDescription(resource.getDescription());
        resourceRepository.findAll().get(resourceRepository.find(UUID.fromString(id))).setAvailable(resource.isAvailable());
    }

    public void removeResource(Resource resource) {
        resourceRepository.remove(resource);
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public synchronized Resource findById(String id) {
        return resourceRepository.findAll().get(resourceRepository.find(UUID.fromString(id)));
    }
}
