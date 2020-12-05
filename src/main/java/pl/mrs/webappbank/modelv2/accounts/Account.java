package pl.mrs.webappbank.modelv2.accounts;

import pl.mrs.webappbank.modelv2.Currency;

public abstract class Account {

    protected final String accountNumber;
    protected double stateOfAccount;

    public Account(String accountNumber, double stateOfAccount) {
        this.accountNumber = accountNumber;
        this.stateOfAccount = stateOfAccount;
    }

    public String getAccountNumber() { return accountNumber; }
    public double getStateOfAccount() { return stateOfAccount; }

    public void changeStateOfAccount(double amount) { this.stateOfAccount += amount; }

    @Override
    abstract public String toString();

    @Override
    public abstract Object clone();

    public Currency getCurrency(){
        return Currency.PLN;
    }
}
