package connectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helps you create a connection to a database using a jdbc connection string.
*/
public class DbConnectionFactory {
    /**
     * LOGGER for error printing.
     */
    private static final Logger LOGGER = Logger.getLogger(DbConnectionFactory.class.getName());
    /**
     * Stores the path to the Driver file
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver";         //am avut o eroare din cauza time zone-ului si a trebuit sa setez si niste proprietati la conectare
    /**
     * Connection string to the database.
     */
    private static final String dbUrl = "jdbc:mysql://localhost:3306/OrderManagement?user=root?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    /**
     * Credential for log in (Username)
     */
    private static final String user = "root";
    /**
     * Credential for log in (Password)
     */
    private static final String pass = "root";

    /**
     * A private instance of the class (used to implement Singleton pattern)
     */
    private static DbConnectionFactory singleton = new DbConnectionFactory();

    /**
     * Constructor that checks if the Driver exists in the libraries.
     */
    private DbConnectionFactory(){
        try{
            Class.forName(driver);
        }catch(ClassNotFoundException e){
            System.out.println("Driver class not found!");
        }
    }

    /**
     *  private method, not for your eyes
     * @return
     */
    private Connection createConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, user, pass);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
        }
        return connection;
    }

    /**
     * This method creates a connection to the database specified in in dbUrl.
     * @return A connection to the database
     */
    public static Connection getConnection() {
        return singleton.createConnection();
    }

}

