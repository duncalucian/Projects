package connectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Provides some utilities like closing a database connection
 */
public class DbUtility {

    /**
     * Closes a connection.
     * @param connection Receives the connection to be closed
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                /*Ignore*/
            }
        }
    }

    /**
     * Closes a statement.
     * @param statement Receives the statement to be closed
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                /*Ignore*/
            }
        }
    }

    /**
     * Closes a resultSet
     * @param resultSet Receives the resultSet to be closed.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                /*Ignore*/
            }
        }
    }
}
