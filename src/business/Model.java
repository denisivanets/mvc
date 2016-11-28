package business;

import inmemorydb.InMemoryDB;

import java.util.List;

public class Model {
    Client currentClient;
    List<Product> availableProducts;


    public AccountType checkLogin(String login,char[] password){
        Client client = FileUtils.findClientInFileByLogin(login);
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

    public List<Product> getProductList(){
        return null;//TODO:???????????????
    }


}
