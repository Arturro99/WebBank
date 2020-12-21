package pl.mrs.webappbank.modelv2;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
public class Loan extends Resource{
    private int value;

    public Loan() { }

    public Loan(String description, int value, boolean available) {
        this.description = description;
        this.value = value;
        this.available = available;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
