package app.roadtrafficsimulator;

import app.roadtrafficsimulator.controllers.ICtrl;
import app.roadtrafficsimulator.controllers.LoginCtrl;
import app.roadtrafficsimulator.exceptions.DBException;
import app.roadtrafficsimulator.helper.EasyPopup;
import app.roadtrafficsimulator.workers.Wrk;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URL;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final String DB_CONFIG_LOCALHOST = "config/db/localhost.properties";
    private static final String FXML = "views/Login.fxml";
    private static final String TITLE = "Road Traffic Simulator";
    
    @Override
    public void start(Stage stage) {
        // Check config file paths
        URL dbConfig = getClass().getResource(DB_CONFIG_LOCALHOST);
        if (dbConfig == null) throw new RuntimeException("Le fichier de configuration de la base de donnée n'a pas été trouvé!");

        // Load the view
        FXMLLoader loader;
        Parent mainView;
        try {
            loader = new FXMLLoader(getClass().getResource(FXML));
            mainView = loader.load();
        } catch (IllegalStateException | IOException e) {
            System.err.println("Error while loading '" + FXML + "'");
            System.err.println(e.getCause().getMessage()); 
            return;
        }

        // Init MVC2
        Wrk wrk = new Wrk(dbConfig);
        ICtrl ctrl = loader.getController();
        ctrl.setWrk(wrk);
        wrk.setCtrl(ctrl);
        Scene scene = new Scene(mainView, 800, 600);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle(TITLE);

        // Manage common interrupts
        stage.setOnCloseRequest(e -> {
            e.consume();
            wrk.terminate();
            ctrl.terminate();
        });

        // Start!
        try {
            ctrl.start();
            wrk.start();
            stage.show();
        } catch (DBException e) {
            EasyPopup.displayError("Erreur fatal", "Un problème s'est produit durant le lancement de l'application", e.getMessage(), true);
        }
    }
}