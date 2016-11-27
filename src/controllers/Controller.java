package controllers;

import business.Model;
import business.AccountType;
import views.View;

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
        if(type == AccountType.ADMIN){
            view.drawAdminPanel();
        } else if(type == AccountType.USER) {
            //user actions
        }
        else{
            view.drawWelcomePanel();
        }
    }
}
