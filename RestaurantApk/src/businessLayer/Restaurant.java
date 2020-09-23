package businessLayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class Restaurant implements IRestaurantProcessing {

    private ArrayList<MenuItem> menu;
    private HashMap<Order, ArrayList<MenuItem>> ordersMap;
    private static Restaurant restaurant = new Restaurant();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Constructor of Restaurant Class
     */
    public Restaurant() {
        this.menu = new ArrayList<MenuItem>();
        this.ordersMap = new HashMap<Order, ArrayList<MenuItem>>();
    }

    /**
     * Invariants of Restaurant class
     * @return Boolean Value
     */
    public boolean isWellFormed() {
        if (restaurant == null)
            return false;
        if (support == null)
            return false;
        if (menu == null)
            return false;
        if (ordersMap == null)
            return false;
        return true;
    }


    public MenuItem findItem(String name) {
        assert isWellFormed();          //se foloseste pentru a gasi instanta unui item cautand dupa numele prdusului
        assert name != null;                //metoda se foloseste in cazul check box-urilor care memoreaza doar un String cu numele produsului si nu o instanta
        for (MenuItem i : menu) {
            if (i.getProductName().equals(name)) {
                assert isWellFormed();
                return i;
            }
        }
        assert isWellFormed();
        return null;
    }


    public void addPropertyChangeListener(PropertyChangeListener lis) {
        assert isWellFormed();
        assert lis != null;
        int len = support.getPropertyChangeListeners().length;      //se adauga un Observer
        support.addPropertyChangeListener(lis);
        assert isWellFormed();
        assert (len + 1) == support.getPropertyChangeListeners().length;
    }


    public void removePropertyChangeListener(PropertyChangeListener lis) {
        assert isWellFormed();
        assert lis != null;
        int len = support.getPropertyChangeListeners().length;
        support.removePropertyChangeListener(lis);
        assert isWellFormed();
        assert len == support.getPropertyChangeListeners().length + 1;
    }


    public void setMenu(ArrayList<MenuItem> menu) {
        assert isWellFormed();
        assert menu != null;
        this.menu = menu;           //se foloseste pentru  deserializable
        assert this.menu == menu;
        assert isWellFormed();
    }


    public void deleteProduct(MenuItem item) {
        assert isWellFormed();                      //metoda care sterge un produs si merge recursiv si sterge mai apoi toate produsele ce il contin
        assert item != null;                                //produsele composite care sunt sterse for cauta la randul lor daca apartin altor produse composite
        ArrayList<MenuItem> toDelete = new ArrayList<MenuItem>();         //si le va sterge si pe acelea
        for (MenuItem i : menu) {
            if (i instanceof CompositeProduct) {
                for (MenuItem j : ((CompositeProduct) i).getComposition())
                    if (j.equals(item)) {
                        toDelete.add(i);
                    }
            }
        }

        for (MenuItem i : toDelete) {
            deleteProduct(i);
        }
        menu.remove(item);
        assert isWellFormed();
    }


    public static Restaurant getInstance() {        //returneaza instanta restaurantului (singleton pattern)
        return restaurant;
    }


    public ArrayList<MenuItem> getMenu() {
        assert isWellFormed();
        return menu;
    }


    public HashMap<Order, ArrayList<MenuItem>> getOrdersMap() {
        assert isWellFormed();
        return ordersMap;
    }


    public void addMenuItem(MenuItem item) {
        assert isWellFormed();
        assert item != null;
        menu.add(item);
        assert menu.contains(item); //verificam daca exista
        assert isWellFormed();
    }


    public PropertyChangeSupport getSupport() {
        assert isWellFormed();
        return support;
    }


    public void addOrder(Order order, ArrayList<MenuItem> orderItems) {
        assert isWellFormed();
        assert order != null;
        assert orderItems != null;
        HashMap<Order, ArrayList<MenuItem>> old = this.ordersMap;
        ordersMap.put(order, orderItems);                                        //adaugam o comanda in HashMap
        support.firePropertyChange("ordersMap", ordersMap, order);      //semnalam Chef-ul ca avem o comanda noua
        assert ordersMap.containsKey(order);
        assert ordersMap.containsValue(orderItems);
        assert isWellFormed();
    }


    public void addBaseProduct(TextField createName, TextField createPrice) {
        try {
            assert createName != null;
            assert createPrice != null;
            assert restaurant.isWellFormed();
            int tempForAssert = menu.size() + 1;
            String name = createName.getText();
            double price = Double.parseDouble(createPrice.getText());
            addMenuItem(new BaseProduct(name, price));
            createName.clear();
            createPrice.clear();
            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setHeaderText("The new data was successfully added!");
            error.show();
            assert tempForAssert == menu.size();
            assert restaurant.isWellFormed();
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Format date incorrect");
            error.show();
        }
    }


    public void editBaseProduct(ChoiceBox editChoiceBox, TextField editName, TextField editPrice) {
        assert editChoiceBox != null;
        assert editName != null;
        assert editPrice != null;
        assert restaurant.isWellFormed();
        try {
            if (!editChoiceBox.getSelectionModel().isEmpty()) {             //verificam daca a fost ales un element in choiceBox
                BaseProduct product = ((BaseProduct) editChoiceBox.getValue());
                String name = editName.getText();
                double price = Double.parseDouble(editPrice.getText());
                product.setProductName(name);
                product.setPrice(price);

                for (MenuItem i : menu) {
                    if (i instanceof CompositeProduct) {                //vom cauta daca exista alte produse care au in compozitie elementul editat
                        for (MenuItem j : ((CompositeProduct) i).getComposition())      //si vom modifica prestul acestor produse
                            if (j.equals(product)) {
                                i.setPrice(i.getPrice() + product.getPrice());
                            }
                    }
                }
                editName.clear();
                editPrice.clear();
                editPrice.setPromptText("Enter new price");
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setHeaderText("The new data was successfully updated!");
                error.show();
                editChoiceBox.getItems().clear();
                editChoiceBox.setItems(FXCollections.observableArrayList(Restaurant.getInstance().getMenu())); //reincarcam in choiceBox elementele
                assert restaurant.isWellFormed();
            } else {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setHeaderText("No product selected");
                error.show();
            }
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Data format incorrect");
            error.show();
        }
    }


    public void deleteBaseAndComposite(ChoiceBox deleteChoiceBox) {
        assert isWellFormed();
        assert deleteChoiceBox != null;
        int tempSize = menu.size();
        try {
            if (!deleteChoiceBox.getSelectionModel().isEmpty()) {
                MenuItem product = ((MenuItem) deleteChoiceBox.getValue());
                deleteProduct(product);                                          //stergerea recursiva a produsului
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setHeaderText("The product was deleted!");
                error.show();
                deleteChoiceBox.getItems().clear();
                deleteChoiceBox.setItems(FXCollections.observableArrayList(Restaurant.getInstance().getMenu())); //se reincarca produsele in choice box
            } else {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setHeaderText("No product selected");
                error.show();
            }
            assert isWellFormed();
            assert tempSize > menu.size();
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Data format incorrect!");
            error.show();
        }
    }


    public void showCreateComposite(GridPane createGrid) {
        assert isWellFormed();
        assert createGrid != null;
        int i = 0, j = 0;
        createGrid.getChildren().clear();
        for (MenuItem item : menu) {                        //se itereaza pe lista de MenuItem
            CheckBox c = new CheckBox(item.getProductName());    //pentru fiecare MenuItem se va crea un checkBox
            createGrid.add(c, i, j);                           //se adauga check box-ul in GridPane
            if (++j == 7) {
                j = 0;
                i++;
            }
        }
        assert isWellFormed();
    }

    public void createCompositeProduct(GridPane createGrid, TextField createNameComposite) {
        assert isWellFormed();
        assert createGrid != null;
        assert createNameComposite != null;
        int tempSize = menu.size() + 1;
        try {
            ArrayList<businessLayer.MenuItem> menu = restaurant.getMenu();
            ObservableList<Node> childrens = createGrid.getChildren();
            CompositeProduct newItem = new CompositeProduct(createNameComposite.getText());
            for (Node node : childrens) {                  //iteram peste toti copii GridPane-ului care sunt niste check box-uri cu numele produselor
                if (((CheckBox) node).isSelected()) {               //daca check box-ul este selectat
                    MenuItem item = findItem(((CheckBox) node).getText());  //vom cauta produsul salvat in ArrayList ul menu cu metoda findItem
                    newItem.addProduct(item);
                    ((CheckBox) node).setSelected(false);                    //deselectam check box-ul
                }
            }
            newItem.setPrice(newItem.computePrice());   //setam prestul
            addMenuItem(newItem);                       //adaugam noul produs in menu
            createNameComposite.clear();
            showCreateComposite(createGrid);
            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setHeaderText("The new data was successfully added!");
            error.show();
            assert tempSize == menu.size();
            assert isWellFormed();
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Format date incorrect");
            error.show();
        }
    }



    public void editCompositeProduct(GridPane editGridComposite, ChoiceBox editBoxComposite, TextField editNameComposite) {
        try {
            assert isWellFormed();
            assert editBoxComposite != null;
            assert editGridComposite != null;
            assert editNameComposite != null;
            ArrayList<businessLayer.MenuItem> menu = getMenu();
            ObservableList<Node> childrens = editGridComposite.getChildren();

            CompositeProduct product = (CompositeProduct) editBoxComposite.getValue(); //obtinem obiectul de editat
            product.getComposition().clear();           //stergem compozitia produsului
            for (Node node : childrens) {
                if (((CheckBox) node).isSelected()) {       //verificam check box-urile selectate si le adaugam in compozitia produsului composite
                    MenuItem item = findItem(((CheckBox) node).getText());
                    product.addProduct(item);
                }
            }
            product.setProductName(editNameComposite.getText());    //setare nume nou
            product.setPrice(product.computePrice());               //setare pret nou


            editNameComposite.clear();
            editBoxComposite.getItems().clear();
            editBoxComposite.setItems(FXCollections.observableArrayList(Restaurant.getInstance().getMenu()));
            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setHeaderText("The new data was successfully updated!");
            error.show();
            assert isWellFormed();
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Data format incorrect");
            error.show();
        }
    }


    public void selectToEditComposite(GridPane editGridComposite, ChoiceBox editBoxComposite) {
        try {
            assert isWellFormed();
            assert editBoxComposite !=null;
            assert editGridComposite !=null;
            ArrayList<businessLayer.MenuItem> menu = getMenu();
            int i = 0, j = 0;
            editGridComposite.getChildren().clear();                       //generare dinamica de checkBox-uri pentru toate inredientele si bifarea componentelor din care
            CompositeProduct product = ((CompositeProduct) editBoxComposite.getValue());    //luam produsul composite care va fi editat din choice box
            for (MenuItem item : menu) {
                if (!item.getProductName().equals(((MenuItem) editBoxComposite.getValue()).getProductName())) { //nu cream checkBox pentru item-ul selectat
                    CheckBox c = new CheckBox(item.getProductName());
                    if (product.findItem(item.getProductName()))      //daca gasim item in lista de compozitie al composite product-ului vom bifa check box-ul
                        c.setSelected(true);

                    editGridComposite.add(c, i, j);
                    if (++j == 7) {
                        j = 0;
                        i++;
                    }
                }
                assert isWellFormed();
            }
        } catch (NullPointerException e) {
        }
    }


    public void createOrder(GridPane orderGrid, TextField tableNumber) {
        try {
            assert isWellFormed();
            assert orderGrid != null;
            assert tableNumber !=null;
            ObservableList<Node> childrens = orderGrid.getChildren();   //luam toti copiii gridPane-ului
            ArrayList<MenuItem> orderProducts = new ArrayList<MenuItem>();  //cream o lista noua pentru produsele comenzii
            for (Node node : childrens) {                       //iteram pe toate check box-urile
                if (((CheckBox) node).isSelected()) {
                    MenuItem item = findItem(((CheckBox) node).getText());  //adaugam in noua lista produsele selectate
                    orderProducts.add(item);
                    ((CheckBox) node).setSelected(false);
                }
            }
            Order newOrder = new Order(Integer.parseInt(tableNumber.getText()), LocalDate.now());   //adaugam comanda in HashMap
            addOrder(newOrder, orderProducts);
            tableNumber.clear();
            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setHeaderText("Order successfully added!");
            error.show();
            assert isWellFormed();
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Format date incorect");
            error.show();
        }
    }
}
