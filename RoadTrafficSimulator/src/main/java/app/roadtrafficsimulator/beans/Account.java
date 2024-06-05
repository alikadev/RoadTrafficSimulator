package app.roadtrafficsimulator.beans;

/**
 * Represents a user account with a name and a password.
 * Provides methods to get and set the name and password.
 *
 * @author Elvin Kuci
 */
public class Account {
    /**
     * Constructs an Account with the specified name and password.
     *
     * @param name the name of the account
     * @param password the password of the account
     */
    public Account(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Returns the name of the account.
     *
     * @return the name of the account
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the account.
     *
     * @param name the new name of the account
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the password of the account.
     *
     * @return the password of the account
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the account.
     *
     * @param password the new password of the account
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The name of the account.
     */
    private String name;

    /**
     * The password of the account.
     */
    private String password;
}
