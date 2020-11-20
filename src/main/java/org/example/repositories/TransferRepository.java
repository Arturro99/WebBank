package org.example.repositories;

import org.example.model.Client;
import org.example.model.Transfer;
import org.example.model.accounts.Account;

import java.util.ArrayList;
import java.util.UUID;

public class TransferRepository implements IRepository<Transfer, UUID>{
    private ArrayList<Transfer> transferHistory;

    public TransferRepository() {
        transferHistory = new ArrayList<>();
    }

    @Override
    public void add(Transfer element) {
        transferHistory.add(element);
    }

    @Override
    public void remove(int index) {
        transferHistory.remove(index);
    }

    @Override
    public ArrayList<Transfer> getList() {
        ArrayList<Transfer> clone = new ArrayList<Transfer>(transferHistory.size());
        for (Transfer item : transferHistory) {
            clone.add((Transfer) item.clone());
        }
        return clone;
    }

    @Override
    public int find(UUID identifier) {
        int i = 0;
        for(Transfer item : transferHistory){
            if(item.getId() == identifier)
                return i;
            i++;
        }
        return -1;
    }

    @Override
    public String toString() {
        String output = "";
        for(Transfer t : transferHistory)
            output += t.toString() + "\n";
        return  output;
    }
}
