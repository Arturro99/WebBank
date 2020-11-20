package org.example.model;

import org.example.managers.AccountManager;
import org.example.model.accounts.Account;
import org.example.repositories.TransferRepository;

import java.util.UUID;

public class Transfer {
    private Account sender, recipient;
    private final UUID id;
    //private long from, to;
    private double amount;

    public Transfer(Account sender, Account recipient, double amount) {
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }
    private Transfer(UUID id, Account sender, Account recipient, double amount){
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }
    @Override
    public Object clone() {
        return new Transfer(id, sender,recipient,amount);
    }

    public UUID getId() {
        return id;
    }
}
