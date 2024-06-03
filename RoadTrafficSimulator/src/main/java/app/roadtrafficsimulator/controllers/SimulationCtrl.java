package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.App;
import app.roadtrafficsimulator.beans.Circuit;
import app.roadtrafficsimulator.beans.Roadable;
import app.roadtrafficsimulator.beans.Vec2;
import app.roadtrafficsimulator.beans.Vehicle;
import app.roadtrafficsimulator.exceptions.NotImplementedYet;
import app.roadtrafficsimulator.helper.EasyPopup;
import app.roadtrafficsimulator.helper.FX;
import app.roadtrafficsimulator.helper.Physics;
import app.roadtrafficsimulator.workers.ICtrlWrk;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * This is the controller that will manage the edition and visualisation of the simulation.
 */
public class SimulationCtrl implements ICtrl {

    private static final Font FONT_TITLE = new Font(20);

    public SimulationCtrl() {
        wrk = null;
        app = null;


        settingsField = new TextField("1");
        pixelPerMeter = FX.set(new TextField("6"), (node) -> node.setOnAction(this::updatePixelPerMeter));
        speedFactor = new TextField("1");

        settingsTab = new Tab("Réglages",
                FX.set(new VBox(10,
                        new ScrollPane(new ListView<String>()),
                        new HBox(10,
                                FX.set(new Button("Charger"), node -> node.setOnMouseClicked(this::loadSettings)),
                                FX.set(new Button("Sauvegarder"), node -> node.setOnMouseClicked(this::saveSettings))
                        ),
                        new VBox(new Label("Nom du jeu de réglage"), settingsField),
                        FX.set(new Button("Créer un jeu de réglage"), node -> node.setOnMouseClicked(this::createSettings))
                ), node -> { VBox.setVgrow(node, Priority.ALWAYS); node.getStyleClass().add("tab_content"); })
        );

        generalTab = new Tab("Général",
                FX.set(new VBox(10,
                        FX.set(new Label("Simulation"), node -> node.setFont(FONT_TITLE)),
                        new VBox(new Label("Pixels par mètre"), pixelPerMeter),
                        new VBox(new Label("Facteur de vitesse"), speedFactor),
                        FX.set(new Label("Véhicules"), node -> node.setFont(FONT_TITLE))
                ), node -> { VBox.setVgrow(node, Priority.ALWAYS); node.getStyleClass().add("tab_content"); })
        );
    }

    @FXML
    public void toggleSimulation(ActionEvent ev) {
        editMenu.setVisible(!editMenu.isVisible());
        if (editMenu.isVisible()) {
            simulationBtn.setText("Démarrer la simulation");
            editMenu.setPrefWidth(200);
        }
        else {
            simulationBtn.setText("Arrêter la simulation");
            editMenu.setPrefWidth(0);
        }
    }

    /**
     * Update the pixel per meter render.
     * @param ev The event, ignored.
     */
    private void updatePixelPerMeter(ActionEvent ev) {
        double ppm;
        try {
            ppm = Double.parseDouble(pixelPerMeter.getText());
        } catch (NumberFormatException e) {
            EasyPopup.displayError("Erreur de format", "Un nombre était attendu", "Le format du champ est invalide!", true);
            pixelPerMeter.setText("1");
            ppm = 1;
        }

        background.setScaleX(ppm);
        background.setScaleY(ppm);

        foreground.setScaleX(ppm);
        foreground.setScaleY(ppm);
    }

    /**
     * Load settings from the database.
     * @param ev The event, ignored.
     */
    private void loadSettings(MouseEvent ev) {
        throw new NotImplementedYet("loadSettings");
    }

    /**
     * Save settings to the database.
     * @param ev The event, ignored.
     */
    private void saveSettings(MouseEvent ev) {
        throw new NotImplementedYet("saveSettings");
    }

    /**
     * When you click on a road, this will be triggerd
     * @param ev The event, ignored.
     */
    private void editRoad(MouseEvent ev) {
        throw new NotImplementedYet("saveSettings");
    }

    /**
     * Create new settings group in the database.
     * @param ev The event, ignored.
     */
    private void createSettings(MouseEvent ev) {
        throw new NotImplementedYet("createSettings");
    }

    @Override
    public void setWrk(ICtrlWrk wrk) {
        this.wrk = wrk;
    }

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    @Override
    public void start() {
        editMenu.getTabs().add(settingsTab);
        editMenu.getTabs().add(generalTab);

        // Draw roads
        render();

        // First update of the pixel per meter
        updatePixelPerMeter(null);
    }

    @Override
    public void render() {
        renderRoads();
        renderVehicles();
    }

    /**
     * Render the roads of the circuit.
     */
    public void renderRoads() {
        Circuit c = wrk.getCircuit();
        background.getChildren().clear();
        for (Roadable rd : c.getRoads()) {
            Node node = rd.draw();
            node.setOnMouseClicked(this::editRoad);
            node.translateXProperty().bind(background.widthProperty().divide(2.f));
            node.translateYProperty().bind(background.heightProperty().divide(2.f));
            background.getChildren().add(node);
        }
    }

    /**
     * render the vehicles of the circuit.
     */
    public void renderVehicles() {
        Circuit c = wrk.getCircuit();
        foreground.getChildren().clear();
        for (Vehicle v : c.getVehicles()) {
            Node node = v.draw();
            node.setOnMouseClicked(this::editRoad);
            node.translateXProperty().bind(foreground.widthProperty().divide(2.f));
            node.translateYProperty().bind(foreground.heightProperty().divide(2.f));
            foreground.getChildren().add(node);
        }
    }

    @Override
    public void terminate() {
        Platform.exit();
    }

    /**
     * The reference to the worker.
     */
    private ICtrlWrk wrk;

    /**
     * The reference to the app.
     */
    private App app;

    /**
     * The background of the simulation
     */
    @FXML
    private AnchorPane background;

    /**
     * The foreground of the simulation space.
     */
    @FXML
    private AnchorPane foreground;

    /**
     * The right menu with the tabs. YOU need to set its content by hand!
     */
    @FXML
    private TabPane editMenu;

    /**
     * The button used to toggle the simulation state.
     */
    @FXML
    private Button simulationBtn;

    /**
     * The settings tab.
     */
    private Tab settingsTab;

    /**
     * The general data about the simulation tab.
     */
    private Tab generalTab;

    /**
     * The settings text field.
     */
    private TextField settingsField;

    /**
     * The `Pixel per Meter` text field.
     */
    private TextField pixelPerMeter;

    /**
     * The `Speed Factor`.
     */
    private TextField speedFactor;
}
