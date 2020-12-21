package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.modelv2.SafeBox;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SafeBoxRepository implements IRepository<SafeBox,UUID>{
    private List<SafeBox> boxes;
    public SafeBoxRepository() {
        boxes = new ArrayList<>();

        SafeBox box = new SafeBox("Duża Skrytka",true, 1, 1);
        box.setId(UUID.randomUUID());
        boxes.add(box);
        box = new SafeBox("Mała Skrytka",true, 2, 3);
        box.setId(UUID.randomUUID());
        boxes.add(box);
        box = new SafeBox("Średnia Skrytka",true, 1, 2);
        box.setId(UUID.randomUUID());
        boxes.add(box);
    }

    @Override
    public void add(SafeBox element) {
        element.setId(UUID.randomUUID());
        boxes.add(element);
    }

    @Override
    public void remove(SafeBox element) {
        boxes.remove(element);
    }

    @Override
    public List<SafeBox> findAll() {
        return new ArrayList<>(boxes);
    }

    @Override
    public int find(UUID identifier) {
        return boxes.indexOf(boxes.stream()
                .filter(l -> l.getId().equals(identifier))
                .findAny()
                .orElse(null));
    }
}
