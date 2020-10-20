package model.accounts;

import model.Bank;
import model.Currency;

public abstract class Account {

    private final long accountNumber;
    private double stateOfAccount;

    public Account(long accountNumber, double stateOfAccount) {
        this.accountNumber = accountNumber;
        this.stateOfAccount = stateOfAccount;
    }

    public long getAccountNumber() { return accountNumber; }
    public double getStateOfAccount() { return stateOfAccount; }

    public void addToStateOfAccount(double amount) { this.stateOfAccount += amount; }

    @Override
    abstract public String toString();
}
