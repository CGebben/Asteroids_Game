package com.asteroids;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

/// Displays the contents of an instructions file in a pop-up window.
/// Text is wrapped manually to improve readability.
///
/// NOTE: The window size and layout still need to be adjusted for better UX.
public class Instructions {

    public static void showInstructions() {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: black;");
        Pane pane = new Pane();
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("game-data/instructions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int i = 0;
                while (i < line.length()) {
                    content.append(line.charAt(i));
                    i++;
                    if (i % 50 == 0) {
                        content.append("\n");
                    }
                }
                content.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Label label = new Label(content.toString());
        label.setFont(Font.font("Times New Roman", 20));
        label.setTextFill(Color.WHITE);
        pane.getChildren().add(label);
        root.getChildren().add(pane);

        Scene scene = new Scene(root, 300, 200);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}