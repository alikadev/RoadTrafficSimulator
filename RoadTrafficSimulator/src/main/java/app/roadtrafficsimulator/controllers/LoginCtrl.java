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
        boolean failed = false;

        // Clean fields styles
        signinAccount.getStyleClass().removeAll("field_error");
        signinPasswd.getStyleClass().removeAll("field_error");
        signinPasswdCheck.getStyleClass().removeAll("field_error");

        // Check if required fields are not empty
        if (signinAccount.getText().isEmpty()) {
            signinAccount.getStyleClass().add("field_error");
            failed = true;
        }

        if (signinPasswd.getText().isEmpty()) {
            signinPasswd.getStyleClass().add("field_error");
            failed = true;
        }

        if (signinPasswdCheck.getText().isEmpty()) {
            signinPasswdCheck.getStyleClass().add("field_error");
            failed = true;
        }

        // Check if password and passwordCheck are the same
        if (!signinPasswdCheck.getText().equals(signinPasswd.getText())) {
            signinPasswdCheck.getStyleClass().add("field_error");
        }

        // Create the account
        if (failed) return;
        try {
            Account account = new Account(signinAccount.getText(), signinPasswd.getText());
            wrk.createAccount(account);
        } catch (DBException e) {
            System.err.println(e.getMessage());
        }
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
