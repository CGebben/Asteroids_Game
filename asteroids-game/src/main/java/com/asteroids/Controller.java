package com.asteroids;

// Import JavaFX libraries for UI components, animations, and event handling.
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Import Java standard libraries for file handling and collections.
import java.io.*;
import java.util.*;

/**
 * The Controller class handles the main game logic and user interactions.
 * It manages player input, enemy behavior, collisions, score tracking, and game
 * progression.
 */
public class Controller {

    // Game screen dimensions.
    public static int Width = 1280;
    public static int Height = 720;

    // Player stats.
    public int points = 0;
    public int lives = 3;

    // Lists for tracking bullets, enemies, and asteroids.
    List<Bullet> bullets = new ArrayList<>();
    List<Character> enemies = new ArrayList<>();
    List<int[]> asteroidsToSplit = new ArrayList<>();

    // Game UI components.
    static Pane pane = new Pane();
    Pane end_pane = new Pane();
    double Rotation = 0;

    // Main player-controlled ship.
    static Ship ship = new Ship(Width / 3, Height / 3);

    // Timeline for managing timed events.
    static Timeline timeline = new Timeline();

    // Creates instance for input handling.
    private InputHandler inputHandler = new InputHandler();

    /// Initializes the game when the "New Game" button is pressed.
    /// Sets up the game screen, spawns enemies, and starts the animation loop.
    @FXML
    public void start(ActionEvent actionEvent) throws IOException {
        // Close the main menu window.
        Stage begin = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        begin.close();

        // Set up the game screen.
        pane.setPrefSize(Width, Height);
        pane.getChildren().add(ship.getCharacter());
        ship.addInvincibility(5);

        Text text = new Text(10, 40, "Points: " + points);
        text.setFont(Font.font("System", 36));
        text.setFill(Color.BLACK);
        pane.getChildren().add(text);

        Text livesText = new Text(10, 80, "Lives: " + lives);
        livesText.setFont(Font.font("System", 36));
        livesText.setFill(Color.BLACK);
        pane.getChildren().add(livesText);

        // Generate enemy levels.
        Level[] levels = Level.createLevels();
        enemies = levels[0].getEnemyList();

        // Add enemies to the game screen.
        enemies.forEach(enemy -> {
            pane.getChildren().add(enemy.getCharacter());
        });

        // Create game scenes.
        Scene scene = new Scene(pane);
        Scene endgame = new Scene(end_pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        // Initialize input handling.
        inputHandler.initialize(scene); // Attach input handling to the scene

        // Inside Controller.java
        GameLoop gameLoop = new GameLoop(
                ship, bullets, enemies, asteroidsToSplit,
                levels, livesText, text, stage, pane, endgame, inputHandler, lives, points);
        gameLoop.start();

    }

    /// Displays the high scores in a new window.
    @FXML
    public void displayScore() {
        // Create a VBox layout for the high score window.
        VBox root = new VBox();
        root.setStyle("-fx-background-color: black;");

        StringBuilder content = new StringBuilder();
        try {
            // Open the high scores file.
            BufferedReader reader = new BufferedReader(new FileReader("game-data/scoreboard.txt")); // Updated path
            String line;

            // Read all lines and append them to the content.
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display high scores in a label.
        Label highScoresLabel = new Label(content.toString());
        highScoresLabel.setFont(Font.font("Times New Roman", 15));
        highScoresLabel.setTextFill(Color.WHITE);
        root.getChildren().add(highScoresLabel);

        // Set up the scene and display it.
        Scene scene = new Scene(root, 300, 200);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Displays the introduction text in a new window.
     * Reads from introduction.txt and presents it in a formatted manner.
     */
    @FXML
    public void displayHowTo() {
        // Create a VBox layout for the introduction window.
        VBox root = new VBox();
        root.setStyle("-fx-background-color: black;");
        Pane pane = new Pane();
        StringBuilder content = new StringBuilder();

        try {
            // Open the introduction file.
            BufferedReader reader = new BufferedReader(new FileReader("game-data/howtoplay.txt")); // Updated path
            String line;

            // Read and format the introduction text (adds line breaks every 50 characters).
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
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display introduction text in a label.
        Label label = new Label(content.toString());
        label.setFont(Font.font("Times New Roman", 20));
        label.setTextFill(Color.WHITE);
        pane.getChildren().add(label);
        root.getChildren().add(pane);

        // Set up the scene and display it.
        Scene scene = new Scene(root, 300, 200);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}