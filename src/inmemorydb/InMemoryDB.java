package inmemorydb;
import model.Client;
import model.Product;
import model.Transaction;

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

    public static void setClientsList(List<Client> clientsList) {
        InMemoryDB.clientsList = clientsList;
    }

    public static void setProductList(List<Product> productList) {
        InMemoryDB.productList = productList;
    }

    public static void setTransactionsList(List<Transaction> transactionsList) {
        InMemoryDB.transactionsList = transactionsList;
    }

    public static void addClient(Client client){
        clientsList.add(client);
    }

    public static void addTransaction(Transaction transaction){
        transactionsList.add(transaction);
    }

    public static List<Product> getProductList() {
        return productList;
    }
}
