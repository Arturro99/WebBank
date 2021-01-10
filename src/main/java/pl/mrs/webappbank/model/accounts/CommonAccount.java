package pl.mrs.webappbank.model.accounts;

public class CommonAccount extends Account {
    public CommonAccount(double stateOfAccount) {
        super(stateOfAccount);
    }

    @Override
    public String toString() {
        return "Konto zwykle o numerze " + getAccountNumber() +
                " posiada na koncie " + getStateOfAccount() + "PLN";
    }

    @Override
    public Object clone() {
        CommonAccount clone = new CommonAccount(stateOfAccount);
        clone.accountNumber = this.accountNumber;
        return clone;
    }


}
