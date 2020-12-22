package pl.mrs.webappbank.model.accounts;

public class CurrencyAccount extends Account {
    private Currency currency;
    public CurrencyAccount(String accountNumber, double stateOfAccount, Currency currency) {
        super(accountNumber, stateOfAccount);
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
        return new CurrencyAccount(accountNumber, stateOfAccount,currency);
    }
}
