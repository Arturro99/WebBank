package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.modelv2.Transfer;

import java.util.ArrayList;
import java.util.List;
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
    public void remove(Transfer transfer) {
        transferHistory.remove(transfer);
    }

    @Override
    public List<Transfer> findAll() {
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
        StringBuilder output = new StringBuilder();
        for(Transfer t : transferHistory)
            output.append(t.toString()).append("\n");
        return output.toString();
    }
}
