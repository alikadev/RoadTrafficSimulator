package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.App;
import app.roadtrafficsimulator.beans.Circuit;
import app.roadtrafficsimulator.beans.Roadable;
import app.roadtrafficsimulator.helper.FX;
import app.roadtrafficsimulator.workers.ICtrlWrk;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * This is the controller that will manage the edition and visualisation of the simulation.
 */
public class SimulationCtrl implements ICtrl {

    private static final Font FONT_TITLE = new Font(20);

    public SimulationCtrl() {
        wrk = null;
        app = null;


        settingsField = new TextField();
        pixelPerMeter = new TextField();
        speedFactor = new TextField();

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
        if (editMenu.isVisible())
            simulationBtn.setText("Démarrer la simulation");
        else
            simulationBtn.setText("Arrêter la simulation");
    }

    private void loadSettings(MouseEvent ev) {
    }

    private void saveSettings(MouseEvent ev) {
    }

    private void createSettings(MouseEvent ev) {
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
        Circuit c = wrk.getCircuit();
        for (Roadable rd : c.getRoads()) {
            throw new RuntimeException("Not implemented yet");
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

    private Tab settingsTab;
    private Tab generalTab;
    private TextField settingsField;
    private TextField pixelPerMeter;
    private TextField speedFactor;
}
