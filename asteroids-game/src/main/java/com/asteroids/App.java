package com.asteroids;

// Import necessary JavaFX classes
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The entry point for the Asteroids game application.
 * This class initializes the JavaFX application and loads the UI.
 */
public class App extends Application {

    /**
     * The start method is called when the JavaFX application is launched.
     * It sets up the primary stage and loads the UI from the FXML file.
     * 
     * 'stage' is the primary stage window of the application.
     * 'throws Exception' happens there is an issue loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Load the main UI layout from the FXML file.
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/asteroids/main.fxml"));

        // Create a new scene using the loaded FXML file, setting dimensions.
        Scene scene = new Scene(fxmlLoader.load(), 320, 440);

        // Set the title of the application window.
        stage.setTitle("Asteroids");

        // Attach the scene to the stage window and display it.
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method launches the JavaFX application.
     * It prints the working directory (useful for debugging) and starts the UI.
     * 
     * 'args' are command-line arguments passed to the program.
     */
    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        launch(args);
    }
}