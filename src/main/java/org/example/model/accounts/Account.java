package org.example.model.accounts;

import org.example.model.Bank;
import org.example.model.Currency;

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
}
