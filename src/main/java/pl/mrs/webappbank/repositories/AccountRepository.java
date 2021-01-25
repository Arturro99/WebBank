package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.model.accounts.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements IRepository<Account, String>{
    protected final ArrayList<Account> listOfAccounts;

    public AccountRepository() {
        listOfAccounts = new ArrayList<>();
    }

    @Override
    public void add(Account element) {
        element.setAccountNumber(generateNewAccountNumber());
        synchronized (listOfAccounts) {
            listOfAccounts.add(element);
        }
    }

    @Override
    public void remove(Account account) {
        synchronized (listOfAccounts) {
            listOfAccounts.remove(account);
        }
    }

    @Override
    public synchronized List<Account> findAll(){
        ArrayList<Account> clone = new ArrayList<Account>(listOfAccounts.size());
        for (Account item : listOfAccounts) {
            clone.add((Account) item.clone());
        }
        return clone;
    }

    @Override
    public synchronized int find(String identifier) {
        int i = 0;
        for(Account item : listOfAccounts){
            if(item.getAccountNumber().equals(identifier))
                return i;
            i++;
        }
        return -1;
    }
    public void transfer(String senderAccountNumber, String recipientAccountnumber, double amount){
        synchronized (listOfAccounts) {
            listOfAccounts.get(find(senderAccountNumber)).changeStateOfAccount(-amount);
            listOfAccounts.get(find(recipientAccountnumber)).changeStateOfAccount(amount);
        }
    }
    public void changeState(String accountNumber, double amount){
        synchronized (listOfAccounts){
            listOfAccounts.get(find(accountNumber)).changeStateOfAccount(amount);
        }
    }

    @Override
    public synchronized String toString() {
        StringBuilder output = new StringBuilder();
        for(Account a : listOfAccounts)
            output.append(a.toString()).append("\n");
        return output.toString();
    }
    private String generateNewAccountNumber(){
        String generatedLong = "";
        do {
            generatedLong = "";
            for(int i = 0 ; i < 26; i++)
                generatedLong += String.valueOf( (int) (Math.random() * 10));
        }while (this.find(generatedLong) >= 0);
        return generatedLong;
    }
}
