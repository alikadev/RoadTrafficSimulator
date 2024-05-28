package app.roadtrafficsimulator.workers;


import app.roadtrafficsimulator.beans.Account;
import app.roadtrafficsimulator.exceptions.DBException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This worker is used to communicate with the database
 * @author kucie
 */
public class DBWrk {

    /**
     * Create the DBWorker
     * @param propsPath The path to the settings file.
     */
    public DBWrk(final String propsPath) {
        this.propsPath = propsPath;
        this.connection = null;
    }

    /**
     * Read the property file and open the connection with the Database
     * @throws DBException In case of an error, it will be there!
     */
    public void start() throws DBException {
        // Prepare JDBC URL from the properties file
        final String type, host, user, passwd, port, database;
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(propsPath));

            // Load individual property in the file
            type = prop.getProperty("type");
            host = prop.getProperty("host");
            database = prop.getProperty("database");
            user = prop.getProperty("user");
            passwd = prop.getProperty("password");
            port = prop.getProperty("port");

        } catch (IOException e) {
            throw new DBException(e.getMessage());
        }

        final String jdbcUrl = "jdbc:" + type + "://" + host + ":" + port + "/" + database + "?serverTimezone=CET";

        // Open the connection
        try {
            // Scan for jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Open connection
            connection = DriverManager.getConnection(jdbcUrl, user, passwd);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DBException("No driver = " + e.getMessage());
        }
    }

    /**
     * Close the connection with the database
     * @throws DBException The exception
     */
    public void terminate() throws DBException {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    /**
     * Insert an account into the database. Will throw an error in case of duplicate
     * @param account The account.
     * @throws DBException The database Exception
     */
    public void insertAccount(Account account) throws DBException {
        try {
            // Check if DB connection is open
            if (connection == null || connection.isClosed())
                throw new DBException("Database connection is not opened");


        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    /**
     * The path of the properties file
     */
    private final String propsPath;

    /**
     * The connection to the database
     */
    private Connection connection;
}
