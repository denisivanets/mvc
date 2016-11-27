import business.*;
import controllers.Controller;
import views.SwingView;
import views.View;


public class Launcher {
    Model model;
    View view;
    Controller controller;

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.startApp();
    }
    private void startApp(){
        model = new Model();
        controller = new Controller(model);
        view = new SwingView(controller);
        controller.setView(view);
        controller.runView();
//        view.drawRegisterPage();
    }
}
