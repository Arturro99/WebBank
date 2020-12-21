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

    public Loan() { this.available = true; }

    public Loan(String description, int value, boolean available) {
        this.description = description;
        this.value = value;
        this.available = available;
    }


    @Override
    public String toString() {
        return description + " (" + value + ") ";
    }
}
