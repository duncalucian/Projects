package businessLayer;

import dataAccess.ClientDAO;
import model.Clients;
import java.util.ArrayList;

/**
 * Used to call methods from DAO layer that implements different operations on Clients table.
 */
public class ClientBll {
    /**
     * Instance of clientDao that is used to call the required methods/
     */
    private static ClientDAO clientDAO = new ClientDAO();

    /**
     * Creates an instance of a Client and calls DAO layer to insert the Client in the database.
     * @param name Name of the client
     * @param address Address of the client
     */
    public static void insertClient(String name, String address){
        Clients client = new Clients(name, address, 0);
        clientDAO.insertTable(client);
    }

    /**
     * Used to call the DAO layer to delete a client from database
     * @param name  Name of the deleted client
     * @param address   Address of the deleted client
     */
    public static void deleteClient(String name, String address){
        Clients client = new Clients(name, address, 0);
        clientDAO.deleteFromTable(client);
    }

    /**
     * Returns all the clients stored in the database (and all their fields) as a ArrayList
     * @return  Returns an ArrayList of Clients
     */
    public static ArrayList<Clients> reportTable() {
        try {
            return clientDAO.reportTable(Clients.class.newInstance());
        }catch(InstantiationException | IllegalAccessException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
