package controllers;

import business.Model;
import business.AccountType;
import business.Product;
import views.View;
import java.util.List;

public class Controller {
    Model model;
    View view;

    public void runView(){
        view.startView();
    }

    public Controller(Model model){
        this.model = model;
    }

    public void setView(View view){
        this.view = view;
    }

    public void loginAction(String login,char[] password){
        AccountType type = model.checkLogin(login,password);
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
        model.createUser(name,surname,phone,login,password);
        view.drawUserPage();
    }

    public List<String> provideAvailableProducts(){
       return model.getAvailableProducts();
    }

}
