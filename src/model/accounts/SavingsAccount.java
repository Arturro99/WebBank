package model.accounts;

public class SavingsAccount extends Account{
    private SavingsType savingsType;

    public SavingsAccount(long accountNumber, double stateOfAccount, SavingsType type) {
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
}
