package com.example.asteroidsadvanced;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyGame extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        //FXML
        FXMLLoader fxmlLoader = new FXMLLoader(MyGame.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),320,440);
        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        launch(args);
    }
}