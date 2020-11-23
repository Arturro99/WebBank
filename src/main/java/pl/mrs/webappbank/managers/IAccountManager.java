package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.exceptions.NonexistentAccountException;
import pl.mrs.webappbank.exceptions.NotEnoughMoneyException;
import pl.mrs.webappbank.model.Currency;
import pl.mrs.webappbank.model.accounts.SavingsType;

public interface IAccountManager {
    void registerCommonAccount(String personalID, String name, String surname, int age);
    void registerCurrencyAccount(String personalID, String name, String surname, int age, Currency currency);
    void registerSavingsAccount(String personalID, String name, String surname, int age, SavingsType savingsType);
    void removeAccount(String accountNumber) throws NonexistentAccountException;
    void transferMoney(String  senderAccountNumber, String recipientAccountnumber, double amount) throws NonexistentAccountException, NotEnoughMoneyException;
    String getInfo(DataType dataType);
    void withdraw(String accountNumber, double amount) throws NotEnoughMoneyException;
    void payInto(String accountNumber, double amount);
}
