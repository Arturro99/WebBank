package pl.mrs.webappbank.model.accounts;

import lombok.Data;
import pl.mrs.webappbank.model.SignableEntity;

import javax.json.bind.annotation.JsonbTransient;
import java.util.UUID;

@Data
public abstract class Account implements SignableEntity {

    private UUID uuid;
    protected String accountNumber;

    protected double stateOfAccount;
    public Account(double stateOfAccount) {
        this.accountNumber = "0000";
        this.stateOfAccount = stateOfAccount;
    }

    public void changeStateOfAccount(double amount) {
        this.stateOfAccount += amount;
    }

    @Override
    abstract public String toString();

    @Override
    public abstract Object clone();

    public Currency getCurrency(){
        return Currency.PLN;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    @JsonbTransient
    public String getSignablePayload() {
        if(null == uuid)
            return "";
        return uuid.toString();
    }
}
