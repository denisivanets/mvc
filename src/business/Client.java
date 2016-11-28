package business;


import java.util.ArrayList;
import java.util.List;

public class Client {
    private String name;
    private String surname;
    private double balance;
    private String phone;
    private String login;
    private String password;
    private AccountType accountType;
    private List<Product> basket;

    public Client(AccountType accountType){
        this.accountType = accountType;
        basket = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addProduct(Product product){
        basket.add(product);
    }
}
