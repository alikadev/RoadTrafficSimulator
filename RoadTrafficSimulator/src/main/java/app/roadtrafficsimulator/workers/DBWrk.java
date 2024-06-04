package app.roadtrafficsimulator.workers;


import app.roadtrafficsimulator.beans.Account;
import app.roadtrafficsimulator.exceptions.DBException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
            throw new DBException("Un problème est apparu durant la lecture du fichier de propriétés: " + e.getMessage());
        }

        final String jdbcUrl = "jdbc:" + type + "://" + host + ":" + port + "/" + database + "?serverTimezone=CET";

        // Open the connection
        try {
            // Scan for jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Open connection
            connection = DriverManager.getConnection(jdbcUrl, user, passwd);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DBException("Problème durant la connection à la base de donnée: " + e.getMessage());
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
            throw new DBException("Un problème est apparu durant la fermeture de la connection à la base de donnée: " + e.getMessage());
        }
    }

    /**
     * Insert an account into the database. Will throw an error in case of duplicate
     * @param account The account.
     * @throws DBException The database Exception
     */
    public void insertAccount(Account account) throws DBException {
        final String QUERY = "INSERT INTO Account VALUES (?, ?);";
        try {
            // Check if DB connection is open
            if (connection == null || connection.isClosed())
                throw new DBException("La connection à la base de donnée n'a pas été ouverte");

            // Create and process the request
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setString(1, account.getName());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DBException("Un problème est apparu durant la création du compte: " + e.getMessage());
        }
    }

    /**
     * Verify if the password of the account is the same that the one in the DB.
     * @param account The account. The password should already be hashed
     * @throws DBException The database Exception
     */
    public boolean verifyAccount(Account account) throws DBException {
        final String QUERY = "SELECT password LIKE ? AS result FROM account WHERE id = ?;";
        try {
            // Check if DB connection is open
            if (connection == null || connection.isClosed())
                throw new DBException("La connection à la base de donnée n'a pas été ouverte");

            // Create and process the request
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setString(1, account.getPassword());
            ps.setString(2, account.getName());
            ResultSet rs = ps.executeQuery();

            // Check if the request exist
            if (!rs.next())
                throw new DBException("Le compte " + account.getName() + " n'existe pas dans la base de donnée!");

            // Return result (1 = true, 0 = false)
            return rs.getInt(1) == 1;
        } catch (SQLException e) {
            throw new DBException("Un problème est apparue durant la vérification du compte: " + e.getMessage());
        }
    }

    public List<String> getSettingsList(Account account) throws DBException {
        final String QUERY = "SELECT DISTINCT setname FROM accountsetting WHERE account=?;";
        try {
            // Check if DB connection is open
            if (connection == null || connection.isClosed())
                throw new DBException("La connection à la base de donnée n'a pas été ouverte");

            // Create and process the request
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setString(1, account.getName());
            ResultSet rs = ps.executeQuery();

            // Check if the request exist
            List<String> sets = new ArrayList<>();
            while (rs.next()) {
                sets.add(rs.getString(1));
            }
            return sets;
        } catch (SQLException e) {
            throw new DBException("Un problème est apparue durant la récupération des jeux de réglages: " + e.getMessage());
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
