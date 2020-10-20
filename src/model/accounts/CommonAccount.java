package model.accounts;

public class CommonAccount extends Account {
    public CommonAccount(long accountNumber, double stateOfAccount) {
        super(accountNumber, stateOfAccount);
    }

    @Override
    public String toString() {
        return "Konto zwykle o numerze " + getAccountNumber() +
                " posiada na koncie " + getStateOfAccount() + "PLN";
    }


}
