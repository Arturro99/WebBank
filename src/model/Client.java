package model;

import exceptions.NonexistentAccountException;
import exceptions.NotEnoughMoneyException;
import model.accounts.Account;

import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class Client {
    private final String name;
    private final String surname;
    private final int age;
    ArrayList<Account> listOfAccounts;

    public Client(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    void addMoney(double amount, Account account) throws NonexistentAccountException {
        boolean accountExists = false;
        for(Account acc : listOfAccounts) {
            if (acc.getAccountNumber() == account.getAccountNumber()) {
                accountExists = true;
                account.addToStateOfAccount(amount);
            }
        }

        if(accountExists) {
            System.out.println("Przelano srodki na konto: " +
                    account.getAccountNumber() +
                    "Jego obecny stan: " +
                    account.getStateOfAccount());
        }
        else
            throw new NonexistentAccountException("Podane konto nie istnieje!");
    }

    void withdrawMoney(double amount, Account account) throws NonexistentAccountException, NotEnoughMoneyException {
        boolean accountExists = false;
        boolean enoughMoney = false;

        for(Account acc : listOfAccounts) {
            if(acc.getAccountNumber() == account.getAccountNumber())
            {
                accountExists = true;
                if(account.getStateOfAccount() - amount >= 0) {
                    enoughMoney = true;
                    account.addToStateOfAccount(-amount);
                }
            }
        }

        if(accountExists && enoughMoney) {
            System.out.println("Udalo sie wyplacic srodki z konta: " +
                    account.getAccountNumber() +
                    "Jego obecny stan: " +
                    account.getStateOfAccount());
        }
        if(!accountExists)
            throw new NonexistentAccountException("Podane konto nie istnieje!");
        if(!enoughMoney)
            throw new NotEnoughMoneyException("Zbyt mało pieniędzy na koncie!");
    }

    void addAccount(Account account) {
        listOfAccounts.add(account);
    }

    double getPersonalStateOfAccount(Account account) throws NonexistentAccountException {
        return OptionalDouble.of(listOfAccounts.stream()
                .filter(acc -> acc.getAccountNumber() == account.getAccountNumber())
                .collect(Collectors.toList())
                .get(0)
                .getStateOfAccount())
                .orElseThrow(() -> new NonexistentAccountException("Podane konto nie istnieje!"));
    }

    long getPersonalAccountNumber(Account account) throws NonexistentAccountException {
        return OptionalLong.of(listOfAccounts.stream()
                .filter(acc -> acc.getAccountNumber() == account.getAccountNumber())
                .collect(Collectors.toList())
                .get(0)
                .getAccountNumber())
                .orElseThrow(() -> new NonexistentAccountException("Podane konto nie istnieje!"));
    }


    public String getName() { return name; }
    public String getSurname() { return surname; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Imie i nazwisko klienta: ")
                .append(getName())
                .append(" ")
                .append(getSurname())
                .append("\nWiek: ")
                .append(getAge());

        for (Account acc : listOfAccounts)
            builder.append("\n")
                    .append(acc.toString());

        return builder.toString();
    }
}
