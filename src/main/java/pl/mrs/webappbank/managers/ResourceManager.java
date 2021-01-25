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
        if (resourceRepository.findAll().stream()
                .noneMatch(x -> x.getId().equals(resource.getId()))) {
            if (resource instanceof SafeBox &&
                    resourceRepository.findAll().stream()
                            .filter(x -> x instanceof SafeBox)
                            .noneMatch(x -> ((SafeBox) x).getPosition().equals(((SafeBox) resource).getPosition()))) {
                resourceRepository.add(resource);
                return;
            }
            resourceRepository.add(resource);
        }
    }

    public void editResource(Resource resource) {
        if (resource instanceof SafeBox) {
            ((SafeBox)resourceRepository.findAll().get(resourceRepository.find(resource.getId()))).setPosition(((SafeBox)resource).getPosition());
        }
        else {
            ((Loan)resourceRepository.findAll().get(resourceRepository.find(resource.getId()))).setValue(((Loan)resource).getValue());
        }
        resourceRepository.findAll().get(resourceRepository.find(resource.getId())).setDescription(resource.getDescription());
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
