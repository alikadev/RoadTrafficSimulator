package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.App;
import app.roadtrafficsimulator.beans.*;
import app.roadtrafficsimulator.exceptions.DBException;
import app.roadtrafficsimulator.helper.EasyPopup;
import app.roadtrafficsimulator.helper.FX;
import app.roadtrafficsimulator.workers.ICtrlWrk;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.*;

/**
 * This is the controller that will manage the edition and visualisation of the simulation.
 *
 * @author Elvin Kuci
 */
public class SimulationCtrl implements ICtrl {

    /**
     * This is the default font setting for a title.
     */
    private static final Font FONT_TITLE = new Font(20);

    /**
     * Create the simulation controller.
     */
    public SimulationCtrl() {
        // Standards...
        wrk = null;
        app = null;

        // Setup static fields and box.
        settingsField = new TextField("");
        pixelPerMeter = FX.set(new TextField("6"), (node) -> node.setOnAction(this::updatePixelPerMeter));
        speedFactor = FX.set(new TextField("1"), (node) -> node.setOnAction(this::checkSpeedFactor));
        vehicleSettings = new VBox();
        setList = new ListView<String>();

        // Create the settings tab.
        settingsTab = new Tab("Réglages",
                FX.set(new VBox(10,
                        new ScrollPane(setList),
                        new HBox(10,
                                FX.set(new Button("Charger"), node -> node.setOnMouseClicked(this::loadSettings)),
                                FX.set(new Button("Sauvegarder"), node -> node.setOnMouseClicked(this::saveSettings))
                        ),
                        new VBox(new Label("Nom du jeu de réglage"), settingsField),
                        FX.set(new Button("Créer un jeu de réglage"), node -> node.setOnMouseClicked(this::createSettings))
                ), node -> { VBox.setVgrow(node, Priority.ALWAYS); node.getStyleClass().add("tab_content"); })
        );

        // Create the general tab
        generalTab = new Tab("Général",
                FX.set(new VBox(10,
                        FX.set(new Label("Simulation"), node -> node.setFont(FONT_TITLE)),
                        new VBox(new Label("Pixels par mètre"), pixelPerMeter),
                        new VBox(new Label("Facteur de vitesse"), speedFactor),
                        FX.set(new Label("Véhicules"), node -> node.setFont(FONT_TITLE)),
                        vehicleSettings // The wrk might still be null during this phase. We'll load these settings on start instead
                ), node -> { VBox.setVgrow(node, Priority.ALWAYS); node.getStyleClass().add("tab_content"); })
        );
    }

    /**
     * This methode is called when the simulation state needs to be toggled.
     *
     * @param ev The event.
     */
    @FXML
    public void toggleSimulation(ActionEvent ev) {
        // Toggle the edit menu visibility.
        editMenu.setVisible(!editMenu.isVisible());

        if (editMenu.isVisible()) {
            // Simulation is stopping
            simulationBtn.setText("Démarrer la simulation");
            editMenu.setPrefWidth(200);
            wrk.stopSimulation();
        }
        else {
            // Simulation is starting
            simulationBtn.setText("Arrêter la simulation");
            editMenu.setPrefWidth(0);
            wrk.startSimulation();
        }
    }

    /**
     * This methode is called when the user update the pixel per meter.
     *
     * @param ev The event.
     */
    private void updatePixelPerMeter(ActionEvent ev) {
        double ppm;
        // Parse the value
        try {
            ppm = Double.parseDouble(pixelPerMeter.getText());
        } catch (NumberFormatException e) {
            EasyPopup.displayError("Erreur de format", "Un nombre était attendu", "Le format du champ est invalide!", true);
            pixelPerMeter.setText("1");
            ppm = 1;
        }

        // Apply it to the foreground and background.
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
        // Check if exactly 1 element in the list is selected
        List<String> selected = setList.getSelectionModel().getSelectedItems();

        if (selected.isEmpty()) {
            EasyPopup.displayError("Erreur", "Aucun jeu de réglages n'a été séléctionnée", "Vous devez séléctionner un jeu de réglages pour effectuer cette opération!", true);
            return;
        } else if (selected.size() > 1) {
            EasyPopup.displayError("Erreur", "Trop jeux de réglages sont été séléctionnées", "Vous ne devez séléctionner qu'un seul jeu de réglages pour effectuer cette opération!", true);
            return;
        }

        String setName = selected.get(0);

        // Load the settings
        SettingsSet set;

        try {
            set = wrk.loadSettingsSet(setName);
        } catch (DBException e) {
            EasyPopup.displayError("Erreur lors de la sauvegarde", "Une erreur c'est produite durant la sauvegarde du jeu de réglages.", e.getMessage(), false);
            return;
        }

        // Apply the set
        wrk.applySettingsSet(set);

        EasyPopup.displayInfo("Succès", "Succès durant le chargement du jeu de réglages", "Le jeu de réglages `" + setName + "` à bel et bien été charger!", false);
    }

