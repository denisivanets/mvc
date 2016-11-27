package inmemorydb;
import business.Client;
import business.Transaction;

import java.util.*;

public class InMemoryDB {
    private static List<Client> clientsList;
    private static List<Transaction> transactionsList;
    static{
        clientsList = new ArrayList<>();
        transactionsList = new ArrayList<>();
    }

    public static List<Client> getClientsList() {
        return clientsList;
    }

    public static List<Transaction> getTransactionsList() {
        return transactionsList;
    }

    public void addClient(Client client){
        clientsList.add(client);
    }

    public void addTransaction(Transaction transaction){
        transactionsList.add(transaction);
    }
}
