package org.example.model.accounts;

public class CommonAccount extends Account {
    public CommonAccount(String accountNumber, double stateOfAccount) {
        super(accountNumber, stateOfAccount);
    }

    @Override
    public String toString() {
        return "Konto zwykle o numerze " + getAccountNumber() +
                " posiada na koncie " + getStateOfAccount() + "PLN";
    }

    @Override
    public Object clone() {
        return new CommonAccount(accountNumber,stateOfAccount);
    }


}
