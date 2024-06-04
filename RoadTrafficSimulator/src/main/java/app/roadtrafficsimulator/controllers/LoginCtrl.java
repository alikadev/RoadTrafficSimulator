package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.App;
import app.roadtrafficsimulator.beans.Account;
import app.roadtrafficsimulator.beans.Vehicle;
import app.roadtrafficsimulator.exceptions.DBException;
import app.roadtrafficsimulator.exceptions.LoginException;
import app.roadtrafficsimulator.exceptions.UnexpectedException;
import app.roadtrafficsimulator.helper.EasyPopup;
import app.roadtrafficsimulator.workers.ICtrlWrk;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Popup;

/**
 * The controller of the "Login and SignIn" view of the application.
 *
 * @author kucie
 */
public class LoginCtrl implements ICtrl {

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
        boolean failed = false;

        // Clean fields styles
        loginAccount.getStyleClass().removeAll("field_error");
        loginAccount.getStyleClass().removeAll("field_error");

        // Check if required fields are not empty
        if (loginAccount.getText().isEmpty()) {
            loginAccount.getStyleClass().add("field_error");
            failed = true;
        }

        if (loginPasswd.getText().isEmpty()) {
            loginPasswd.getStyleClass().add("field_error");
            failed = true;
        }

        if (failed) return;

        try {
            // Vérification du mot de passe
            if (!wrk.verifyAccount(new Account(loginAccount.getText(), loginPasswd.getText())))
                throw new LoginException("Le nom du compte et/ou le mot de passe ne sont pas le bon");

            nextView();
        } catch (LoginException | DBException e) {
            EasyPopup.displayError("Erreur", "Erreur durant la connection", e.getMessage(), true);
        }
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

        if (failed) return;

        // Create the account
        try {
            Account account = new Account(signinAccount.getText(), signinPasswd.getText());
            wrk.createAccount(account);
            nextView();
        } catch (DBException e) {
            EasyPopup.displayError("Erreur", "Erreur durant la création du compte.", e.getMessage(), true);
        }
    }

    @Override
    public void addVehicle(Vehicle v) {
        throw new UnexpectedException("You can't add a vehicle on the login view...");
    }

    @Override
    public void removeVehicle(Vehicle v) {
        throw new UnexpectedException("You can't remove a vehicle from the login view...");
    }

    @Override
    public double getSpeedFactor() {
        throw new UnexpectedException("You can't get the speed factor of the login view...");
    }

    private void nextView() {
        app.loadView(App.SIMULATION_VIEW);
    }

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    public void setWrk(ICtrlWrk wrk) {
        this.wrk = wrk;
    }

    /**
     * The reference to the current worker.
     */
    ICtrlWrk wrk;
    /**
     * The reference to the current worker.
     */
    App app;
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
