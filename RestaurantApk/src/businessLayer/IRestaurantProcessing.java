package businessLayer;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;

public interface IRestaurantProcessing {


    /**
     * Search for a MenuItem and returns if found else null.
     * @param name Return a MenuItem
     * @return Return a MewnuItem
     * @pre name != null
     * @post @nochange
     */
    public MenuItem findItem(String name);

    /**
     * add a property change listener
     * @param lis The new property change listener ti be added to support.
     * @pre lis != null
     * @post support.getPropertyChangeListeners().length + 1 @pre  == support.getPropertyChangeListeners().length
     */
    public void addPropertyChangeListener(PropertyChangeListener lis);

    /**
     * remove a property change listener
     * @param lis The new property change listener ti be added to support.
     * @pre lis !=null
     * @post support.getPropertyChangeListeners().length @pre == support.getPropertyChangeListeners().length + 1
     */
    public void removePropertyChangeListener(PropertyChangeListener lis);

    /**
     * Sets the menu
     * @param menu An ArrayList of MenuItems.
     * @pre menu != null
     * @post this.menu == menu
     */
    public void setMenu(ArrayList<MenuItem> menu);

    /**
     * Deletes a product
     * @param item Item to delete.
     * @pre item != null
     * @post forall i : menu @ i != toDelete
     */
    public void deleteProduct(MenuItem item);

    /**
     * return the menu
     * @return Reuturn an ArrayList
     * @pre true
     * @post @nochange
     */
    public ArrayList<MenuItem> getMenu();

    /**
     * Return the oredersMap
     * @return Return an HashMap.
     * @pre true
     * @post @nochange
     */
    public HashMap<Order, ArrayList<MenuItem>> getOrdersMap();

    /**
     * adds an item to the mnu
     * @param item Add item parameter to menu.
     * @pre item != null
     * @post exists x in menu
     */
    public void addMenuItem(MenuItem item);

    /**
     * return the support field
     * @return Return support field.
     * @pre true
     * @post @nochange
     */
    public PropertyChangeSupport getSupport();

    /**
     * Adds an order to the ordersMap
     * @param order      Used as a key for the HashMap
     * @param orderItems Added to the hashMap as a value.
     * @pre order!= null
     * @pre orderItems !=null
     * @post ordersMap.containsKey(order) == true
     * @post ordersMap.containsValue(orderItems) == true;
     */
    public void addOrder(Order order, ArrayList<MenuItem> orderItems);

    /**
     * Adds a base product to the mnu
     * @param createName  Name od the new product
     * @param createPrice Price of the new product
     * @pre createName != null, createPrice != null
     * @post menu.size()@pre +1 == menu.size()
     */
    public void addBaseProduct(TextField createName, TextField createPrice);

    /**
     * Edits a base product
     * @param editChoiceBox Choicebox with all the products from where we select the product to edit
     * @param editName      The new name of the product
     * @param editPrice     The new price of the product
     * @pre editChoice != null, editName != null, editPrice != null
     * @post @post @nochange
     */
    public void editBaseProduct(ChoiceBox editChoiceBox, TextField editName, TextField editPrice);

    /**
     * Deletes a base/composite product
     * @param deleteChoiceBox The choicebox from where we select the product to delete
     * @pre deleteChoiceBox != null
     * @post menu.size()@pre  menu.size()
     */
    public void deleteBaseAndComposite(ChoiceBox deleteChoiceBox);

    /**
     * creates an composite product
     * @param createGrid          GridPane where we put the checkboxes.
     * @param createNameComposite The name of the new product
     * @pre createGrid != null, createNameComposite != null
     * @post menu.size()@pre +1 == menu.size()
     */
    public void createCompositeProduct(GridPane createGrid, TextField createNameComposite);

    /**
     * show the check boxes for the product interface
     * @param createGrid GridPane where we put checkboxes.
     * @pre createGrid != null
     * @post @nochange
     */
    public void showCreateComposite(GridPane createGrid);


    /**
     * Edits a composite product
     * @param editGridComposite GridPane where we generate the checkboxes
     * @param editBoxComposite  ChoiceBox from which we select the product to edit
     * @param editNameComposite The new name of the product.
     * @pre editGridComposite != null, editBoxComposite !=null, editNameComposite != null
     * @post @nochange
     */
    public void editCompositeProduct(GridPane editGridComposite, ChoiceBox editBoxComposite, TextField editNameComposite);

    /***
     * Generetes check boxes for editing a composite product
     * @pre editGridComposite !=null, editBoxComposite !=null
     * @post @nochange
     * @param editGridComposite GridPanme where we generate the checkboxes
     * @param editBoxComposite  ChoiceBox from which we select the product to edit
     */
    public void selectToEditComposite(GridPane editGridComposite, ChoiceBox editBoxComposite);

    /**
     * creates a new order
     * @pre orderGrid !=null, tableNumber != null;
     * @post @nochange
     * @param orderGrid GridPane where we generate the checkboxes
     * @param tableNumber  The number of the table associated with the order.
     */
    public void createOrder(GridPane orderGrid, TextField tableNumber);
}
