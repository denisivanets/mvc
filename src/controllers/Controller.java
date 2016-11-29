package controllers;

import model.Shop;
import model.AccountType;
import model.Transaction;
import views.View;
import java.util.Vector;

public class Controller {
    private Shop shop;
    private View view;

    public void runView(){
        view.startView();
    }

    public Controller(Shop shop){
        this.shop = shop;
    }

    public void setView(View view){
        this.view = view;
    }

    public void loginAction(String login,char[] password){
        AccountType type = shop.checkLogin(login,password);
        switch(type){
            case ADMIN:
                view.drawAdminPage();
                break;
            case USER:
                view.drawUserPage();
                break;
            case NEW_USER:
                view.drawRegisterPage();
                break;
            default:
                view.drawWelcomePage();
        }
    }

    public void registerAction(String name, String surname, String phone, String login, char[] password){
        if(shop.createUser(name,surname,phone,login,password)){
            view.drawUserPage();
        }
        else{
            view.drawRegisterPage();
        }
    }

    public Vector<String> provideAvailableProducts(){
       return shop.getAvailableProductsToController();
    }

    public Vector<String> provideUserBasket(){
        return shop.getCurrentUserBasket();
    }

    public void setContentFromBasket(String content){
        shop.addProductToCurrentUserBasket(content);
    }

    public Vector<String> getContentForBasket(){
        Vector<String> result = new Vector<>();
        shop.getCurrentUserBasket().forEach(
                (element) -> {
                    result.add(element.substring(0,element.lastIndexOf(",")));
                }
        );
        return result;
    }

    public String getContentForTotalSum(){
        double sum = shop.getTotalSum();
        return String.format("Total : $%.2f",sum);
    }

    public void removeContentFromBasket(String content){
        shop.removeProductFromBasket(content);
    }

    public String getContentForBalance(){
        double balance = shop.getBalance();
        return String.format("Balance : $%.2f",balance);
    }

    public void backAction(){
        view.drawWelcomePage();
    }

    public void buyAction(){
        shop.buyItemsFromCurrentUserBasket();
    }

    public Vector<String> getTransactionContent(){
        Vector<String> transactionContent = new Vector<>();
        shop.getTransactions().forEach(
                (element) -> {
                    transactionContent.add(element.toString());
                }
        );
        return transactionContent;
    }
    public void addNewItemAction(){
        view.drawAddItemPage();
    }

    public void backFromAdd(){ view.drawAdminPage(); }

    public boolean addNewItem(String name, String cost,String amount){
        return shop.addNewItem(name,cost,amount);
    }
}
