package repositories;

import model.accounts.Account;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AccountRepository {
    private ArrayList<Account> listOfAccounts;

    public void addToListOfAccounts(Account account) {
        listOfAccounts.add(account);
    }

    public Account findAccount(Account account) {
        return listOfAccounts.stream()
                .filter(acc -> acc.getAccountNumber() == account.getAccountNumber())
                .collect(Collectors.toList())
                .get(0);
    }
}
