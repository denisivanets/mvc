package model;

public class Product {
    private String productName;
    private int amount;
    private double cost;

    public Product(String productName, int amount, double cost) {
        this.productName = productName;
        this.amount = amount;
        this.cost = cost;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString(){
        return String.format("%s,$%.2f,available:%d",productName,cost,amount);
    }
}
