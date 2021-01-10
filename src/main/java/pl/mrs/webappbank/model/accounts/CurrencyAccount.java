package pl.mrs.webappbank.model.accounts;

public class CurrencyAccount extends Account {
    private Currency currency;
    public CurrencyAccount(double stateOfAccount, Currency currency) {
        super(stateOfAccount);
        this.currency = currency;
    }

    public Currency getCurrency() { return currency; }

    @Override
    public String toString() {
        String output = "Konto walutowe o numerze " + getAccountNumber() +
                " posiada na koncie " + getStateOfAccount();
        switch (getCurrency()){
            case EUR:
            output+="EUR";
                break;
            case PLN:
                output+="PLN";
                break;
            case USD:
                output+="USD";
                break;
        }
       return output;
    }

    @Override
    public Object clone() {
        CurrencyAccount clone = new CurrencyAccount(stateOfAccount,currency);
        clone.accountNumber = this.accountNumber;
        return clone;
    }
}
