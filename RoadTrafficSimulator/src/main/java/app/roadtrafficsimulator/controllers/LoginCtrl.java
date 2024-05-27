/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.workers.ICtrlWrk;
import javafx.application.Platform;

/**
 * The controller of the "Login and SignIn" view of the application.
 *
 * @author kucie
 */
public class LoginCtrl implements IAppCtrl {

    /**
     * Create the login controller.
     */
    public LoginCtrl() {
        wrk = null;
    }

    @Override
    public void start() {
    }

    @Override
    public void terminate() {
        Platform.exit();
    }

    @Override
    public void setWrk(ICtrlWrk wrk) {
        this.wrk = wrk;
    }
    
    /**
     * The reference to the current worker.
     */
    ICtrlWrk wrk;
}
