package com.asteroids;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Create a blank pane (game + UI will render here)
        Pane root = new Pane();
        root.setPrefSize(Controller.Width, Controller.Height);

        // Set up the main scene and attach it to the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Asteroids");
        stage.show();

        // Create a Controller instance to manage game and callbacks
        Controller controller = new Controller(root, stage, scene);
        controller.setupMenus();
    }

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        launch(args);
    }
}