package com.asteroids;

// Import JavaFX libraries for UI components, animations, and event handling.
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    public static int Width = 800;
    public static int Height = 600;

    // Player stats.
    public int points = 0;
    public int lives = 3;

    // Lists for tracking bullets, enemies, and asteroids.
    List<Bullet> bullets = new ArrayList<>();
    List<Bullet> alien_bullets = new ArrayList<>();
    List<Character> enemies = new ArrayList<>();
    List<Asteroid> asteroidsToDowngrade = new ArrayList<>();

    // Timestamp for alien spawn logic.
    private long alienSpawnTime;

    // Game UI components.
    static Pane pane = new Pane();
    Pane end_pane = new Pane();
    double Rotation = 0;

    // Main player-controlled ship.
    static Ship ship = new Ship(Width / 3, Height / 3);

    // Timeline for managing timed events.
    static Timeline timeline = new Timeline();

    /**
     * Initializes the game when the "New Game" button is pressed.
     * This method sets up the game screen, spawns enemies, and starts the animation
     * loop.
     *
     * actionEvent - The event triggered by clicking "New Game".
     * throws IOException - If there is an issue loading resources.
     */
    @FXML
    public void start(ActionEvent actionEvent) throws IOException {
        // Close the main menu window.
        Stage begin = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        begin.close();

        // Set up the game screen.
        pane.setPrefSize(Width, Height);
        pane.getChildren().add(ship.getCharacter());

        // Display score and lives.
        Text text = new Text(10, 20, "Points:" + points);
        pane.getChildren().add(text);

        Text livesText = new Text(10, 40, "Lives: " + lives);
        pane.getChildren().add(livesText);

        // Generate enemy levels.
        Level[] levels = Level.createLevels();
        enemies = levels[0].getEnemyList();

        // Add enemies to the game screen.
        enemies.forEach(enemy -> {
            pane.getChildren().add(enemy.getCharacter());
        });
        // Add initial invincibility for the player.
        addInvincibility(5);

        // Create game scenes.
        Scene scene = new Scene(pane);
        Scene endgame = new Scene(end_pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        // Track key presses for player movement.
        Map<KeyCode, Boolean> pressedKey = new HashMap<>();
        scene.setOnKeyPressed(keyEvent -> {
            pressedKey.put(keyEvent.getCode(), Boolean.TRUE);
        });
        scene.setOnKeyReleased(keyEvent -> {
            pressedKey.put(keyEvent.getCode(), Boolean.FALSE);
        });

        // Create a new AnimationTimer that continuously updates game logic.
        new AnimationTimer() {
            private long lastUpdate = 0;
            private long lastbullets = 0;
            private int currentLevel = 0;

            /**
             * This method runs continuously, handling real-time game updates.
             * 'now' represents the current timestamp in nanoseconds.
             */
            public void handle(long now) {

                // Hyperspace logic: If 'A' is pressed, teleport the ship randomly.
                if (pressedKey.getOrDefault(KeyCode.A, false)) {
                    pressedKey.remove(KeyCode.A);
                    boolean collision;
                    timeline.stop();

                    do {
                        collision = false;
                        ship.Hyperspace();
                        for (Character enemy : enemies) {
                            if (enemy.collide(ship)) {
                                collision = true;
                                break;
                            }
                        }
                        for (Bullet bullet : alien_bullets) {
                            if (ship.collide(bullet)) {
                                collision = true;
                                break;
                            }
                        }
                    } while (collision);
                    addInvincibility(5);
                }

                // Handle ship movement based on key presses.
                if (pressedKey.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }
                if (pressedKey.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }
                if (pressedKey.getOrDefault(KeyCode.UP, false)) {
                    ship.setHyperspaced(false);
                    ship.acc();
                }

                // Handle bullet firing (spacebar).
                if (pressedKey.getOrDefault(KeyCode.SPACE, false) && (now - lastUpdate > 330_000_000)) {
                    Bullet bullet = new Bullet((int) (ship.getCharacter().getTranslateX()),
                            (int) (ship.getCharacter().getTranslateY()));
                    bullet.getCharacter().setRotate(ship.getCharacter().getRotate());
                    bullets.add(bullet);
                    bullet.acc();
                    pane.getChildren().add(bullet.getCharacter());
                    lastUpdate = now;
                }

                // Handle enemy shooting (only alien ships fire bullets).
                enemies.forEach(enemy -> {
                    if (now - alienSpawnTime > 5_000_000_000L && enemy.getSize() == 4) {
                        while (now - lastbullets > 1_000_000_000) {
                            Bullet bullet1 = new Bullet((int) (enemy.getCharacter().getTranslateX()),
                                    (int) (enemy.getCharacter().getTranslateY()));
                            var diff_y = ship.getCharacter().getTranslateX() - enemy.getCharacter().getTranslateX();
                            var diff_x = ship.getCharacter().getTranslateY() - enemy.getCharacter().getTranslateY();
                            var angle = Math.toDegrees(Math.atan2(diff_x, diff_y));
                            bullet1.getCharacter().setRotate(angle);
                            alien_bullets.add(bullet1);
                            bullet1.acc();
                            pane.getChildren().add(bullet1.getCharacter());
                            lastbullets = now;
                        }
                    }
                });

                // Move the ship if it is not in hyperspace mode.
                if (!ship.isHyperspaced()) {
                    ship.move();
                }

                // Move all enemies on the screen.
                enemies.forEach(enemy -> {
                    enemy.move();
                });

                // Handle collisions between the player and enemies.
                enemies.forEach(enemy -> {
                    if (enemy.collide(ship) && !ship.isInvincible()) {
                        lives--;
                        livesText.setText("Lives: " + lives);
                        if (lives > 0) {
                            // Reset the ship position and add temporary invincibility.
                            ship.getCharacter().setTranslateX(Width / 3);
                            ship.getCharacter().setTranslateY(Height / 3);
                            addInvincibility(5);
                        } else {
                            // If lives reach 0, end the game.
                            pane.getChildren().remove(ship.getCharacter());
                            stage.setScene(endgame);
                            saveScore();
                            stop();
                            stage.close();
                            livesText.setText("Game Over");
                        }
                    }
                });

                // Move bullets
                bullets.forEach(bullet -> {
                    bullet.move();
                });
                alien_bullets.forEach(bullet1 -> {
                    bullet1.move();
                });

                // Check for collisions between player and alien bullets.
                Iterator<Bullet> alienBulletIterator = alien_bullets.iterator();
                while (alienBulletIterator.hasNext()) {
                    Bullet bullet = alienBulletIterator.next();
                    if (ship.collide(bullet) && !ship.isInvincible()) {
                        lives--;
                        livesText.setText("Lives: " + lives);
                        pane.getChildren().remove(bullet.getCharacter());
                        alienBulletIterator.remove();

                        // Reset ship position and grant temporary invincibility.
                        if (lives > 0) {
                            // Reset the ship position and make it invincible for a short time
                            ship.getCharacter().setTranslateX(Width / 3);
                            ship.getCharacter().setTranslateY(Height / 3);
                            addInvincibility(5);
                        } else {
                            // If player runs out of lives, end the game.
                            pane.getChildren().remove(ship.getCharacter());
                            stage.setScene(endgame);
                            saveScore();
                            stop();
                            stage.close();
                            livesText.setText("Game Over: " + points);
                        }
                    }
                }

                // Check for collisions between bullets and enemies.
                Iterator<Bullet> bulletIterator = bullets.iterator();
                while (bulletIterator.hasNext()) {
                    Bullet bullet = bulletIterator.next();
                    Iterator<Character> enemyIterator = enemies.iterator();
                    while (enemyIterator.hasNext()) {
                        Character enemy = enemyIterator.next();
                        if (enemy.collide(bullet)) {
                            if (enemy.getSize() == 1) {
                                points += 10;
                                text.setText("Points:" + points);
                            }
                            if (enemy.getSize() == 2 || enemy.getSize() == 3) {
                                points += 30;
                                text.setText("Points:" + points);
                                double X = enemy.getCharacter().getTranslateX();
                                double Y = enemy.getCharacter().getTranslateY();
                                int Z = enemy.getSize();
                                asteroidsToDowngrade.add(new Asteroid((int) X + 10, (int) Y + 10, Z - 1));
                                asteroidsToDowngrade.add(new Asteroid((int) X - 10, (int) Y - 10, Z - 1));
                            }
                            if (enemy.getSize() == 4) {
                                points += 100;
                                text.setText("Points:" + points);
                                alien_bullets.forEach(bullet1 -> {
                                    pane.getChildren().remove(bullet1.getCharacter());
                                });
                                alien_bullets.clear();
                            }

                            // Remove the bullet and enemy from the game
                            pane.getChildren().remove(bullet.getCharacter());
                            bulletIterator.remove(); // Remove bullet using iterator
                            pane.getChildren().remove(enemy.getCharacter());
                            enemyIterator.remove(); // Remove enemy using iterator
                        }
                    }
                    ;
                }
                ;

                // Downgrade asteroids when hit.
                for (Asteroid asteroid : asteroidsToDowngrade) {
                    Downgrade((int) asteroid.getCharacter().getTranslateX(),
                            (int) asteroid.getCharacter().getTranslateY(), asteroid.getSize());
                }
                asteroidsToDowngrade.clear();

                // Handle level progression when all enemies are destroyed.
                if (enemies.isEmpty()) {
                    currentLevel++;
                    if (currentLevel >= levels.length) {
                        // If all levels are completed, display win message.
                        pane.getChildren().remove(ship.getCharacter());
                        stage.setScene(endgame);
                        text.setText("You Win!");
                        return;
                    }

                    // Remove all bullets from the screen before starting the next level
                    bullets.forEach(bullet -> {
                        pane.getChildren().remove(bullet.getCharacter());
                    });
                    bullets.clear();

                    alien_bullets.forEach(bullet -> {
                        pane.getChildren().remove(bullet.getCharacter());
                    });
                    alien_bullets.clear();

                    // Load new enemies for the next level.
                    enemies.clear();
                    enemies = levels[currentLevel].getEnemyList();
                    enemies.forEach(enemy -> {
                        pane.getChildren().add(enemy.getCharacter());
                    });

                    // Set alien ship spawn timer.
                    alienSpawnTime = System.nanoTime();
                }
                ;
            };
        }.start();
    }

    /**
     * Downgrades an asteroid when it is hit.
     * If an asteroid is large, it will break into smaller asteroids.
     *
     * x - The x-coordinate of the asteroid.
     * y - The y-coordinate of the asteroid.
     * z - The new size of the asteroid.
     */
    public void Downgrade(int x, int y, int z) {
        // Create a new asteroid with the downgraded size.
        Asteroid asteroid = new Asteroid(x, y, z);

        // Add the downgraded asteroid to the enemy list.
        enemies.add(asteroid);
        pane.getChildren().add(asteroid.getCharacter());
    }

    /**
     * Grants temporary invincibility to the player's ship.
     *
     * seconds - The duration of invincibility in seconds.
     */

    public static void addInvincibility(int seconds) {

        // Set the ship to invincible.
        ship.setInvincible(true);

        // Create a timeline to turn off invincibility after the given duration.
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(seconds), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // Reset the ship's invincibility status.
                ship.setInvincible(false); // Set the ship as not invincible after the duration
                timeline.stop();
            }
        }));

        // Start the timer.
        timeline.play();
    }

    /**
     * Displays a pop-up window that allows the player to enter their name
     * and saves their score to a file.
     */
    public void saveScore() {
        // Create a new VBox layout for the score-saving window.
        VBox root = new VBox();
        root.setStyle("-fx-background-color: black;");
        Stage stage = new Stage();

        // Prompt label for user input.
        Label promptLabel = new Label("Please enter your name: ");
        promptLabel.setStyle("-fx-font-size: 20pt; -fx-text-fill: white;");

        // Input field for player name.
        TextField name = new TextField();

        // Save button.
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            String playerName = name.getText();
            try {
                // Define the file path to store scores (relative to the game-data folder).
                File file = new File("../game-data/highScores.txt"); // Updated path
                file.getParentFile().mkdirs(); // Ensure game-data exists

                // Append the player's name and score to the file.
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(playerName + ": " + points + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Close the pop-up window after saving.
                stage.close();
            }
        });

        // Add UI elements to the window.
        root.getChildren().addAll(promptLabel, name, saveButton);

        // Set up the scene and display it.
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Displays the high scores in a new window.
     * Reads from the highScores.txt file and shows the contents.
     */
    @FXML
    public void display() {
        // Create a VBox layout for the high score window.
        VBox root = new VBox();
        root.setStyle("-fx-background-color: black;");

        StringBuilder content = new StringBuilder();
        try {
            // Open the high scores file.
            BufferedReader reader = new BufferedReader(new FileReader("../game-data/highScores.txt")); // Updated path
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
        highScoresLabel.setFont(Font.font("Brush Script MT", 15));
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
    public void Introduction() {
        // Create a VBox layout for the introduction window.
        VBox root = new VBox();
        root.setStyle("-fx-background-color: black;");
        Pane pane = new Pane();
        StringBuilder content = new StringBuilder();

        try {
            // Open the introduction file.
            BufferedReader reader = new BufferedReader(new FileReader("../game-data/introduction.txt")); // Updated path
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
        label.setFont(Font.font("Brush Script MT", 20));
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