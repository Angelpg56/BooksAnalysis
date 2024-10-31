package edu.psp.booksanalysis.utils;

import javafx.scene.control.Alert;

/**
 * Utility class for displaying messages to the user.
 */
public class MessageUtils {

    /**
     * Displays an error message to the user.
     *
     * @param header  the header text of the error message
     * @param message the content text of the error message
     */
    public static void showError(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Displays a warning message to the user.
     *
     * @param header  the header text of the warning message
     * @param message the content text of the warning message
     */
    public static void showMessage(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Displays a confirmation message to the user.
     *
     * @param header  the header text of the confirmation message
     * @param message the content text of the confirmation message
     */
    public static boolean showConfirmation(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();

        return alert.getResult().getButtonData().isDefaultButton();
    }
}