    /**
     * Save settings to the database.
     *
     * @param ev The event, ignored.
     */
    private void saveSettings(MouseEvent ev) {
        // Check if exactly 1 element in the list is selected
        List<String> selected = setList.getSelectionModel().getSelectedItems();

        if (selected.isEmpty()) {
            EasyPopup.displayError("Erreur", "Aucun jeu de réglages n'a été séléctionnée", "Vous devez séléctionner un jeu de réglages pour effectuer cette opération!", true);
            return;
        } else if (selected.size() > 1) {
            EasyPopup.displayError("Erreur", "Trop jeux de réglages sont été séléctionnées", "Vous ne devez séléctionner qu'un seul jeu de réglages pour effectuer cette opération!", true);
            return;
        }

        String setName = selected.get(0);

        // Get circuit's settings
        Map<String, Double> map = new HashMap<>();

        for (Roadable rd : wrk.getCircuit().getRoads()) {
            map.putAll(rd.getSettings());
        }

        // Save them
        try {
            wrk.updateSettingsSet(new SettingsSet(setName, map));
        } catch (DBException e) {
            EasyPopup.displayError("Erreur lors de la sauvegarde", "Une erreur c'est produite durant la sauvegarde du jeu de réglages.", e.getMessage(), false);
            return;
        }

        // Add and confirm success to the user
        EasyPopup.displayInfo("Succès", "Succès durant la création du jeu de réglages", "Le jeu de réglages `" + setName + "` à bel et bien été sauvegarder!", false);
    }

    /**
     * Create new settings group in the database.
     *
     * @param ev The event, ignored.
     */
    private void createSettings(MouseEvent ev) {
        // Check if the required field is not empty
        String setName = settingsField.getText();

        if (setName.isEmpty()) {
            settingsField.getStyleClass().add("field_error");
            return;
        }

        // Get circuit's settings
        Map<String, Double> map = new HashMap<>();

        for (Roadable rd : wrk.getCircuit().getRoads()) {
            map.putAll(rd.getSettings());
        }

        // Save them
        try {
            wrk.createSettingsSet(new SettingsSet(setName, map));
        } catch (DBException e) {
            EasyPopup.displayError("Erreur lors de la sauvegarde", "Une erreur c'est produite durant la sauvegarde du jeu de réglages.", e.getMessage(), false);
            return;
        }

        // Add and confirm success to the user
        setList.getItems().add(setName);

        EasyPopup.displayInfo("Succès", "Succès durant la création du jeu de réglages", "Le jeu de réglages `" + setName + "` à bel et bien été sauvegarder!", false);
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
        // Load all settings set
        try {
            List<String> sets = wrk.getSettingsSetsList();
            setList.getItems().setAll(sets);
        } catch (DBException e) {
            EasyPopup.displayError("Erreur lors du chargement", "Une erreur c'est produite durant le chargement de la liste de jeu de réglages.", e.getMessage(), false);
        }

        // Load vehicle settings
        for (InputField in : wrk.getCircuit().getDefaultVehicle().getProperties()) {
            vehicleSettings.getChildren().add(
                    new VBox(
                            new Label(in.getValueLabel()),
                            FX.set(new TextField(), t -> t.textProperty().bindBidirectional(in.valueProperty()))
                            // TODO: Add tolerance
                    )
            );
        }

        editMenu.getTabs().add(settingsTab);
        editMenu.getTabs().add(generalTab);

        // Draw roads
        renderRoads();

        // First update of the pixel per meter
        updatePixelPerMeter(null);
    }

