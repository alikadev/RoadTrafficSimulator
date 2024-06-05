package app.controllers;

import app.workers.Wrk;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Main view Controller
 * @author kucie
 */
public class MainViewCtrl implements Initializable {

    /**
     * Initialise the MainView Controller.
     * @param url The location
     * @param rb The ressource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wrk = new Wrk();
        wrk.setCtrl(this);
        wrk.start();
    }

    /**
     * Close the application.
     */
    public void quit() {
        wrk.stop();
        wrk = null;
        Platform.exit();
    }
    
    public void setArrowPosition(double x, double y) {
        arrow.setLayoutX(x);
        arrow.setLayoutY(y);
    }
    
    public void setArrowRotation(double angle) {
        arrow.setRotate(angle);
    }
    
    public double getLayerWidth() {
        return layer.getWidth();
    }
    
    public double getLayerHeight() {
        return layer.getHeight();
    }
    
    public double getArrowWidth() {
        return arrow.getFitWidth();
    }
    
    public double getArrowHeight() {
        return arrow.getFitHeight();
    }
    
    /**
     * The reference to the worker.
     */
    private Wrk wrk;
    
    /**
     * The current layer.
     */
    @FXML
    private AnchorPane layer;
    
    /**
     * The arrow to move.
     */
    @FXML
    private ImageView arrow;
    
}
