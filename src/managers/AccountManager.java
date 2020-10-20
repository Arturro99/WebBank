package managers;

import model.Client;
import model.accounts.Account;
import repositories.AccountRepository;
import repositories.ClientRepository;

public class AccountManager {

    private AccountRepository accountRepository;
    private ClientRepository clientRepository;


    public AccountManager(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public void addAccount(Account account, Client client) {
        boolean clientExists = false;
        boolean accountExists = false;
        boolean accountOccupied = false;

        if(!clientRepository.getListOfClients().isEmpty()) {
           clientExists =  clientRepository.getListOfClients().stream()
                    .anyMatch(x -> x.equals(client));
        }

        clientRepository.getListOfClients().stream()

    }
}
