package dataAccess;

import connectionFactory.DbConnectionFactory;
import connectionFactory.DbUtility;
import model.Products;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A class that makes it possible to use the basic operations implemented in AbstractDao
 * on the Products table by extending the generic class AbstractDao.Is also used to implement
 * specific behavior of Products table
 */
public class ProductDAO extends AbstractDAO<Products> {

    /**
     * Method used to update the quantity of a product.
     * @param prod  Product for which the quantity will be updated.
     */
    private void updateProduct(Products prod){
        StringBuilder query = new StringBuilder();
        query.append("UPDATE Products SET quantity = quantity + " + prod.getQuantity() + " WHERE product_name = '"+ prod.getProduct_name()+"'");
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnectionFactory.getConnection();
            statement = connection.prepareStatement(query.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtility.close(statement);
            DbUtility.close(connection);
        }
    }

    /**
     * Method used to insert a product into the database and if an duplicate exception is thrown
     * we update the quantity of the product instead.
     * @param obj Product which will be inserted into the database.
     */
    public void insertTable(Products obj) {
        int error = super.insertTable(obj);
        if (error == 1062) {       //1062 code pentru "Duplicate entry"
            updateProduct((Products)obj);  //se va face update la cantitatea produsului
        }
    }

}
