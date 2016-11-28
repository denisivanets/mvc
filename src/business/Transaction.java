package business;

import java.util.Date;

public class Transaction {
    private String clientLogin;
    private String productName;
    private int amount;
    private String date;

    public Transaction(String clientLogin,String productName,int amount){
        this.clientLogin = clientLogin;
        this.productName = productName;
        this.amount = amount;
        date = new Date().toString();
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
