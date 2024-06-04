package app.roadtrafficsimulator;

import app.roadtrafficsimulator.beans.Account;
import app.roadtrafficsimulator.controllers.ICtrl;
import app.roadtrafficsimulator.controllers.LoginCtrl;
import app.roadtrafficsimulator.exceptions.DBException;
import app.roadtrafficsimulator.helper.EasyPopup;
import app.roadtrafficsimulator.workers.Wrk;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URL;

/**
 * JavaFX App
 */
public class App extends Application {

    public static final String LOGIN_VIEW = "views/Login.fxml";
    public static final String SIMULATION_VIEW = "views/Simulation.fxml";
    private static final String TITLE = "Road Traffic Simulator";
    public static final String DB_CONFIG_LOCALHOST = "app/roadtrafficsimulator/config/db/localhost.properties";
    public static final String ROAD_TEXTURE = "app/roadtrafficsimulator/textures/road.png";
    public static final int CAR_TEXTURE_START = 1;
    public static final int CAR_TEXTURE_END = 6;
    public static final String CAR_TEXTURE_BASE = "app/roadtrafficsimulator/textures/car";
    public static final String CAR_TEXTURE_EXTENSION = ".png";

    public App() {
        super();

        // Init local vars
        ctrl = null;
        wrk = new Wrk(); // Prefabricate the worker now, it will not change during runtime
        view = null;
        scene = null;
    }

    @Override
    public void start(Stage theStage) {
        // Prepare window
        stage = theStage;
        stage.setResizable(true);
        stage.setTitle(TITLE);

        // Manage common interrupts
        stage.setOnCloseRequest(e -> {
            e.consume();
            wrk.terminate();
            ctrl.terminate();
        });

        // Load the basic view
        loadView(LOGIN_VIEW);

        // Start!
        try {
            wrk.start();
            stage.show();
        } catch (DBException e) {
            EasyPopup.displayError("Erreur fatal", "Un problème s'est produit durant le lancement de l'application", e.getMessage(), true);
        }
    }

    public void loadView(final String path) {
        // Load the view
        FXMLLoader loader;
        try {
            loader = new FXMLLoader(getClass().getResource(path));
            view = loader.load();
        } catch (IllegalStateException | IOException e) {
            throw new RuntimeException("Failed to load view " + path + ": " + e.getMessage());
        }

        // Prepare MVC2
        ctrl = loader.getController();
        ctrl.setWrk(wrk);
        wrk.setCtrl(ctrl);
        ctrl.setApp(this);

        // Prepare scene
        double width = scene == null ? 800 : scene.getWidth();
        double height = scene == null ? 600 : scene.getHeight();
        scene = new Scene(view, width, height);
        stage.setScene(scene);

        ctrl.start();
    }

    private ICtrl ctrl;
    private final Wrk wrk;
    private Parent view;
    private Scene scene;
    private Stage stage;
}