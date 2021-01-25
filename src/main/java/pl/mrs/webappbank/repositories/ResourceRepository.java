package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.resources.Position;
import pl.mrs.webappbank.model.resources.Resource;
import pl.mrs.webappbank.model.resources.SafeBox;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResourceRepository implements IRepository<Resource, UUID> {
    private final List<Resource> resources;

    public ResourceRepository() {
        resources = new ArrayList<>();

        Loan loan1 = new Loan("Minimalna pożyczka", 100, true);
        Loan loan2 = new Loan("Mała pożyczka", 1000, true);
        Loan loan3 = new Loan("Duża pożyczka", 10000, true);
        Loan loan4 = new Loan("Wielki kredyt", 100000, true);
        loan1.setId(UUID.randomUUID());
        loan2.setId(UUID.randomUUID());
        loan3.setId(UUID.randomUUID());
        loan4.setId(UUID.randomUUID());
        resources.add(loan1);
        resources.add(loan2);
        resources.add(loan3);
        resources.add(loan4);

        SafeBox box1 = new SafeBox("Duża Skrytka",true, new Position(1, 1));
        SafeBox box2 = new SafeBox("Mała Skrytka",true, new Position(2, 3));
        SafeBox box3 = new SafeBox("Średnia Skrytka",true, new Position(1, 2));
        box1.setId(UUID.randomUUID());
        box2.setId(UUID.randomUUID());
        box3.setId(UUID.randomUUID());
        resources.add(box1);
        resources.add(box2);
        resources.add(box3);
    }

    @Override
    public void add(Resource element) {
        synchronized (resources) {
            element.setId(UUID.randomUUID());
            if (resources.stream()
                    .noneMatch(x -> x.getId().equals(element.getId()))) {
                if (element instanceof SafeBox &&
                        resources.stream()
                                .filter(x -> x instanceof SafeBox)
                                .noneMatch(
                                        x -> ((SafeBox) x).getPosition().equals(((SafeBox) element).getPosition()))) {
                    resources.add(element);
                    return;
                }
                resources.add(element);
            } else {
                throw RepositoryException.NotFound(element.toString());
            }
        }
    }

    @Override
    public void remove(Resource element) {
        synchronized (resources) {
            resources.remove(element);
        }
    }

    @Override
    public List<Resource> findAll() {
        return new ArrayList<>(resources);
    }

    @Override
    public int find(UUID identifier) {
        return resources.indexOf(resources.stream()
                .filter(r -> r.getId().equals(identifier))
                .findAny()
                .orElseThrow(() -> RepositoryException.NotFound(identifier.toString())));
    }
}
