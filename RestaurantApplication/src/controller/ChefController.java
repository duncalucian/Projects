package controller;

import businessLayer.MenuItem;
import businessLayer.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChefController implements PropertyChangeListener {
    @FXML
    private TextArea orderArea;
    Scene mainView;
    Thread thread;

    @FXML
    void initialize() {
        thread = new Thread() {
            public void run() {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/mainScene.fxml"));
                    mainView = new Scene(root, 450, 303);
                    mainView.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                } catch (IOException e) {
                }
            }
        };
        thread.start();
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Order order = (Order) evt.getNewValue();
        HashMap<Order, ArrayList<MenuItem>> ordersMap = (HashMap<Order, ArrayList<MenuItem>>) evt.getOldValue();
        orderArea.appendText("The order with id " + order.getOrderId() + " for table " + order.getTableNumber() + " was received!\n");
        orderArea.appendText("The order with id " + order.getOrderId() + " for table " + order.getTableNumber() + " is ready!\n Ordered items: \n");
        for(MenuItem i: ordersMap.get(order)) {
            orderArea.appendText(i.getProductName() + " ");
        }
        orderArea.appendText("\n\n");
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
