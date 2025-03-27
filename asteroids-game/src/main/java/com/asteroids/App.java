package com.asteroids;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/// Entry point for the Asteroids game. Initializes the stage and controller.
public class App extends Application {

    @Override
    public void start(Stage stage) {
        System.out.println("Launching Asteroids...");

        // Create game pane and set size
        Pane root = new Pane();
        root.setPrefSize(Controller.Width, Controller.Height);

        // Attach scene to stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Asteroids");
        stage.show();

        // Create and set up game controller
        Controller controller = new Controller(root, stage, scene);
        controller.setupMenus();
    }

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        launch(args);
    }
}