package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.exceptions.NonexistentAccountException;
import pl.mrs.webappbank.exceptions.NotEnoughMoneyException;
import pl.mrs.webappbank.model.Currency;
import pl.mrs.webappbank.model.accounts.SavingsType;
import pl.mrs.webappbank.modelv2.Client;

public interface IAccountManager {
    void registerCommonAccount(Client client);
    void registerCurrencyAccount(Client client, Currency currency);
    void registerSavingsAccount(Client client, SavingsType savingsType);
    void removeAccount(String accountNumber) throws NonexistentAccountException;
    void transferMoney(String  senderAccountNumber, String recipientAccountnumber, double amount) throws NonexistentAccountException, NotEnoughMoneyException;
    String getInfo(DataType dataType);
    void withdraw(String accountNumber, double amount) throws NotEnoughMoneyException;
    void payInto(String accountNumber, double amount);
}