    /**
     * Open the edit menu for a road.
     *
     * @param rd The road
     */
    private void openRoadEditMenu(Roadable rd) {
        // Close last road tab if it exists
        editMenu.getTabs().remove(roadTab);

        // Fill the road settings
        VBox roadSettings = new VBox();

        for (InputField in : rd.getProperties()) {
            roadSettings.getChildren().add(
                    new VBox(
                            new Label(in.getValueLabel()),
                            FX.set(new TextField(), t -> t.textProperty().bindBidirectional(in.valueProperty()))
                    )
            );
        }

        // Create the tab
        roadTab = new Tab("Route",
                FX.set(new VBox(10,
                        roadSettings
                ), node -> { VBox.setVgrow(node, Priority.ALWAYS); node.getStyleClass().add("tab_content"); })
        );

        // TODO: Implement and add traffic lights tab

        // Open the tab
        editMenu.getTabs().add(roadTab);
        editMenu.getSelectionModel().select(roadTab);
        editMenu.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != roadTab) {
                editMenu.getTabs().remove(roadTab);
            }
        });

    }

    /**
     * Render the roads of the circuit.
     */
    public void renderRoads() {
        Circuit c = wrk.getCircuit();
        // Clear the pre-existing roads
        background.getChildren().clear();

        for (Roadable rd : c.getRoads()) {
            // Draw the road.
            Node node = rd.draw();
            // Set up the on-click callback
            node.setOnMouseClicked(e -> openRoadEditMenu(rd));
            // Center the road, (0,0) is in the rendering-zone center
            node.translateXProperty().bind(background.widthProperty().divide(2.f));
            node.translateYProperty().bind(background.heightProperty().divide(2.f));
            // Add the element to the rendering-zone
            background.getChildren().add(node);
        }
    }

    @Override
    public void addVehicle(Vehicle v) {
        Platform.runLater(() -> {
            // Draw the road
            Node node = v.draw();
            // Center the road, (0,0) is in the rendering-zone center
            node.translateXProperty().bind(foreground.widthProperty().divide(2.f));
            node.translateYProperty().bind(foreground.heightProperty().divide(2.f));
            // Add the vehicle to the rendering-zone
            foreground.getChildren().add(node);
        });
    }

    @Override
    public void removeVehicle(Vehicle v) {
        Platform.runLater(() -> {
            Node vn = v.draw();
            // Find the equivalent vehicle in the rendering-zone.
            for (Node n : foreground.getChildren()) {
                // Compare by position, OK for now
                if (n.getLayoutX() == vn.getLayoutX()
                        && n.getLayoutY() == vn.getLayoutY()) {
                    // Remove the element
                    foreground.getChildren().remove(n);
                    break;
                }
            }
        });
    }

    /**
     * Check if the speed factor input is parsable. It is not stored anywhere, it's just a verification!
     *
     * @param ev The event.
     */
    void checkSpeedFactor(ActionEvent ev) {
        try {
            Double.parseDouble(speedFactor.getText());
        } catch (NumberFormatException e) {
            EasyPopup.displayError("Erreur de format", "Un nombre était attendu", "Le format du champ est invalide!", true);
            // Reset the value
            speedFactor.setText("1");
        }
    }

    @Override
    public double getSpeedFactor() {
        return Double.parseDouble(speedFactor.getText());
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
     * This is the place where the vehicle settings will be put in...
     */
    private final VBox vehicleSettings;

    /**
     * The list of sets that are currently available.
     */
    private final ListView<String> setList;

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
    private final Tab settingsTab;

    /**
     * The general data about the simulation tab.
     */
    private final Tab generalTab;

    /**
     * This is the tab that contains a road settings.
     */
    private Tab roadTab;

    /**
     * The settings text field.
     */
    private final TextField settingsField;

    /**
     * The `Pixel per Meter` text field.
     */
    private final TextField pixelPerMeter;

    /**
     * The `Speed Factor`.
     */
    private final TextField speedFactor;
}
