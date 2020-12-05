package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.exceptions.NonexistentAccountException;
import pl.mrs.webappbank.exceptions.NotEnoughMoneyException;
import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.modelv2.Currency;
import pl.mrs.webappbank.modelv2.Transfer;
import pl.mrs.webappbank.modelv2.accounts.*;
import pl.mrs.webappbank.repositories.AccountRepository;
import pl.mrs.webappbank.repositories.TransferRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;


@ApplicationScoped
public class AccountManager implements IAccountManager{

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    public AccountManager() {
        accountRepository = new AccountRepository();
        transferRepository = new TransferRepository();
    }

    public AccountManager(AccountRepository accountRepository, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    public void registerCommonAccount(Client client){
        String newAccountNumber = generateNewAccountNumber();
        Account newAccount = new CommonAccount(newAccountNumber,0.0);
        accountRepository.add(newAccount);
        client.addAccount(newAccount);
    }

    @Override
    public void registerCurrencyAccount(Client client, Currency currency) {
        String newAccountNumber = generateNewAccountNumber();
        Account newAccount = new CurrencyAccount(newAccountNumber,0.0, currency);
        accountRepository.add(newAccount);
        client.addAccount(newAccount);
    }

    @Override
    public void registerSavingsAccount(Client client, SavingsType savingsType) {
        String newAccountNumber = generateNewAccountNumber();
        Account newAccount = new SavingsAccount(newAccountNumber,0.0, savingsType);
        accountRepository.add(newAccount);
        client.addAccount(newAccount);
    }

    @Override
    public void removeAccount(Account account) throws NonexistentAccountException {
        if(accountRepository.find(account.getAccountNumber()) == -1)
            throw new NonexistentAccountException(account.getAccountNumber() + "Do not exist");
        accountRepository.remove(account);
    }

    @Override
    public void transferMoney(String senderAccountNumber, String recipientAccountnumber, double amount) throws NonexistentAccountException, NotEnoughMoneyException {
        if(accountRepository.find(senderAccountNumber) == -1)
            throw new NonexistentAccountException(senderAccountNumber + "Do not exist");
        if( accountRepository.find(recipientAccountnumber) == -1)
            throw new NonexistentAccountException(recipientAccountnumber + "Do not exist");
        synchronized (this) {
            List<Account> currentState = accountRepository.findAll();
            int senderID = findAccountInList(currentState, senderAccountNumber);
            int recipientID = findAccountInList(currentState,recipientAccountnumber);
            if (currentState.get(senderID).getStateOfAccount() < amount)
                throw new NotEnoughMoneyException(senderAccountNumber + "Has less than " + amount);
            accountRepository.transfer(senderAccountNumber, recipientAccountnumber, amount);
            transferRepository.add(new Transfer(currentState.get(senderID),currentState.get(recipientID),amount));
        }
    }

    public String getInfo(){
        return getInfo(DataType.CLIENTS);
    }
    @Override
    public String getInfo(DataType dataType) {
        switch (dataType){
            case ACCOUNTS:
                return accountRepository.toString();
            case TRANSFERS:
                return transferRepository.toString();
        }
        return this.toString();
    }

    @Override
    public void withdraw(String accountNumber, double amount) throws NotEnoughMoneyException {
        List<Account> currentState = accountRepository.findAll();
        int accountID = findAccountInList(currentState,accountNumber);
        if (currentState.get(accountID).getStateOfAccount() < amount)
            throw new NotEnoughMoneyException(accountNumber + "Has less than " + amount);
        accountRepository.changeState(accountNumber,-amount);
    }

    @Override
    public void payInto(String accountNumber, double amount) {
        accountRepository.changeState(accountNumber,amount);
    }

    String generateNewAccountNumber(){
        String generatedLong = "";
        do {
            generatedLong = "";
            for(int i = 0 ; i < 26; i++)
                generatedLong += String.valueOf( (int) (Math.random() * 10));
        }while (accountRepository.find(generatedLong) >= 0);
        return generatedLong;
    }

    public int findAccountInList(List<Account>listOfAccounts,String identifier) {
        int i = 0;
        for(Account item : listOfAccounts){
            if(item.getAccountNumber().equals(identifier))
                return i;
            i++;
        }
        return -1;
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }
}
