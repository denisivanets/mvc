package views;

import controllers.Controller;

import javax.swing.*;
import java.awt.*;

public class SwingView implements View {
    JFrame frame;
    Controller controller;
    JPanel welcomePanel;
    JPanel adminPanel;
    JPanel registerPanel;
    JPanel userPanel;

    public SwingView(Controller controller){
        this.controller = controller;
    }

    @Override
    public void startView(){
        frame = new JFrame("shop");
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawWelcomePage();
        frame.setVisible(true);
    }
    @Override
    public void drawWelcomePage(){
        welcomePanel = new JPanel();
        frame.getContentPane().add(BorderLayout.CENTER,welcomePanel);
        JTextField login = new JTextField("Login",10);
        JPasswordField password = new JPasswordField(10);
        Box box = new Box(BoxLayout.Y_AXIS);
        box.add(login);
        box.add(password);
        JButton confirmButton = new JButton("Confirm");
        box.add(confirmButton);
        confirmButton.addActionListener((event) -> {
            welcomePanel.setVisible(false);
            controller.loginAction(login.getText(),password.getPassword());
        });
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener( (event) -> {
            welcomePanel.setVisible(false);
            drawRegisterPage();
        } );
        box.add(registerButton);
        welcomePanel.add(box);
    }
    @Override
    public void drawAdminPage(){
        adminPanel = new JPanel();
        frame.getContentPane().add(BorderLayout.CENTER,adminPanel);
        JTextArea area = new JTextArea("ADMIN PAGE");
        adminPanel.add(area);
        adminPanel.setVisible(true);
    }
    @Override
    public void drawRegisterPage(){
        registerPanel = new JPanel();
        frame.getContentPane().add(registerPanel);
        Box box = new Box(BoxLayout.Y_AXIS);
        registerPanel.add(BorderLayout.CENTER,box);
        JTextField name = new JTextField("Name",10);
        JTextField surname = new JTextField("Surname",10);
        JTextField phone = new JTextField("Phone",10);
        JTextField login = new JTextField("Login",10);
        JPasswordField password = new JPasswordField("Password",10);
        box.add(name);
        box.add(surname);
        box.add(phone);
        box.add(login);
        box.add(password);
        JButton sendButton = new JButton("Send");
        box.add(sendButton);
        registerPanel.setVisible(true);
        sendButton.addActionListener(
                (event) -> {
                    registerPanel.setVisible(false);
                    controller.registerAction(
                            name.getText(),
                            surname.getText(),
                            phone.getText(),
                            login.getText(),
                            password.getPassword()
                    );

        } );
    }
    @Override
    public void drawUserPage(){
        userPanel = new JPanel();
        frame.getContentPane().add(BorderLayout.CENTER,userPanel);
        JTextArea area = new JTextArea("USER PAGE");
        userPanel.add(area);
        userPanel.setVisible(true);
    }
}
