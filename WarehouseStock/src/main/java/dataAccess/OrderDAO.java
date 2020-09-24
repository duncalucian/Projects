package dataAccess;

import connectionFactory.DbConnectionFactory;
import connectionFactory.DbUtility;
import model.Orders;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class that makes it possible to use the basic operations implemented in AbstractDao
 * on the Orders table by extending the generic class AbstractDao.Is also used to implement
 * specific behavior of Orders table
 */
public class OrderDAO extends AbstractDAO<Orders> {
    /**
     * Method used to call the parent method for inserting the Order into the database
     * and return ture if the error code is 1644 ( user-defined exception condition )
     * generated using a trigger implemented into the db. This error means that
     * the quantity of the product avalabile is not enough to complete the order
     * and we need to generate a undestock bill.
     * @param obj An object of type Orders that represents the data that will be saved into the db.
     * @return Boolean
     */
    public boolean insertTable(Orders obj) {
        int err = super.insertTable(obj);
        if(err == 1644)
            return true;
        return false;
    }

    /**
     * Method used to return the order with the highest id (last order inserted into the db)
     * which will be used on our fourth table (orderItems) to retrieve the totalPrice of the order.
     * @return int that represents the id of the last order inserted into the db
     */
    public int getOrder(){
        String query = "SELECT MAX(orderId) FROM Orders";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = DbConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            result = statement.executeQuery();
            result.next();
            return result.getInt("MAX(orderId)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtility.close(statement);
            DbUtility.close(connection);
            DbUtility.close(result);
        }
        return 0;
    }

    /**
     * Used to get the the total price of an order.
     * @param id the id of the order
     * @return  int that represents the total price
     */
    public int getTotalPrice(int id){
        String query = "SELECT total_price From orderItems WHERE id_order = " + id;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = DbConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            result = statement.executeQuery();
            result.next();
            return result.getInt("total_price");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtility.close(statement);
            DbUtility.close(connection);
            DbUtility.close(result);
        }
        return 0;
    }

}
