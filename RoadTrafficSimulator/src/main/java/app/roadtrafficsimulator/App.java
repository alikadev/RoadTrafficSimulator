package app.roadtrafficsimulator;

import app.roadtrafficsimulator.controllers.ICtrl;
import app.roadtrafficsimulator.exceptions.DBException;
import app.roadtrafficsimulator.helper.EasyPopup;
import app.roadtrafficsimulator.workers.Wrk;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the JavaFX application class. This is where everything starts for us.
 */
public class App extends Application {

    /**
     * The login view path.
     */
    public static final String LOGIN_VIEW = "views/Login.fxml";

    /**
     * The simulation view path.
     */
    public static final String SIMULATION_VIEW = "views/Simulation.fxml";

    /**
     * The window title.
     */
    private static final String TITLE = "Road Traffic Simulator";

    /**
     * The database config for localhost absolute path.
     */
    public static final String DB_CONFIG_LOCALHOST = "app/roadtrafficsimulator/config/db/localhost.properties";

    /**
     * The road texture absolute path.
     */
    public static final String ROAD_TEXTURE = "app/roadtrafficsimulator/textures/road.png";

    /**
     * The car texture absolute path base.
     * The format is `BASE + id + EXTENSION`.
     */
    public static final String CAR_TEXTURE_BASE = "app/roadtrafficsimulator/textures/car";

    /**
     * The car texture starting index.
     */
    public static final int CAR_TEXTURE_START = 1;

    /**
     * The car texture last index.
     */
    public static final int CAR_TEXTURE_END = 6;

    /**
     * The car texture extension.
     */
    public static final String CAR_TEXTURE_EXTENSION = ".png";

    /**
     * This is the constructor of the Application.
     */
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

    /**
     * When you need to load/change the view, call that and your dreams will come true...
     *
     * @param path The view path. Please, use the constants in top of this class...
     */
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

    /**
     * The controller interface's instance.
     */
    private ICtrl ctrl;

    /**
     * The worker's instance
     */
    private final Wrk wrk;

    /**
     * The parent view.
     */
    private Parent view;

    /**
     * The JavaFX scene...
     */
    private Scene scene;

    /**
     * The JavaFX stage...
     */
    private Stage stage;
}