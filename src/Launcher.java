import files.FileUtils;
import model.*;
import controllers.Controller;
import views.SwingView;
import views.View;


public class Launcher {
    Shop shop;
    View view;
    Controller controller;

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.startApp();
    }
    private void startApp(){
        shop = new Shop();
        controller = new Controller(shop);
        view = new SwingView(controller);
        controller.setView(view);
        controller.runView();
    }
}
