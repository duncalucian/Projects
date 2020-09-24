package dataAccess;

import connectionFactory.DbConnectionFactory;
import connectionFactory.DbUtility;
import model.CheckToDelete;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements the basic operations needed for our tabels (insert, delect, Select*) using reflection technique to avoid duplicate code.
 * @param <T> generic parameter
 */
public class AbstractDAO<T> {
    /**
     * Variable used to write log messages during the execution of the program/
     */
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    /**
     * Used to store the type of the child Class by retrieving the class parameter.
     */
    private final Class<T> type;

    /**
     *  Initialise "type" field variable.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Creates a query that will be used to insert data into the database
     * @param obj An object that represents the data that will be saved to the database (it can be a Client, Product or Order)
     * @return A stringBuilder that represents the query.
     */
    private StringBuilder createInsertQuery(Object obj) {
        StringBuilder str1 = new StringBuilder();     //in primul string se va forma prima parte a queryului (pana la VALUES)
        StringBuilder str2 = new StringBuilder();       // aici se va for forma efectiv datele ce vor fi introduse in tabel
        str1.append("INSERT INTO " + obj.getClass().getSimpleName() + "(");
        str2.append(" VALUES(");
        Field fieldlist[] = obj.getClass().getDeclaredFields();  //se obtine un vector cu toate field-urile
        for (int i = 1; i < fieldlist.length; ++i) {    //se incepe de la 1 deoarece coloana de id din toate tabelurile este setata pe auto_increment si nu trebuie introdusa de noi
            fieldlist[i].setAccessible(true);
            Object value;
            try {
                value = fieldlist[i].get(obj);
                str1.append(fieldlist[i].getName() + (i != fieldlist.length - 1 ? "," : "")); //se adauga numele field-ului si "," daca nu este ultimul element
                str2.append((fieldlist[i].getType().equals(String.class) ? "'" : "") + value + (fieldlist[i].getType().equals(String.class) ? "'" : "") + (i != fieldlist.length - 1 ? "," : ""));
            } catch (IllegalArgumentException e) {        //daca field-ul este de tipul string va fi pus intre ghilimele
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        str1.append(")");
        str2.append(")");
        str1.append(str2);  //se concateneaza rezultatele
        return str1;
    }

    /**
     * Creates a query that will be used to delete from the database by setting the deleted flag to 1.
     * @param obj  An object that represente the data that will be deleted from the database (it can be a Client, Product or Order)
     * @return  A stringBuilder that represents the query.
     */
    private StringBuilder createDeleteQuery(Object obj) {
        StringBuilder str1 = new StringBuilder();
        str1.append("UPDATE " + obj.getClass().getSimpleName() + " SET deleted = 1 WHERE "); //se formeaza partea standard al query-ului la care se va face append
        Field fieldlist[] = obj.getClass().getDeclaredFields();     //returneaza un array cu toate field-urile
        boolean foundAnnotation = false;    //se va folosi o variabila booleana pentru a ne ajuta sa pune corect "AND" intre conditii
        for (Field field : fieldlist) {
            field.setAccessible(true);      //vom seta permisiunile pe true pentru a putea accesa field-ul
            Object value;
            try {
                value = field.get(obj);
                if (field.isAnnotationPresent(CheckToDelete.class)) {       //daca field-ul are setat o adnotatie de tipul CheckToDelete acesta trebuie sa apara in query
                    str1.append((foundAnnotation == true ? " AND " : "")+ field.getName() + " = " + "'" + value + "'");
                    foundAnnotation = true;      //doar in prima iteratie nu se va printa "AND" (doar daca avem mai mult de o conditie treuie sa punem "AND")
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return str1;
    }

    /**
     *  Creates a query that will be used to retrieve all the data from a table.
     * @param obj An object whose type represents the table from where the data will pe retrieved.(it can be a Client, Product or Order)
     * @return A stringBuilder that represents the query.
     */
    private StringBuilder createReportQuery(Object obj) {
        StringBuilder str1 = new StringBuilder();
        str1.append("SELECT * FROM " + obj.getClass().getSimpleName() + " WHERE deleted = 0");
        return str1;
    }

    /**
     * Method used to insert data into a tabel according to the Object received as parameter.
     * @param obj  An object whose type represents the table where the data will pe inserted. (it can be a Client, Product or Order)
     * @return Un cod de eroare sau 0 incaz de succes
     */
    public int insertTable(Object obj) {
        String query = createInsertQuery(obj).toString();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            return e.getErrorCode(); // returneaza codul de eroare
        } finally {
            DbUtility.close(statement);
            DbUtility.close(connection);
        }
        return 0;
    }
    /**
     * Method used to delete data from a tabel according to the Object received as parameter.
     * @param obj  An object whose type represents the table from where the data will be deleted. (it can be a Client, Product or Order)
     */
    public void deleteFromTable(Object obj) {
        String query = createDeleteQuery(obj).toString();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:deleteTable " + e.getMessage());
        } finally {
            DbUtility.close(statement);
            DbUtility.close(connection);
        }
    }

    /**
     * Method used to retrieve datafrom a tabel according to the Object received as parameter.
     * @param obj An object whose type represents the table from where the data wil pe retrieved. (it can be a Client, Product or Order)
     * @return An ArrayList which contains the data retrieved from the table (Each object represents 1 row)
     */
    public ArrayList<T> reportTable(Object obj) {
        String query = createReportQuery(obj).toString();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = DbConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            result = statement.executeQuery();
            return createObjects(result);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:reportTable " + e.getMessage());
        } finally {
            DbUtility.close(statement);
            DbUtility.close(connection);
            DbUtility.close(result);
        }
        return null;
    }

    /**
     * Creating objects using reflection technique.
     * @param resultSet The data retrieved from the table
     * @return An ArrayList which contains the data retrieved from the table.
     */
    private ArrayList<T> createObjects(ResultSet resultSet) {
        ArrayList<T> list = new ArrayList<T>();
        try {
            while (resultSet.next()) {
                T instance = type.newInstance();                    //se creaza un obiect de tipul clasei care este transmisa ca parametru
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());    //se obtine valoare field-ului
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);   //se creeaza un descriptor folosit pentru a obtine setter-ul field ului
                    Method method = propertyDescriptor.getWriteMethod();            //se obtine metoda setter
                    method.invoke(instance, value);                         // se seteaza valoare
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

}
