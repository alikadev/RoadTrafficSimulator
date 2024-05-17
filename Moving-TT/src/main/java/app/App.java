package app;

import app.controllers.MainViewCtrl;
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

    private static final String FXML = "views/MainView.fxml";
    private static final String TITLE = "Movement Test Technologique";
    
    @Override
    public void start(Stage stage) {
        FXMLLoader loader;
        Parent mainView;
        try {
            System.out.println(getClass().getResource(FXML));
            loader = new FXMLLoader(getClass().getResource(FXML));
            mainView = loader.load();
        } catch (IllegalStateException | IOException e) {
            System.err.println("Error while loading '" + FXML + "'");
            System.err.println(e.getCause().getMessage()); 
            return;
        }
        
        MainViewCtrl mainCtrl = loader.getController();
        Scene scene = new Scene(mainView, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(TITLE);
        stage.show();
        stage.setOnCloseRequest(e -> {
            e.consume();
            mainCtrl.quit();
        });
    }
}