package controller;

import businessLayer.BaseProduct;
import businessLayer.CompositeProduct;
import businessLayer.MenuItem;
import businessLayer.Restaurant;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class AdministratorController{
    @FXML
    private Label chooseLabel;
    @FXML
    private Label compositeLabel;
    @FXML
    private AnchorPane createPane;
    @FXML
    private TextField createName;
    @FXML
    private TextField createPrice;
    @FXML
    private AnchorPane editPane;
    @FXML
    private ChoiceBox editChoiceBox;
    @FXML
    private TextField editName;
    @FXML
    private TextField editPrice;
    @FXML
    private AnchorPane deletePane;
    @FXML
    private ChoiceBox deleteChoiceBox;
    @FXML
    private AnchorPane allPane;
    @FXML
    private TableView table;
    @FXML
    private GridPane createGrid;
    @FXML
    private AnchorPane createPaneComposite;
    @FXML
    private AnchorPane editPaneComposite;
    @FXML
    private TextField createNameComposite;
    @FXML
    private ChoiceBox editBoxComposite;
    @FXML
    private TextField editNameComposite;
    @FXML
    private GridPane editGridComposite;
    Scene mainView;
    Thread thread;

    @FXML
    void initialize() {
        thread = new Thread() {
            public void run() {             //folosit pentru a elimina 1/2 secunde de lag atunci cand doream sa ma intorc la fereastra princcipala
                try {                                  //pentru a nu mai face load atunci cand apasam pe butonul home voi face load automat la inceput prin acest thread
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
    public void addBaseProduct() {
        Restaurant restaurant = Restaurant.getInstance();
        restaurant.addBaseProduct(createName, createPrice);
    }

    @FXML
    public void editBaseProduct() {
        Restaurant restaurant = Restaurant.getInstance();
        restaurant.editBaseProduct(editChoiceBox, editName, editPrice);
    }

    @FXML
    public void deleteBaseAndComposite() {
        Restaurant restaurant = Restaurant.getInstance();
        restaurant.deleteBaseAndComposite(deleteChoiceBox);
    }

    @FXML
    public void oldPrice() {
        try {
            editPrice.setPromptText("Old price" + ((BaseProduct) editChoiceBox.getValue()).getPrice());
        } catch (NullPointerException e) {
        }
    }

    @FXML
    public void createCompositeProduct() {

        Restaurant restaurant = Restaurant.getInstance();
        restaurant.createCompositeProduct(createGrid, createNameComposite);

    }

    @FXML
    public void editCompositeProduct() {
        Restaurant restaurant = Restaurant.getInstance();
        restaurant.editCompositeProduct(editGridComposite, editBoxComposite, editNameComposite);
    }

    @FXML
    public void selectToEditComposite() {  //generare dinamica de check box-uri pentru toate produsele din meniu
        Restaurant restaurant = Restaurant.getInstance();
        restaurant.selectToEditComposite(editGridComposite, editBoxComposite);
    }


    @FXML
    void showCreateBase() {
        createPane.setVisible(true);
        editPane.setVisible(false);
        chooseLabel.setVisible(false);
    }

    @FXML
    void showEditBase() {
        editPane.setVisible(true);
        createPane.setVisible(false);
        chooseLabel.setVisible(false);
        ArrayList<MenuItem> base = new ArrayList<MenuItem>();

        for (MenuItem i : Restaurant.getInstance().getMenu()) {
            if (i instanceof BaseProduct)
                base.add(i);
        }
        editChoiceBox.setItems(FXCollections.observableArrayList(base));
    }

    @FXML
    void loadDelete() {
        deleteChoiceBox.setItems(FXCollections.observableArrayList(Restaurant.getInstance().getMenu()));
    }

    @FXML
    void loadAllItems() {
        TableColumn nameColumn = new TableColumn("Product name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        nameColumn.setPrefWidth(200);
        TableColumn priceColumn = new TableColumn("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(120);
        table.getColumns().clear();
        table.getItems().clear();
        table.getColumns().addAll(nameColumn, priceColumn);
        table.setItems(FXCollections.observableArrayList(Restaurant.getInstance().getMenu()));
    }


    @FXML
    void showCreateComposite() {
        Restaurant restaurant = Restaurant.getInstance();
        restaurant.showCreateComposite(createGrid);
        createPaneComposite.setVisible(true);
        editPaneComposite.setVisible(false);
        compositeLabel.setVisible(false);
    }

    @FXML
    void showEditComposite() {
        editPaneComposite.setVisible(true);
        createPaneComposite.setVisible(false);
        compositeLabel.setVisible(false);
        ArrayList<MenuItem> base = new ArrayList<MenuItem>();
        for (MenuItem i : Restaurant.getInstance().getMenu()) { //salveaza doar produsele composite intr o lista
            if (i instanceof CompositeProduct)
                base.add(i);
        }

        editBoxComposite.setItems(FXCollections.observableArrayList(base)); //pune lista cu produse composite in Choice Box
    }

    @FXML
    void showLabel() {
        editPaneComposite.setVisible(false);
        createPaneComposite.setVisible(false);
        compositeLabel.setVisible(true);
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

