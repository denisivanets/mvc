package views;

import controllers.Controller;

import javax.swing.*;
import java.awt.*;

public class SwingView implements View {
    JFrame frame;
    Controller controller;
    JPanel welcomePanel;
    JPanel adminPanel;

    public SwingView(Controller controller){
        this.controller = controller;
    }

    @Override
    public void startView(){
        frame = new JFrame("shop");
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawWelcomePanel();
        frame.setVisible(true);
    }
    @Override
    public void drawWelcomePanel(){
        welcomePanel = new JPanel();
        frame.getContentPane().add(BorderLayout.CENTER,welcomePanel);
        JTextField login = new JTextField("Login",10);
        JPasswordField password = new JPasswordField(10);
        welcomePanel.add(BorderLayout.CENTER, login);
        welcomePanel.add(BorderLayout.CENTER, password);
        JButton confirmButton = new JButton("Confirm");
        welcomePanel.add(confirmButton);
        confirmButton.addActionListener((event) -> {
            welcomePanel.setVisible(false);
            controller.loginAction(login.getText(),password.getPassword());
        });
    }
    @Override
    public void drawAdminPanel(){
        adminPanel = new JPanel();
        frame.getContentPane().add(BorderLayout.CENTER,adminPanel);
        JTextField login = new JTextField("Password");
        adminPanel.add(BorderLayout.CENTER, login);
        adminPanel.add(new JButton("BTTTTTTTTN"));
        adminPanel.setVisible(true);
    }
}
