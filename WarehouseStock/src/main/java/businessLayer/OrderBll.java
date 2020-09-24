package businessLayer;

import dataAccess.OrderDAO;
import model.Orders;

import java.util.ArrayList;

/**
 * Used to call methods from DAO layer that implements different operations on Orders table.
 */
public class OrderBll {
    /**
     * Instance of OrderDao that is used to call the required methods/
     */
    private static OrderDAO orderDAO = new OrderDAO();

    /**
     * Creates an instance of a Order and calls DAO layer to insert the Order in the database.
     * @param cname    Name of the Client that placed the order
     * @param pname    Name of the product
     * @param quantity Number of products for the order.
     * @return True if command placed with success (quantity of the needed product is greater than the quantity of the order) and false otherwise.
     */
    public static boolean insertOrder(String cname, String pname, int quantity) {
        Orders order = new Orders(cname, pname, quantity, 0);
        return orderDAO.insertTable(order); //se returneaza daca a fost sau nu efectuata cu succes operatia pentru a se putea genera chitanta.
    }

    /**
     * Return de total price of the last paced order.
     * @return  An int that represents the total price of the last placed order
     */
    public static int getTotalPrice() {
        int id = orderDAO.getOrder();          //se obine id-ul ultimei comenzi prin interogarea tabelei Orders
        int totalPrice = orderDAO.getTotalPrice(id);    //se obtine totalPrice ul comenzii prin gasirea id-ului comenzii in tabelul orderItems
        return totalPrice;
    }

    /**
     * Returns all the orders stored in the database (and all their fields) as a ArrayList
     * @return  Returns an ArrayList of Orders
     */
    public static ArrayList<Orders> reportTable() {
        try {
            return orderDAO.reportTable(Orders.class.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
