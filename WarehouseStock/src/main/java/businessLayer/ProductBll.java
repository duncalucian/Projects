package businessLayer;

import dataAccess.ProductDAO;
import model.Products;

import java.util.ArrayList;

/**
 * Used to call methods from DAO layer that implements different operations on Orders table.
 */
public class ProductBll {

    /**
     * Instance of ProductDao that is used to call the required methods/
     */
    private static ProductDAO productDAO = new ProductDAO();

    /**
     * Creates an instance of a Product and calls DAO layer to insert the Order in the database.
     * @param name  name of the product
     * @param quantity  number of products avalabile
     * @param price cost of the product
     */
    public static void insertProduct(String name, int quantity, double price) {
        Products client = new Products(name, quantity, price, 0);
        productDAO.insertTable(client);
    }

    /**
     * Used to call the DAO layer to delete an order from database
     * @param name Name of the product to be deleted
     */
    public static void deleteProduct(String name) {
        Products client = new Products(name); //creates a client, only the name needs to be set
        productDAO.deleteFromTable(client);         //because only the name is needed for the deletion
    }

    /**
     * Returns all the products stored in the database (and all their fields) as a ArrayList
     * @return  Returns an ArrayList of Products
     */
    public static ArrayList<Products> reportTable() {
        try {
            return productDAO.reportTable(Products.class.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
