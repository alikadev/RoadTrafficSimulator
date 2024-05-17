package app.controllers;

import app.workers.Wrk;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
        bindLabelToInputText();
    }

    /**
     * Close the application.
     */
    public void quit() {
        wrk = null;
        Platform.exit();
    }
    
    /**
     * Update the list content
     * @param values The list of values to put in the list
     */
    public void updateList(List<String> values) {
        // Use of Platform.runLater because it will be called by an external thread.
        Platform.runLater(() -> listTask.getItems().setAll(values));
    }
    
    /**
     * Bind the label to the input and enable the buttonRunTask.
     */
    private void bindLabelToInputText() {
        label.textProperty().bind(textLabel.textProperty());
        buttonRunTask.setDisable(false);
    }
    
    /**
     * Unbind the label from the input and disable the buttonRunTask.
     */
    private void unbindLabelToInputText() {
        label.textProperty().unbind();
        buttonRunTask.setDisable(true);
    }
    
    /**
     * Handle the button click and create a data fetching task.
     * @param ev The event
     */
    @FXML
    private void handleButtonClick(ActionEvent ev) {
        // Set label's loading text
        unbindLabelToInputText();
        label.setText("Fetching data...");
        
        // Create a task to set the new string value
        Task<Void> fetchTask = new Task() {
            @Override
            protected Void call() throws Exception {
                // Simulate a long operation (1 second)
                wrk.fetchData();
                return null;
            }
        };
        
        // On task success, rebind the text
        fetchTask.setOnSucceeded((WorkerStateEvent e) -> {
            bindLabelToInputText();
        });
        
        // Start the task
        new Thread(fetchTask).start();
    }
    
    /**
     * The reference to the worker.
     */
    Wrk wrk;
    
    /**
     * The label.
     */
    @FXML
    private Label label;
    
    /**
     * The textfield that edit the label.
     */
    @FXML
    private TextField textLabel;
    
    /**
     * The button that run a task.
     */
    @FXML
    private Button buttonRunTask;
    
    /**
     * The list of the data fetched by the task.
     */
    @FXML
    private ListView<String> listTask;
}
