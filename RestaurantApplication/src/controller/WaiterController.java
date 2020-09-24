package controller;

import businessLayer.MenuItem;
import businessLayer.Order;
import businessLayer.Restaurant;
import dataLayer.BillWriter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class WaiterController {
    @FXML
    private GridPane orderGrid;
    @FXML
    private TableView table;
    @FXML
    private TextField tableNumber;
    Scene mainView;
    Thread thread;

    @FXML
    void initialize() {
        Restaurant restaurant = Restaurant.getInstance();
        ArrayList<MenuItem> menu = restaurant.getMenu();       //lista cu toate produsele existente in restaurant
        int i = 0, j = 0;
        orderGrid.getChildren().clear();                 //initial eliminam toti copiii GridPane-ului pentru a nu aparea suprapuneri
        for (MenuItem item : menu) {
            CheckBox c = new CheckBox(item.getProductName());       //iteram pe toate produsele si cream un checkBox pentru fiecare
            orderGrid.add(c, i, j);                                 //adaugam checkBox-ul in gridPane (in cazul nostru in interfata incap maxim 7x4 produse)
            if (++j == 7) {
                j = 0;
                i++;
            }
        }

        thread = new Thread() {
            public void run() {      //folosit pentru a elimina 1/2 secunde de lag atunci cand doream sa ma intorc la fereastra princcipala
                try {                           //pentru a nu mai face load atunci cand apasam pe butonul home voi face load automat la inceput prin acest thread
                    Parent root = FXMLLoader.load(getClass().getResource("/view/mainScene.fxml"));
                    mainView = new Scene(root, 450, 303);
                    mainView.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                } catch (IOException e) {
                }
            }
        };
        thread.start();
    }

    @FXML
    public void createOrder() {
        Restaurant restaurant = Restaurant.getInstance();
        restaurant.createOrder(orderGrid, tableNumber);
    }

    @FXML
    void generateBill() {
        BillWriter.generateBill(table);
        loadAllItems();
    }

    @FXML
    void generateTotalPrice() {
        Order order = (Order) table.getSelectionModel().getSelectedItem();
        double totalPrice = 0;
        for (MenuItem i : Restaurant.getInstance().getOrdersMap().get(order)) {
            totalPrice += i.getPrice();
        }
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.setHeaderText("Order value: " + totalPrice);
        error.show();
    }

    @FXML
    void loadAllItems() {
        TableColumn nameColumn = new TableColumn("Order id");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        nameColumn.setPrefWidth(70);
        TableColumn tableNumber = new TableColumn("Table");
        tableNumber.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        tableNumber.setPrefWidth(80);
        TableColumn priceColumn = new TableColumn("Date");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        priceColumn.setPrefWidth(120);
        TableColumn billed = new TableColumn("Order closed");
        billed.setCellValueFactory(new PropertyValueFactory<>("billed"));
        billed.setPrefWidth(110);
        table.getColumns().clear();
        table.getItems().clear();
        table.getColumns().addAll(nameColumn, tableNumber, priceColumn, billed);
        table.setItems(FXCollections.observableArrayList(Restaurant.getInstance().getOrdersMap().keySet()));
    }

    @FXML
    public void toHome(ActionEvent event) {
        try {
            thread.join();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainView);
            window.show();
        } catch (InterruptedException e) {
        }
    }
}
