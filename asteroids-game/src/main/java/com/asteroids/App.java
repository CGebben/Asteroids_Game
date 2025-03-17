package com.asteroids;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load the correct FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/asteroids/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 440);
        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        launch(args);
    }
}