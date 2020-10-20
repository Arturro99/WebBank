package model;

import managers.AccountManager;
import repositories.TransferRepository;

public class Transfer {
    private long from, to;
    private double amount;

    public Transfer(long from, long to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public void transfer(AccountManager accountManager, Bank bank,
                         long from, long to, double amount, TransferRepository repository) {

    }

    public long getFrom() { return from; }
    public long getTo() { return to; }
    public double getAmount() { return amount; }
}
