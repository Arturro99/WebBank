package pl.mrs.webappbank.modelv2.accounts;

import lombok.Data;
import pl.mrs.webappbank.modelv2.Currency;

@Data
public abstract class Account {

    protected final String accountNumber;
    protected double stateOfAccount;

    public Account(String accountNumber, double stateOfAccount) {
        this.accountNumber = accountNumber;
        this.stateOfAccount = stateOfAccount;
    }

    public void changeStateOfAccount(double amount) { this.stateOfAccount += amount; }

    @Override
    abstract public String toString();

    @Override
    public abstract Object clone();

    public Currency getCurrency(){
        return Currency.PLN;
    }
}
