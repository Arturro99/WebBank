package pl.mrs.webappbank.model.accounts;

import lombok.Data;

@Data
public abstract class Account {

    protected String accountNumber;

    protected double stateOfAccount;
    public Account(double stateOfAccount) {
        this.accountNumber = "0000";
        this.stateOfAccount = stateOfAccount;
    }

    public void changeStateOfAccount(double amount) {
        this.stateOfAccount += amount;
    }

    @Override
    abstract public String toString();

    @Override
    public abstract Object clone();

    public Currency getCurrency(){
        return Currency.PLN;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
