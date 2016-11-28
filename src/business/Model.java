package business;

import files.FileUtils;
import inmemorydb.InMemoryDB;

import java.util.*;

public class Model {
    Client currentClient;
    List<Product> availableProducts;

    public Model(){
        availableProducts = FileUtils.readProductsFromFile();
        InMemoryDB.setProductList(FileUtils.readProductsFromFile());
        InMemoryDB.setClientsList(FileUtils.readClientsFromFile());
        InMemoryDB.setTransactionsList(FileUtils.readTransactionsFromFile());
    }


    public AccountType checkLogin(String login,char[] password){
        Client client = findClientByLogin(login);
        if(client != null){
            if(parsePassword(password).equals(client.getPassword())){
                currentClient = client;
                return client.getAccountType();
            }
            else{
                return AccountType.UNKNOWN;
            }
        } else{
            return AccountType.NEW_USER;
        }
    }

    public void createUser(String name, String surname, String phone, String login, char[] password){
        Client client = createClient(name,surname,phone,login,password);
        addClientToAllDBs(client);
        currentClient = client;
    }

    private Client createClient(String name, String surname, String phone, String login, char[] password){
        Client client = new Client(AccountType.USER);
        client.setName(name);
        client.setBalance(5700);
        client.setSurname(surname);
        client.setPhone(phone);
        client.setLogin(login);
        client.setPassword(parsePassword(password));
        return client;
    }

    private String parsePassword(char[] password){
        String result = "";
        for(char oneChar : password){
            result += oneChar;
        }
        return result;
    }

    private void addClientToAllDBs(Client client){
        InMemoryDB.addClient(client);
        FileUtils.writeClientInFile(client);
    }

    public Vector<String> getAvailableProductsToController(){
        Vector<String> productsAsStringList = new Vector<>();
        InMemoryDB.getProductList().forEach(
                (product) -> productsAsStringList.add(product.toString())
        );
        return productsAsStringList;
    }

    private Client findClientByLogin(String login){
        List<Client> allClients = InMemoryDB.getClientsList();
        Client client = null;
        boolean breakCondition = false;
        int i = 0;
        while(!breakCondition){
            client = allClients.get(i);
            if(login.equals(client.getLogin())){
                breakCondition = true;
            }
            i++;
        }//while
        return client;
    }

    public Vector<String> getCurrentUserBasket(){
        Vector<String> currentUserBasket = new Vector<>();
        currentClient.getBasket().forEach(
                (product) -> currentUserBasket.add(product.toString())
        );
        return currentUserBasket;
    }

}
