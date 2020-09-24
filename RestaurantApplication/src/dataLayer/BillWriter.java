package dataLayer;

import businessLayer.MenuItem;
import businessLayer.Order;
import businessLayer.Restaurant;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.io.PrintWriter;

public class BillWriter {

    public static void generateBill(TableView table) {
        try {
            if (table.getSelectionModel().getSelectedItem() != null) {
                Restaurant restaurant = Restaurant.getInstance();
                Order order = (Order) table.getSelectionModel().getSelectedItem();

                PrintWriter writeTo = new PrintWriter("Bill_Table" + order.getTableNumber() + "_Order" + order.getOrderId());
                writeTo.println("Lambada Restaurant\nStr. Memorandumului nr. 28.\nCluj Napoca - 400114\nRomania\nPhn: 072079144X");
                writeTo.println("-----------------------------------");
                writeTo.println("Memo#: " + order.getOrderId() + "/" + order.getTableNumber() + "\t\t\t" + order.getDate());
                writeTo.println("-----------------------------------");
                writeTo.println("Sr\tProduct\t\t\tAmount");
                writeTo.println("-----------------------------------");
                int sr = 1;
                double totalPrice = 0;
                for (MenuItem i : restaurant.getOrdersMap().get(order)) {
                    writeTo.println(sr + "\t" + i.getProductName() + "\t\t\t" + i.getPrice());
                    totalPrice += i.getPrice();
                }
                writeTo.println("-----------------------------------");
                writeTo.println("Total: " + "\t\t\t\t" + totalPrice);
                writeTo.close();
                order.setBilled(true);
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setHeaderText("Bill generated!");
                error.show();

            }
        } catch (IOException e) {

        }
    }
}
