/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.roadtrafficsimulator.workers;

import app.roadtrafficsimulator.beans.Account;
import app.roadtrafficsimulator.beans.Circuit;
import app.roadtrafficsimulator.exceptions.DBException;

/**
 * Interface du controller envers le worker
 *
 * @author Elvin Kuci
 */
public interface ICtrlWrk {
    void createAccount(Account account) throws DBException;
    boolean verifyAccount(Account account) throws DBException;
    Circuit getCircuit();
}
