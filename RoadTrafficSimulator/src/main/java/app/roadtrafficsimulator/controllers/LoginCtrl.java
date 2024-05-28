package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.beans.Account;
import app.roadtrafficsimulator.exceptions.DBException;
import app.roadtrafficsimulator.workers.ICtrlWrk;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * The controller of the "Login and SignIn" view of the application.
 *
 * @author kucie
 */
public class LoginCtrl implements IWrkCtrl {

    /**
     * Create the login controller.
     */
    public LoginCtrl() {
        wrk = null;
    }

    public void start() {
    }

    public void terminate() {
        Platform.exit();
    }
    
    @FXML
    private void logIn(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @FXML
    private void signIn(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setWrk(ICtrlWrk wrk) {
        this.wrk = wrk;
    }
    
    /**
     * The reference to the current worker.
     */
    ICtrlWrk wrk;
    

    /**
     * The login's side "Account" textfield
     */
    @FXML
    private TextField loginAccount;
    /**
     * The login's side "Password" textfield
     */
    @FXML
    private PasswordField loginPasswd;
    /**
     * The signin's side "Account" textfield
     */
    @FXML
    private TextField signinAccount;
    /**
     * The signin's side "Password" textfield
     */
    @FXML
    private PasswordField signinPasswd;
    /**
     * The signin's side "Password Confirmation" textfield
     */
    @FXML
    private PasswordField signinPasswdCheck;
}
