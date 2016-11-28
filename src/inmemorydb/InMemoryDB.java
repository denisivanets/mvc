package inmemorydb;
import business.Client;
import business.Product;
import business.Transaction;

import java.util.*;

public class InMemoryDB {
    private static List<Client> clientsList;
    private static List<Transaction> transactionsList;
    private static List<Product> productList;
    static{
        clientsList = new ArrayList<>();
        transactionsList = new ArrayList<>();
        productList = new ArrayList<>();
    }

    public static List<Client> getClientsList() {
        return clientsList;
    }

    public static List<Transaction> getTransactionsList() {
        return transactionsList;
    }

    public static void addClient(Client client){
        clientsList.add(client);
    }

    public static void addTransaction(Transaction transaction){
        transactionsList.add(transaction);
    }
}
