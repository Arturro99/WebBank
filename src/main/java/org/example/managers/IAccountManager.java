package org.example.managers;

import org.example.exceptions.NonexistentAccountException;
import org.example.exceptions.NotEnoughMoneyException;
import org.example.model.Currency;
import org.example.model.accounts.SavingsType;

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
