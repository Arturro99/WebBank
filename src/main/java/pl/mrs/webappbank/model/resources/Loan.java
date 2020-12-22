package pl.mrs.webappbank.model.resources;

import lombok.Data;

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
