package com.asteroids;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

/// Handles saving and displaying high scores.
/// Scores are stored in a text file located in `game-data/highscores.txt`.
public class Scoring {

    /// Opens a window to enter a player's name and save the score to file.
    public void enterScore(int points) {
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
                File file = new File("game-data/highscores.txt");
                file.getParentFile().mkdirs(); // Ensure directory exists

                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(playerName + ": " + points + "\n");
                writer.close();
                System.out.println("Saved score: " + playerName + " = " + points);
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
        System.out.println("Score entry popup opened.");
    }

    /// Opens a window displaying the contents of the high score file.
    public void showHighScores() {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: black;");
        Stage stage = new Stage();

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("game-data/highscores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Label highScoresLabel = new Label(content.toString());
        highScoresLabel.setFont(Font.font("Times New Roman", 15));
        highScoresLabel.setTextFill(Color.WHITE);
        root.getChildren().add(highScoresLabel);

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
        System.out.println("High score popup opened.");
    }
}