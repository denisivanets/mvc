package views;

import controllers.Controller;

import javax.swing.*;
import java.awt.*;

public class SwingView implements View {
    private JFrame frame;
    private Controller controller;

    public SwingView(Controller controller){
        this.controller = controller;
    }

    @Override
    public void startView(){
        frame = new JFrame("shop");
        frame.setSize(700,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawWelcomePage();
        frame.setVisible(true);
    }
    @Override
    public void drawWelcomePage(){
        JPanel welcomePanel = new JPanel();
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
        welcomePanel.setVisible(true);
    }
    @Override
    public void drawAdminPage(){
        JPanel adminPanel = new JPanel();
        frame.setSize(700,600);
        adminPanel.setLayout(new BorderLayout());
        Box adminPanelBox = new Box(BoxLayout.Y_AXIS);
        frame.getContentPane().add(BorderLayout.CENTER,adminPanel);
        JPanel firstPanel = new JPanel();
        JButton backButton = new JButton("Back");
        backButton.addActionListener(
                (event) -> {
                    controller.backAction();
                    adminPanel.setVisible(false);
                }
        );
        Box box1 = new Box(BoxLayout.Y_AXIS);
        JLabel storageLabel = new JLabel("Storage");
        JList<String> storage = new JList<>(controller.provideAvailableProducts());
        JScrollPane scroll = new JScrollPane(storage);
        box1.add(storageLabel);
        box1.add(scroll);
        firstPanel.add(box1);
        Box box2 = new Box(BoxLayout.Y_AXIS);
        JButton addNewProductButton = new JButton("Add new product");
        box2.add(addNewProductButton);
        firstPanel.add(box2);
        JList<String> transactions = new JList<>(controller.getTransactionContent());
        JScrollPane scrollTrans = new JScrollPane(transactions);
        adminPanelBox.add(firstPanel);
        adminPanelBox.add(new JLabel("Transactions"));
        adminPanelBox.add(scrollTrans);
        adminPanel.add(adminPanelBox);
        adminPanel.add(BorderLayout.SOUTH,backButton);
        addNewProductButton.addActionListener(
                (event) -> {
                    adminPanel.setVisible(false);
                    controller.addNewItemAction();
                }
        );
        adminPanel.setVisible(true);
    }
    @Override
    public void drawRegisterPage(){
        JPanel registerPanel = new JPanel();
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
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());
        JPanel firstPanel = new JPanel();
        frame.getContentPane().add(BorderLayout.CENTER,userPanel);
        JList<String> productList = new JList<>(controller.provideAvailableProducts());
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(productList);
        Box box1 = new Box(BoxLayout.Y_AXIS);
        JLabel balance = new JLabel(controller.getContentForBalance());
        box1.add(new JLabel("Products"));
        box1.add(scroll);
        box1.add(balance);
        firstPanel.add(box1);
        userPanel.add(firstPanel);
        JButton addButton = new JButton("Add        ");
        JButton removeButton = new JButton("Remove");
        Box box2 = new Box(BoxLayout.Y_AXIS);
        JLabel totalSum = new JLabel(controller.getContentForTotalSum());
        box2.add(addButton);
        box2.add(removeButton);
        box2.add(totalSum);
        firstPanel.add(box2);
        Box box3 = new Box(BoxLayout.Y_AXIS);
        JList<String> basket = new JList<>(controller.provideUserBasket());
        basket.setFixedCellHeight(20);
        basket.setFixedCellWidth(200);
        JScrollPane scrollBasket = new JScrollPane(basket);
        box3.add(new JLabel("Basket"));
        box3.add(scrollBasket);
        JButton buyButton = new JButton("Buy all");
        box3.add(buyButton);
        firstPanel.add(box3);
        JButton backButton = new JButton("Back");
        userPanel.add(BorderLayout.SOUTH,backButton);
        userPanel.setVisible(true);
        addButton.addActionListener(
                (event) -> {
                    controller.setContentFromBasket(productList.getSelectedValue());
                    basket.setListData(controller.getContentForBasket());
                    totalSum.setText(controller.getContentForTotalSum());
                }
        );
        removeButton.addActionListener(
                (event) -> {
                    controller.removeContentFromBasket(basket.getSelectedValue());
                    basket.setListData(controller.getContentForBasket());
                    totalSum.setText(controller.getContentForTotalSum());
                }
        );
        backButton.addActionListener(
                (event) -> {
                    userPanel.setVisible(false);
                    controller.backAction();
                }
        );
        buyButton.addActionListener(
                (event) -> {
                    controller.buyAction();
                    basket.setListData(controller.getContentForBasket());
                    productList.setListData(controller.provideAvailableProducts());
                    balance.setText(controller.getContentForBalance());
                    totalSum.setText(controller.getContentForTotalSum());
                }
        );
    }
    @Override
    public void drawAddItemPage(){
        JPanel addItemPanel = new JPanel();
        frame.getContentPane().add(addItemPanel);
        JTextField prodName = new JTextField("Product name");
        JTextField prodCost = new JTextField("Cost");
        JTextField prodAmount = new JTextField("Amount");
        Box box = new Box(BoxLayout.Y_AXIS);
        box.add(prodName);
        box.add(prodCost);
        box.add(prodAmount);
        JButton addButton = new JButton("Add  ");
        JButton backButton = new JButton("Back");
        box.add(addButton);
        box.add(backButton);
        addItemPanel.add(box);
        backButton.addActionListener(
                (event) -> {
                    addItemPanel.setVisible(false);
                    controller.backFromAdd();
                }
        );
        addButton.addActionListener(
                (event) -> {
                    String name = prodName.getText();
                    String cost = prodCost.getText();
                    String amount = prodAmount.getText();
                    if (controller.addNewItem(name,cost,amount)) {
                        addItemPanel.setVisible(false);
                        controller.backFromAdd();
                    }
                }
        );
    }
}
