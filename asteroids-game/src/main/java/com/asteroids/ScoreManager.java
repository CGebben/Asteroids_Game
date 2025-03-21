package com.asteroids;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class ScoreManager {

    /**
     * Opens a pop-up window to enter a name and saves the score to a file.
     *
     * 'points' The player's score to save.
     */
    public void showScoreEntry(int points) {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: black;");
        Stage stage = new Stage();

        Label promptLabel = new Label("ENTER NAME: ");
        promptLabel.setStyle("-fx-font-size: 20pt; -fx-text-fill: white;");
        TextField name = new TextField();
        Button saveButton = new Button("Save");

        saveButton.setOnAction(event -> {
            String playerName = name.getText();
            try {
                File file = new File("game-data/scoreboard.txt");
                file.getParentFile().mkdirs(); // Ensure game-data exists

                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(playerName + ": " + points + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                stage.close();
            }
        });

        root.getChildren().addAll(promptLabel, name, saveButton);
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
    }
}