package pl.mrs.webappbank.model.accounts;

public class SavingsAccount extends Account{
    private SavingsType savingsType;

    public SavingsAccount(String accountNumber, double stateOfAccount, SavingsType type) {
        super(accountNumber, stateOfAccount);
        this.savingsType = type;
    }

    public SavingsType getSavingsType() { return savingsType; }

    @Override
    public String toString() {
        return "Konto oszczednosciowe o numerze " + getAccountNumber() +
             " posiada na koncie " + getStateOfAccount() + "PLN" +
                ", typ " + getSavingsType().toString();
    }

    @Override
    public Object clone() {
        return new SavingsAccount(accountNumber,stateOfAccount,savingsType);
    }
}
