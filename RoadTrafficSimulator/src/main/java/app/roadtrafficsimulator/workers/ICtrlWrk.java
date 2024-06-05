/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.roadtrafficsimulator.workers;

import app.roadtrafficsimulator.beans.Account;
import app.roadtrafficsimulator.beans.Circuit;
import app.roadtrafficsimulator.beans.SettingsSet;
import app.roadtrafficsimulator.exceptions.DBException;

import java.util.List;

/**
 * Interface du controller envers le worker
 *
 * @author Elvin Kuci
 */
public interface ICtrlWrk {
    /**
     * Create an account and persist it on the database.
     *
     * @param account The account (The password shouldn't be hashed at this point).
     * @throws DBException In case of an error, this will be thrown.
     */
    void createAccount(Account account) throws DBException;

    /**
     * Verify an account's password.
     *
     * @param account The account (the password needs to be set, but it shouldn't be hashed at this point).
     *
     * @return True if the account exists and the password is correct.
     * @throws DBException In case of an error, this will be thrown.
     */
    boolean verifyAccount(Account account) throws DBException;

    /**
     * Get the settings sets list from the database.
     * The user needs to already be connected in the worker's perspective.
     *
     * @return The list of settings set.
     * @throws DBException In case of an error, this will be thrown.
     */
    List<String> getSettingsSetsList() throws DBException;

    /**
     * Create a new settings set in the database.
     * The user needs to already be connected in the worker's perspective.
     *
     * @param set The set to preserve in the database.
     *
     * @throws DBException In case of an error, this will be thrown.
     */
    void updateSettingsSet(SettingsSet set) throws DBException;

    /**
     * Update a new settings set in the database.
     * The user needs to already be connected in the worker's perspective.
     * In reality, it will do the same that `updateSettingsSet` but with a set exists check!
     *
     * @param set The set to preserve in the database.
     *
     * @throws DBException In case of an error, this will be thrown.
     */
    void createSettingsSet(SettingsSet set) throws DBException;

    /**
     * Load a settings set from the database. In case of a new value in a set, the setting set will use the default value!
     * The user needs to already be connected in the worker's perspective.
     *
     * @param setName The name of the set.
     *
     * @return The settings set in the database.
     * @throws DBException In case of an error, this will be thrown.
     */
    SettingsSet loadSettingsSet(String setName) throws DBException;

    /**
     * Apply a setting set to the current circuit.
     *
     * @param set The set of settings to apply.
     */
    void applySettingsSet(SettingsSet set);

    /**
     * Get the circuit reference.
     *
     * @return The circuit.
     */
    Circuit getCircuit();

    /**
     * Start the simulation.
     */
    void startSimulation();

    /**
     * Stop the simulation.
     */
    void stopSimulation();
}
