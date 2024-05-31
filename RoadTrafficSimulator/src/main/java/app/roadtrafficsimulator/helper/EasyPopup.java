package app.roadtrafficsimulator.helper;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

/**
 * Create, manage and display a simple informational popups
 *
 * @author Elvin Kuci
 */
public class EasyPopup {
    /**
     * Create and display an error popup.
     * @param title The title of the popup's window
     * @param header The header of the popup
     * @param msg The message in the popup
     * @param blocking Define if the popup is blocking the process or not
     */
    public static void displayError(String title, String header, String msg, boolean blocking) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(null);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        if (blocking) alert.showAndWait();
        else alert.show();
    }

    /**
     * Create and display an information popup.
     * @param title The title of the popup's window
     * @param header The header of the popup
     * @param msg The message in the popup
     * @param blocking Define if the popup is blocking the process or not
     */
    public static void displayInfo(String title, String header, String msg, boolean blocking) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(null);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        if (blocking) alert.showAndWait();
        else alert.show();
    }
}
