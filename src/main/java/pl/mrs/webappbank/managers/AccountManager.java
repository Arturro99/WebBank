package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.exceptions.NonexistentAccountException;
import pl.mrs.webappbank.exceptions.NotEnoughMoneyException;
import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.model.Currency;
import pl.mrs.webappbank.model.Transfer;
import pl.mrs.webappbank.model.accounts.*;
import pl.mrs.webappbank.repositories.AccountRepository;
import pl.mrs.webappbank.repositories.ClientRepository;
import pl.mrs.webappbank.repositories.IRepository;
import pl.mrs.webappbank.repositories.TransferRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@ApplicationScoped
public class AccountManager implements IAccountManager{

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final TransferRepository transferRepository;

    public AccountManager() {
        accountRepository = new AccountRepository();
        clientRepository = new ClientRepository();
        transferRepository = new TransferRepository();
    }

    public AccountManager(AccountRepository accountRepository, ClientRepository clientRepository, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.transferRepository = transferRepository;
    }

    private String accountInit(Client client) {
        if (clientRepository.find(client.getPid()) == -1)
            clientRepository.add(client);
        return generateNewAccountNumber();
    }

    public void registerCommonAccount(Client client){
        String newAccountNumber = accountInit(client);
        Account newAccount = new CommonAccount(newAccountNumber,0.0);
        accountRepository.add(newAccount);
        connectClientAccount(client.getPid(),newAccount);
    }

    @Override
    public void registerCurrencyAccount(Client client, Currency currency) {
        String newAccountNumber = accountInit(client);
        Account newAccount = new CurrencyAccount(newAccountNumber,0.0, currency);
        accountRepository.add(newAccount);
        connectClientAccount(client.getPid(),newAccount);
    }

    @Override
    public void registerSavingsAccount(Client client, SavingsType savingsType) {
        String newAccountNumber = accountInit(client);
        Account newAccount = new SavingsAccount(newAccountNumber,0.0, savingsType);
        accountRepository.add(newAccount);
        connectClientAccount(client.getPid(),newAccount);
    }

    @Override
    public void removeAccount(String accountNumber) throws NonexistentAccountException {
        if(accountRepository.find(accountNumber) == -1)
            throw new NonexistentAccountException(accountNumber + "Do not exist");
        accountRepository.remove(accountRepository.find(accountNumber));
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
            case CLIENTS:
                return clientRepository.toString();
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

    private void connectClientAccount(String personalID, Account newAccount) {
        clientRepository.assignAccount(personalID,newAccount);
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
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }
}
