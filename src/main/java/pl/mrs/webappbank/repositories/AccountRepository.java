package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.model.Client;
import pl.mrs.webappbank.model.accounts.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements IRepository<Account, String>{
    protected ArrayList<Account> listOfAccounts;

    public AccountRepository() {
        listOfAccounts = new ArrayList<>();
    }

    @Override
    public void add(Account element) {
        listOfAccounts.add(element);
    }

    @Override
    public void remove(Account account) {
        listOfAccounts.remove(account);
    }

    @Override
    public List<Account> findAll(){
        ArrayList<Account> clone = new ArrayList<Account>(listOfAccounts.size());
        for (Account item : listOfAccounts) {
            clone.add((Account) item.clone());
        }
        return clone;
    }

    @Override
    public int find(String identifier) {
        int i = 0;
        for(Account item : listOfAccounts){
            if(item.getAccountNumber().equals(identifier))
                return i;
            i++;
        }
        return -1;
    }
    public void transfer(String senderAccountNumber, String recipientAccountnumber, double amount){
            listOfAccounts.get(find(senderAccountNumber)).changeStateOfAccount(-amount);
            listOfAccounts.get(find(recipientAccountnumber)).changeStateOfAccount(amount);
    }
    public void changeState(String accountNumber, double amount){
        synchronized (this){
            listOfAccounts.get(find(accountNumber)).changeStateOfAccount(amount);
        }
    }

    @Override
    public String toString() {
        String output = "";
        for(Account a : listOfAccounts)
            output += a.toString() + "\n";
        return  output;
    }
}
