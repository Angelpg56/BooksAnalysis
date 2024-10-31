package edu.psp.booksanalysis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for the Library Analysis application.
 */
public class MainApplication extends Application {

    /**
     * Starts the JavaFX application.
     *
     * @param stage the primary stage for this application
     * @throws IOException if an I/O error occurs if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 465);
        stage.setTitle("Library Analysis");
        stage.setScene(scene);

        MainController controller = fxmlLoader.getController();
        stage.setOnCloseRequest(controller::onCloseRequest);

        stage.show();
    }

    /**
     * The main method to launch the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}