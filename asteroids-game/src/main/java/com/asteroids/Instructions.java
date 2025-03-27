package com.asteroids;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.io.*;

/// Displays the contents of an instructions file in a clean popup.
/// Uses a Text node and resizes window to fit all content.
public class Instructions {

    public static void showInstructions() {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: black;");
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("game-data/instructions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            System.out.println("Instructions file loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Text text = new Text(content.toString());
        text.setFont(Font.font("Times New Roman", 20));
        text.setFill(Color.WHITE);
        text.setWrappingWidth(600);

        root.getChildren().add(text);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("How to Play");
        stage.setScene(scene);
        stage.sizeToScene(); // auto-fit window to text
        stage.show();
        System.out.println("Instructions popup opened.");
    }
}