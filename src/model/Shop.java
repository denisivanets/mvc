package model;

import files.FileUtils;
import inmemorydb.InMemoryDB;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Shop {
    private Client currentClient;

    public List<Product> getAvailableProducts() {
        return availableProducts;
    }

    public void setAvailableProducts(List<Product> availableProducts) {
        this.availableProducts = availableProducts;
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }

    private List<Product> availableProducts;

    public Shop(){
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

    public boolean createUser(String name, String surname, String phone, String login, char[] password){
        if(!checkValidationData(name,surname,phone,login,password)){
            return false;
        }
        if(checkExistLogin(login)){
            return false;
        }
        Client client = createClient(name,surname,phone,login,password);
        currentClient = client;
        InMemoryDB.addClient(client);
        FileUtils.writeClientInFile(client);
        return true;
    }

    private boolean checkExistLogin(String login){
        if(getIndexByClientLogin(login) >= 0){
            return true;
        } else {
            return false;
        }
    }

    private boolean checkValidationData(String name, String surname, String phone, String login, char[] password){
        Pattern loginPattern = Pattern.compile("[a-zA-z1-9]*");
        Pattern pattern = Pattern.compile("[a-zA-z]*");
        Pattern patternPhone = Pattern.compile("[0-9]*");
        Matcher matcherName = pattern.matcher(name);
        if(!matcherName.matches()){
            return false;
        }
        Matcher matcherSurname = pattern.matcher(surname);
        if(!matcherSurname.matches()){
            return false;
        }
        Matcher matcherLogin = loginPattern.matcher(login);
        if(!matcherLogin.matches()){
            return false;
        }
        Matcher matcherPhone = patternPhone.matcher(phone);
        if(!matcherPhone.matches()){
            return false;
        }
        Matcher matcherPassword = loginPattern.matcher(parsePassword(password));
        if(!matcherPassword.matches()){
            return false;
        }
        return true;
    }

    private Client createClient(String name, String surname, String phone, String login, char[] password){
        Client client = new Client(AccountType.USER);
        client.setName(name);
        client.setBalance(100);
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
        return listToStringVector(InMemoryDB.getProductList());
    }

    private Client findClientByLogin(String login){
        Client client = null;
        List<String> logins = new ArrayList<>();
        InMemoryDB.getClientsList().forEach(
                (element) -> {
                    logins.add(element.getLogin());
                }
        );
        int index = logins.indexOf(login);
        if(index >= 0){
            return InMemoryDB.getClientsList().get(index);
        }
        return client;
    }

    public Vector<String> getCurrentUserBasket(){
        return listToStringVector(currentClient.getBasket());
    }

    public void addProductToCurrentUserBasket(String product){
        if(product == null) return;
        currentClient.addProductToBasket(findProductByStringName(product, InMemoryDB.getProductList()));
    }

    private Product findProductByStringName(String productAsString, List<Product> list) {
        if(list.size() == 0 || productAsString == null){
            return null;
        }
        Product product = null;
        int NAME = 0;
        String[] prodArr = productAsString.split(",");
        String requiredName = prodArr[NAME];
        boolean isMatch = false;
        int index = 0;
        while (!isMatch) {
            String name = list.get(index).getProductName();
            if (requiredName.equals(name)) {
                product = list.get(index);
                isMatch = true;
            }
            index++;
        }
        return product;
    }

    private <T> Vector<String> listToStringVector(List<T> list){
        Vector<String> vector = new Vector<>();
        list.forEach(
                (element) -> vector.add(element.toString())
        );
        return vector;
    }

    public void removeProductFromBasket(String productAsString){
        Product product = findProductByStringName(productAsString, currentClient.getBasket());
        if(product == null) { return; }
        currentClient.removeProduct(product);
    }

    public double getTotalSum(){
        double sum = 0;
        List<Product> basket = currentClient.getBasket();
        for (Product aBasket : basket) {
            sum = sum + aBasket.getCost();
        }
        return sum;
    }

    public double getBalance(){
        return currentClient.getBalance();
    }

    public void buyItemsFromCurrentUserBasket(){
        List<Product> basket = currentClient.getBasket();
        List<Product> storage = InMemoryDB.getProductList();
        buyAction(basket,storage);
        FileUtils.clearFile(FileUtils.productsPath);
        InMemoryDB.getProductList().forEach(
                (product) -> {
                    FileUtils.writeProductInFile(product);
                }
        );
        updateClientInDBs();
    }

    private int getIndexByProductName(String productName, List<Product> list){
        List<String> namesList = new ArrayList<>();
        list.forEach(
                (element) -> namesList.add(element.getProductName())
        );
        return namesList.indexOf(productName);
    }

    private void buyAction(List<Product> basketList,List<Product> storageList){
        Iterator<Product> iterator = basketList.iterator();
        while(iterator.hasNext()){
            Product basketItem = iterator.next();
            int index = getIndexByProductName(basketItem.getProductName(),storageList);
            if(index >= 0){
                Product storageItem = InMemoryDB.getProductList().get(index);
                if(storageItem.getAmount() > 0 && currentClient.getBalance() >= basketItem.getCost()){
                currentClient.setBalance(currentClient.getBalance() - basketItem.getCost());
                storageItem.setAmount(storageItem.getAmount() - 1);
                createTransaction(currentClient.getLogin(),basketItem.getProductName(),1,new Date().toString());
                iterator.remove();
                }
            }
        }
    }

    private void updateClientInDBs(){
        int index = getIndexByClientLogin(currentClient.getLogin());
        InMemoryDB.getClientsList().remove(index);
        InMemoryDB.getClientsList().add(index,currentClient);
        FileUtils.clearFile(FileUtils.clientsPath);
        InMemoryDB.getClientsList().forEach(
                (element) -> {
                    FileUtils.writeClientInFile(element);
                }
        );
    }

    private int getIndexByClientLogin(String login){
        List<String> logins = new ArrayList<>();
        InMemoryDB.getClientsList().forEach(
                (element) -> {
                    logins.add(element.getLogin());
                }
        );
        return logins.indexOf(login);
    }

    private void createTransaction(String login,String prodName, int amount, String date){
        Transaction transaction = new Transaction(login,prodName,amount);
        transaction.setDate(date);
        InMemoryDB.addTransaction(transaction);
        FileUtils.clearFile(FileUtils.transactionsPath);
        InMemoryDB.getTransactionsList().forEach(
                (element) -> FileUtils.writeTransactionIfFile(element)
        );
    }

    public Vector<Transaction> getTransactions(){
        Vector<Transaction> transactions = new Vector<>();
        InMemoryDB.getTransactionsList().forEach(
                (element) -> {
                    transactions.add(element);
                }
        );
        return transactions;
    }

    public boolean addNewItem(String name, String cost, String amount){
        double prodCost = 0;
        int prodAmount = 0;
        try {
            prodCost = Double.parseDouble(cost);
            prodAmount = Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            return false;
        }
        List<Product> storage = InMemoryDB.getProductList();
        int index = getIndexByProductName(name,storage);
        if(index >= 0){
            int newAmount = storage.get(index).getAmount() + prodAmount;
            storage.get(index).setAmount(newAmount);
            FileUtils.clearFile(FileUtils.productsPath);
            storage.forEach(
                    (element) -> FileUtils.writeProductInFile(element)
            );
        }
        else{
            InMemoryDB.getProductList().add(new Product(name,prodAmount,prodCost));
            FileUtils.writeProductInFile(new Product(name,prodAmount,prodCost));
        }
        return true;
    }

}
