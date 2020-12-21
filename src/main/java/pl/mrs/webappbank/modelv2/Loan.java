package pl.mrs.webappbank.modelv2;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
public class Loan extends Resource{
    private int value;

    public Loan() {
        super(null,true);}

    public Loan(String description, int value, boolean available) {
        super(description,available);
        this.value= value;
    }


    @Override
    public String toString() {
        return description + " (" + value + ") ";
    }
}
