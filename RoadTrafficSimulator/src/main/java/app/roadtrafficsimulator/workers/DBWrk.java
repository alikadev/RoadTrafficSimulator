package app.roadtrafficsimulator.workers;


import app.roadtrafficsimulator.beans.Account;
import app.roadtrafficsimulator.beans.SettingsSet;
import app.roadtrafficsimulator.exceptions.DBException;
import app.roadtrafficsimulator.exceptions.UnexpectedException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This worker is used to communicate with the database
 *
 * @author Elvin Kuci
 */
public class DBWrk {
    /**
     * Create the DBWorker
     *
     * @param propsPath The path to the settings file.
     */
    public DBWrk(final String propsPath) {
        this.propsPath = propsPath;
        this.connection = null;
    }

    /**
     * Read the property file and open the connection with the Database
     *
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
     *
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
     *
     * @param account The account.
     *
     * @throws DBException The database Exception
     */
    public void insertAccount(Account account) throws DBException {
        final String QUERY = "INSERT INTO account VALUES (?, ?);";

        // Check argument
        if (account == null)
            throw new UnexpectedException("insertAccount got an unexpected null argument");

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
     *
     * @param account The account. The password should already be hashed
     *
     * @throws DBException The database Exception
     */
    public boolean verifyAccount(Account account) throws DBException {
        final String QUERY = "SELECT password LIKE ? AS result FROM account WHERE id = ?;";

        // Check argument
        if (account == null)
            throw new UnexpectedException("verifyAccount got an unexpected null argument");

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

    /**
     * Get the list of settings set's name created by an account.
     *
     * @param account The account. (The password will not be checked)
     *
     * @return The list of set's name.
     * @throws DBException In case of an error, this will be thrown.
     */
    public List<String> getSettingsSetsList(Account account) throws DBException {
        final String QUERY = "SELECT DISTINCT setname FROM accountsetting WHERE account=?;";

        // Check argument
        if (account == null)
            throw new UnexpectedException("getSettingsSetsList got an unexpected null argument");

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
     * Insert / Update a settings set in the database.
     *
     * @param account The account. (The password will not be checked)
     * @param set The settings set to insert / update.
     *
     * @throws DBException In case of an error, this will be thrown.
     */
    public void insertSettingsSet(Account account, SettingsSet set) throws DBException {
        final String QUERY = "INSERT INTO AccountSetting VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE value=?;";
        //                                                       ^  ^  ^  ^                                ^
        //                                                 Account  |  |  SetName                       Value
        //                                                SettingName  Value

        // Check argument
        if (account == null || set == null)
            throw new UnexpectedException("insertSettingsSet got an unexpected null argument");

        try {
            // Check if DB connection is open
            if (connection == null || connection.isClosed())
                throw new DBException("La connection à la base de donnée n'a pas été ouverte");

            for (String key : set.getSettings().keySet()) {
                Double value = set.getSettings().get(key);

                // Create and process the request
                PreparedStatement ps = connection.prepareStatement(QUERY);
                ps.setString(1, account.getName());
                ps.setString(2, key);
                ps.setDouble(3, value);
                ps.setString(4, set.getName());
                ps.setDouble(5, value);

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBException("Un problème est apparue durant la récupération des jeux de réglages: " + e.getMessage());
        }
    }

    /**
     * Check if the database contains an account's with a settings set with this name.
     *
     * @param account The account. (The password will not be checked)
     * @param setName The set's name.
     *
     * @return True when the database contains an account with a sets with this name.
     *
     * @throws DBException In case of an error, this will be thrown.
     */
    public boolean containsSettingsSet(Account account, String setName) throws DBException {
        final String QUERY = "SELECT 1 FROM dual  WHERE EXISTS(SELECT 1 FROM accountsetting WHERE account LIKE ? AND setname LIKE ? );";

        // Check argument
        if (account == null || setName == null)
            throw new UnexpectedException("containsSettingsSet got an unexpected null argument");

        try {
            // Check if DB connection is open
            if (connection == null || connection.isClosed())
                throw new DBException("La connection à la base de donnée n'a pas été ouverte");

            // Create and process the request
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setString(1, account.getName());
            ps.setString(2, setName);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DBException("Un problème est apparue durant la récupération des jeux de réglages: " + e.getMessage());
        }
    }

    /**
     * Get a settings set from the database.
     * Note that in case of an unset setting's value, the default value will be returned.
     *
     * @param account The account. (The password will not be checked)
     * @param setName The settings set name.
     *
     * @return The set of settings.
     *
     * @throws DBException In case of an error, this will be thrown.
     */
    public SettingsSet getSettingsSet(Account account, String setName) throws DBException {
        final String QUERY =
                "SELECT " +
                "  st.id AS setting, " +
                // In case of NULL value, use the setting's default one
                "  CASE WHEN acst.value IS NULL THEN st.default ELSE acst.value END AS value " +
                "FROM AccountSetting AS acst " +
                "RIGHT JOIN Setting AS st " +
                "  ON acst.setting = st.id " +
                "    AND acst.account = ? " +
                "    AND acst.setname = ?;";

        // Check argument
        if (account == null || setName == null)
            throw new UnexpectedException("containsSettingsSet got an unexpected null argument");

        // Pre init the set
        SettingsSet set = new SettingsSet(setName);

        try {
            // Check if DB connection is open
            if (connection == null || connection.isClosed())
                throw new DBException("La connection à la base de donnée n'a pas été ouverte");

            // Create and process the request
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setString(1, account.getName());
            ps.setString(2, setName);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                set.getSettings().put(rs.getString(1), rs.getDouble(2));
            }

            return set;
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
