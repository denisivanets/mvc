package business;

import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FileUtils {
    private static String clientsPath = "src\\files\\clients.dat";
    private static String transactionsPath = "src\\files\\transactions.dat";

    public static void writeClientInFile(Client client){
        File file = new File(clientsPath);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(clientsPath,true))){
            bw.write(parseClientToString(client) + "\n");
        } catch(IOException ex){
            ex.getMessage();
            ex.printStackTrace();
        }
    }

    public static void writeTransactionIfFile(Transaction transaction){
        File file = new File(transactionsPath);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(transactionsPath,true))){
            bw.write(parseTransactionToString(transaction));
        } catch(IOException ex){
            ex.getMessage();
            ex.printStackTrace();
        }
    }

    public static List<Client> readClientsFromFile(){
        List<Client> clients = new ArrayList<>();
        File file = new File(clientsPath);
        try(BufferedReader br = new BufferedReader(new FileReader(clientsPath))){
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

    public static Client findClientInFileByLogin(String login){
        List<Client> clients = readClientsFromFile();
        Iterator iter = clients.iterator();
        while(iter.hasNext()){
            Client someClient = (Client) iter.next();
            if(login.equals(someClient.getLogin())){
                return someClient;
            }
        }
        return null;
    }

    private static String parseClientToString(Client client){
        String name = client.getName();
        String surname = client.getSurname();
        String balance = Double.toString(client.getBalance());
        String phone = client.getPhone();
        String login = client.getLogin();
        String password = client.getPassword();
        String accountType = client.getAccountType()==AccountType.ADMIN?"a":"u";
        String result = String.format(
                "name:%s,srname:%s,blnc:%s,phone:%s,log:%s,pass:%s,type:%s",
                name,surname,balance,phone,login,password,accountType);
        return result;
    }

    private static String parseTransactionToString(Transaction transaction){
        String login = transaction.getClientLogin();
        String product = transaction.getProductName();
        String amount = Integer.toString(transaction.getAmount());
        String date = transaction.getDate().toString();
        String result = String.format("log:%s,prod:%s,amount:%s,date:%s",login,product,amount,date);
        return result;
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

    private static String extractValue(String str){
        return str.substring(str.indexOf(":" + 1));
    }


}