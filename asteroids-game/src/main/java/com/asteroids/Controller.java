package com.asteroids;

// Import JavaFX libraries for UI components, animations, and event handling.
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class Controller {

    private Menus menus;
    private Scoring scoring = new Scoring(); // one instance reused

    public void setupMenus() {
        menus = new Menus(
                pane,
                scoring,
                this::startGame,
                this::showHighScores,
                this::showInstructions,
                this::playAgain,
                this::returnToMainMenu,
                Platform::exit);
    }

    public Controller(Pane pane, Stage stage, Scene scene) {
        this.pane = pane;
        this.stage = stage;
        this.scene = scene;
    }

    public void setEndgameManager(Endgame endgameManager) {
        System.out.println("Setting endgameManager in GameLoop...");
        if (gameLoop != null) {
            gameLoop.setEndgameManager(endgameManager);
        } else {
            System.out.println("Warning: gameLoop is null!");
        }
    }

    // Game screen dimensions.
    public static int Width = 1280;
    public static int Height = 720;

    private Pane pane;
    private Stage stage;
    private Scene scene;
    private GameLoop gameLoop;
    private Inputs inputs = new Inputs();

    // Player stats.
    public int points = 0;
    public int lives = 3;

    // Lists for tracking bullets, enemies, and asteroids.
    List<Bullet> bullets = new ArrayList<>();
    List<Character> enemies = new ArrayList<>();
    List<int[]> asteroidsToSplit = new ArrayList<>();

    // Game UI components.
    Pane end_pane = new Pane();
    double Rotation = 0;

    // Main player-controlled ship.
    static Ship ship = new Ship(Width / 3, Height / 3);

    // Timeline for managing timed events.
    static Timeline timeline = new Timeline();

    public void showHighScores() {
        scoring.showHighScores();
    }

    public void showInstructions() {
        Instructions.showInstructions();
    }

    public void playAgain() {
        startGame();
    }

    public void returnToMainMenu() {
        System.out.println("Controller.returnToMainMenu() called");
        menus.showMainMenu(); // this will handle hiding others & showing the main menu
    }

    /// Initializes the game when the "New Game" button is pressed.
    /// Sets up the game screen, spawns enemies, and starts the animation loop.
    public void startGame() {
        bullets.clear();
        enemies.clear();
        asteroidsToSplit.clear();
        pane.getChildren().clear();
        points = 0;
        lives = 3;

        ship = new Ship(Width / 3, Height / 3);
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

        Level[] levels = Level.createLevels();
        enemies = levels[0].getEnemyList();
        enemies.forEach(enemy -> pane.getChildren().add(enemy.getCharacter()));

        inputs.initialize(scene);

        this.gameLoop = new GameLoop(
                ship, bullets, enemies, asteroidsToSplit,
                levels, livesText, text, stage, pane, scene, inputs, lives, points);
        gameLoop.start();

        Endgame endgame = new Endgame(stage, menus, scoring, gameLoop);
        gameLoop.setEndgameManager(endgame);
    }
}