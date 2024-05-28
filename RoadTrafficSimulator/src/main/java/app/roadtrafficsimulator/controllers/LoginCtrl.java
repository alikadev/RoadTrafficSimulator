package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.beans.Account;
import app.roadtrafficsimulator.exceptions.DBException;
import app.roadtrafficsimulator.exceptions.LoginException;
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

            EasyPopup.displayInfo("Succès", "Succès durant la connection", "Vous êtes bel et bien connecté", true);
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
            EasyPopup.displayInfo("Succès", "Succès lors de la création du compte", "Votre compte à bel et bien été créer!", true);
        } catch (DBException e) {
            EasyPopup.displayError("Erreur", "Erreur durant la création du compte.", e.getMessage(), true);
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
