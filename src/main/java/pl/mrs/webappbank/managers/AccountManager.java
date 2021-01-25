package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.exceptions.NonexistentAccountException;
import pl.mrs.webappbank.exceptions.NotEnoughMoneyException;
import pl.mrs.webappbank.model.users.Client;
import pl.mrs.webappbank.model.accounts.Currency;
import pl.mrs.webappbank.model.Transfer;
import pl.mrs.webappbank.model.accounts.*;
import pl.mrs.webappbank.repositories.AccountRepository;
import pl.mrs.webappbank.repositories.TransferRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class AccountManager implements IAccountManager, Serializable {

    private AccountRepository accountRepository;
    private TransferRepository transferRepository;
    private boolean exampleAccounts;

    @Inject
    private ClientManager clientManager;

    public AccountManager() {
        exampleAccounts = false;
        accountRepository = new AccountRepository();
        transferRepository = new TransferRepository();

        //Sample data
        registerCommonAccount(clientManager.getAllClients().get(0));
        registerCommonAccount(clientManager.getAllClients().get(1));
        registerCurrencyAccount(clientManager.getAllClients().get(1),Currency.EUR);
        registerCommonAccount(clientManager.getAllClients().get(2));

        payInto(getAllAccounts().get(0).getAccountNumber(),9990);
        payInto(getAllAccounts().get(1).getAccountNumber(),90);
        payInto(getAllAccounts().get(2).getAccountNumber(),94990);
        payInto(getAllAccounts().get(2).getAccountNumber(),850);
    }

    public boolean isExampleAccounts() {
        return exampleAccounts;
    }

    public void setExampleAccounts(boolean exampleAccounts) {
        this.exampleAccounts = exampleAccounts;
    }

//    public AccountManager(AccountRepository accountRepository, TransferRepository transferRepository) {
//        this.accountRepository = accountRepository;
//        this.transferRepository = transferRepository;
//    }

    public void registerCommonAccount(Client client){
        Account newAccount = new CommonAccount(0.0);
        accountRepository.add(newAccount);
        client.addAccount(newAccount);
    }

    @Override
    public void registerCurrencyAccount(Client client, Currency currency) {
        Account newAccount = new CurrencyAccount(0.0, currency);
        accountRepository.add(newAccount);
        client.addAccount(newAccount);
    }

    @Override
    public void registerSavingsAccount(Client client, SavingsType savingsType) {
        Account newAccount = new SavingsAccount(0.0, savingsType);
        accountRepository.add(newAccount);
        client.addAccount(newAccount);
    }

    @Override
    public void removeAccount(Client client, Account account) throws NonexistentAccountException {
        if(accountRepository.find(account.getAccountNumber()) == -1)
            throw new NonexistentAccountException(account.getAccountNumber() + "Do not exist");
        synchronized (this) {
            client.deleteAccount(account);
            accountRepository.remove(account);
        }
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
