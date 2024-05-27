package app.roadtrafficsimulator;

import app.roadtrafficsimulator.controllers.IAppCtrl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final String FXML = "views/Login.fxml";
    private static final String TITLE = "Road Traffic Simulator";
    
    @Override
    public void start(Stage stage) {
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
        
        IAppCtrl ctrl = loader.getController();
        Scene scene = new Scene(mainView, 800, 600);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle(TITLE);
        
        stage.setOnCloseRequest(e -> {
            e.consume();
            ctrl.terminate();
        });
        
        ctrl.start();
        stage.show();
    }
}