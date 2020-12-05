package pl.mrs.webappbank.modelv2;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
public class Loan {
    private UUID id;
    private String description;
    private int value;
    private boolean available;

    public Loan() {
        this.id = UUID.randomUUID();
    }

    public Loan(String description, int value, boolean available) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.value = value;
        this.available = available;
    }
}
