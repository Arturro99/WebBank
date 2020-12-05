package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.exceptions.NonexistentAccountException;
import pl.mrs.webappbank.exceptions.NotEnoughMoneyException;
import pl.mrs.webappbank.modelv2.Currency;
import pl.mrs.webappbank.modelv2.accounts.Account;
import pl.mrs.webappbank.modelv2.accounts.SavingsType;
import pl.mrs.webappbank.modelv2.Client;

public interface IAccountManager {
    void registerCommonAccount(Client client);
    void registerCurrencyAccount(Client client, Currency currency);
    void registerSavingsAccount(Client client, SavingsType savingsType);
    void removeAccount(Account account) throws NonexistentAccountException;
    void transferMoney(String  senderAccountNumber, String recipientAccountnumber, double amount) throws NonexistentAccountException, NotEnoughMoneyException;
    String getInfo(DataType dataType);
    void withdraw(String accountNumber, double amount) throws NotEnoughMoneyException;
    void payInto(String accountNumber, double amount);
}
