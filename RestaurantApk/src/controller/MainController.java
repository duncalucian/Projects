package controller;

import dataLayer.RestaurantSerializator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private static Scene chef;

    public static void setChef(Scene chf) {
        chef = chf;
    }


    public void toAdministrator(ActionEvent event) throws IOException {
        Parent newView = FXMLLoader.load(getClass().getResource("/view/administratorScene.fxml"));    //incarcam fxml ul ce corespunde administratorului
        Scene administratorView = new Scene(newView, 450, 350);             //creez o scena din fxml-ul incarcat anterior
        administratorView.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(administratorView);                                         //setam scena in fereastra
        window.show();
    }

    public void toWaiter(ActionEvent event) throws IOException {
        Parent newView = FXMLLoader.load(getClass().getResource("/view/waiterScene.fxml"));
        Scene waiterView = new Scene(newView, 370, 310);
        waiterView.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(waiterView);
        window.show();
    }

    public void toChef(ActionEvent event) {                                      //nu se incarca fxml-ul chef-ului pentru ca a fost setat deja la pornire
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();      //pentru a se incarca de fiecare data aceeasi instanta in acest fel nu se
        window.setScene(chef);                                                             //pierde Observer-ul setat pe Restaurant
        window.show();
    }

    public void serializator() {     //executa serializarea
        RestaurantSerializator.serializator();
    }
}
