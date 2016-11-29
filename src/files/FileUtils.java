package files;

import model.AccountType;
import model.Client;
import model.Product;
import model.Transaction;

import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static final String clientsPath = "src\\files\\clients.dat";
    public static final String transactionsPath = "src\\files\\transactions.dat";
    public static final String productsPath = "src\\files\\products.dat";

    public static void writeClientInFile(Client client){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(clientsPath,true))){
            bw.write(parseClientToString(client) + "\n");
        } catch(IOException ex){
            ex.getMessage();
            ex.printStackTrace();
        }
    }

    public static List<Client> readClientsFromFile(){
        List<Client> clients = new ArrayList<>();
        File file = new File(clientsPath);
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String oneClient;
            while((oneClient = br.readLine()) != null){
                clients.add(parseStringToClient(oneClient));
            }
        } catch(IOException ex){
            ex.getMessage();
            ex.printStackTrace();
        }
        return clients;
    }

    public static void writeTransactionIfFile(Transaction transaction){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(transactionsPath,true))){
            bw.write(parseTransactionToString(transaction));
        } catch(IOException ex){
            ex.getMessage();
            ex.printStackTrace();
        }
    }

    public static List<Transaction> readTransactionsFromFile(){
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(transactionsPath);
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String oneTransaction;
            while( (oneTransaction = br.readLine()) != null){
                transactions.add(parseStringToTransaction(oneTransaction));
            }
        } catch(IOException ex){
            ex.getMessage();
            ex.printStackTrace();
        }
        return transactions;
    }

    public static void writeProductInFile(Product product){
        File file = new File(clientsPath);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(productsPath,true))){
            bw.write(parseProductToString(product) + "\n");
        } catch(IOException ex){
            ex.getMessage();
            ex.printStackTrace();
        }
    }

    public static List<Product> readProductsFromFile(){
        List<Product> products = new ArrayList<>();
        File file = new File(productsPath);
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String oneProdcut;
            while( (oneProdcut = br.readLine()) != null){
                products.add(parseStringToProduct(oneProdcut));
            }
        } catch(IOException ex){
            ex.getMessage();
            ex.printStackTrace();
        }
        return products;
    }

    private static String parseClientToString(Client client){
        String name = client.getName();
        String surname = client.getSurname();
        String balance = Double.toString(client.getBalance());
        String phone = client.getPhone();
        String login = client.getLogin();
        String password = client.getPassword();
        String accountType = client.getAccountType()== AccountType.ADMIN?"a":"u";
        return String.format(
                "name:%s,srname:%s,blnc:%s,phone:%s,log:%s,pass:%s,type:%s",
                name,surname,balance,phone,login,password,accountType);
    }

    private static String parseTransactionToString(Transaction transaction){
        String login = transaction.getClientLogin();
        String product = transaction.getProductName();
        String amount = Integer.toString(transaction.getAmount());
        String date = transaction.getDate();
        return String.format("log:%s,prod:%s,amount:%s,date:%s",login,product,amount,date);
    }

    private static Transaction parseStringToTransaction(String transactionAsString){
        int LOGIN = 0;
        int PRODUCT = 1;
        int AMOUNT = 2;
        int DATE = 3;
        String[] oneTransaction = transactionAsString.split(",");
        String login = extractValue(oneTransaction[LOGIN]);
        String product = extractValue(oneTransaction[PRODUCT]);
        int amount = Integer.parseInt(extractValue(oneTransaction[AMOUNT]));
        String date = extractValue(oneTransaction[DATE]);
        Transaction transaction = new Transaction(login,product,amount);
        transaction.setDate(date);
        return transaction;
    }

    private static Client parseStringToClient(String clientAsString){
        int NAME = 0;
        int SURNAME = 1;
        int BALANCE = 2;
        int PHONE = 3;
        int LOGIN = 4;
        int PASSWORD = 5;
        int ACC_TYPE = 6;
        String[] oneClient = clientAsString.split(",");
        String name = extractValue(oneClient[NAME]);
        String surname = extractValue(oneClient[SURNAME]);
        double balance = Double.valueOf(extractValue(oneClient[BALANCE]));
        String phone = extractValue(oneClient[PHONE]);
        String login = extractValue(oneClient[LOGIN]);
        String pass = extractValue(oneClient[PASSWORD]);
        AccountType accType = "a".equals(extractValue(oneClient[ACC_TYPE]))?AccountType.ADMIN:AccountType.USER;
        Client client = new Client(accType);
        client.setName(name);
        client.setSurname(surname);
        client.setBalance(balance);
        client.setPhone(phone);
        client.setLogin(login);
        client.setPassword(pass);
        return client;
    }

    private static String parseProductToString(Product product){
        String name = product.getProductName();
        String amount = Integer.toString(product.getAmount());
        String cost = Double.toString(product.getCost());
        return String.format("name:%s,amount:%s,cost:%s",name,amount,cost);
    }

    private static Product parseStringToProduct(String productAsString){
        int NAME = 0;
        int AMOUNT = 1;
        int COST = 2;
        String[] oneProduct = productAsString.split(",");
        String name = extractValue(oneProduct[NAME]);
        int amount = Integer.parseInt(extractValue(oneProduct[AMOUNT]));
        double cost = Double.parseDouble(extractValue(oneProduct[COST]));
        return new Product(name,amount,cost);
    }

    private static String extractValue(String str){
        return str.substring(str.indexOf(":") + 1);
    }

    public static void clearFile(String path){
        File file = new File(path);
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
