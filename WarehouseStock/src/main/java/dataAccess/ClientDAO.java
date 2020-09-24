package dataAccess;

import model.Clients;
import model.Orders;

/**
 * A class that makes it possible to use the basic operations implemented in AbstractDao
 * on the Clients table by extending the generic class AbstractDao.Is also used to implement
 * specific behavior of Clients table
 */
public class ClientDAO extends AbstractDAO<Clients> {
    /**
     * Method used to delete both the client orders and the client from the database.
     * @param client The client who will be deleted from table
     */
    public void deleteFromTable(Clients client) {
        Orders order = new Orders(client.getClient_name()); //cream o comanda care are numele clientului
        super.deleteFromTable(order);               //stergem toate comenzile cu numele clientului respectiv
        super.deleteFromTable(client);              //stergem clientul din tabel
    }
}
