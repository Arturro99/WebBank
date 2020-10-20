package model.accounts;

import model.Currency;

public class CurrencyAccount extends Account {
    private Currency currency;
    public CurrencyAccount(long accountNumber, double stateOfAccount, Currency currency) {
        super(accountNumber, stateOfAccount);
        this.currency = currency;
    }

    public Currency getCurrency() { return currency; }

    @Override
    public String toString() {
        return "Konto walutowe o numerze " + getAccountNumber() +
                " posiada na koncie " + getStateOfAccount() +
                (getCurrency() == Currency.EUR ? " EUR" : " USD");
    }
}
