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
    void createAccount(Account account) throws DBException;
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
     * @throws DBException In case of an error, this will be thrown.
     */
    void updateSettingsSet(SettingsSet set) throws DBException;

    /**
     * Update a new settings set in the database.
     * The user needs to already be connected in the worker's perspective.
     * In reality, it will do the same that `updateSettingsSet` but with a set exists check!
     *
     * @param set The set to preserve in the database.
     * @throws DBException In case of an error, this will be thrown.
     */
    void createSettingsSet(SettingsSet set) throws DBException;

    /**
     * Load a settings set from the database. In case of a new value in a set, the setting set will use the default value!
     * The user needs to already be connected in the worker's perspective.
     *
     * @param setName The name of the set.
     * @return The settings set in the database.
     * @throws DBException In case of an error, this will be thrown.
     */
    SettingsSet loadSettingsSet(String setName) throws DBException;
    void applySettingsSet(SettingsSet set);
    Circuit getCircuit();
    void startSimulation();
    void stopSimulation();
}
