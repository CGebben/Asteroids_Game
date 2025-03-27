package com.asteroids;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

/// Manages game lifecycle, UI interaction, and game object creation.
public class Controller {

    // --- Constants ---
    public static int Width = 1280;
    public static int Height = 720;

    // --- Fields ---
    private final Pane pane;
    private final Stage stage;
    private final Scene scene;
    private final Inputs inputs = new Inputs();
    private final Scoring scoring = new Scoring();

    private GameLoop gameLoop;
    private Menus menus;

    private int points = 0;
    private int lives = 3;

    private List<Bullet> bullets = new ArrayList<>();
    private List<Character> enemies = new ArrayList<>();
    private List<int[]> asteroidsToSplit = new ArrayList<>();

    private Pane end_pane = new Pane();
    private double Rotation = 0;

    private static Ship ship = new Ship(Width / 3, Height / 3);
    private static Timeline timeline = new Timeline();

    // --- Constructor ---

    public Controller(Pane pane, Stage stage, Scene scene) {
        this.pane = pane;
        this.stage = stage;
        this.scene = scene;
    }

    // --- Setup ---

    /// Initializes and displays all menus.
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

    public void setEndgameManager(Endgame endgameManager) {
        System.out.println("Setting endgameManager in GameLoop...");
        if (gameLoop != null) {
            gameLoop.setEndgameManager(endgameManager);
        } else {
            System.out.println("Warning: gameLoop is null!");
        }
    }

    // --- Game Flow Methods ---

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
        menus.showMainMenu();
    }

    /// Starts a new game session from scratch.
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

        Text scoreText = new Text(10, 40, "Points: " + points);
        scoreText.setFont(Font.font("System", 36));
        scoreText.setFill(Color.BLACK);
        pane.getChildren().add(scoreText);

        Text livesText = new Text(10, 80, "Lives: " + lives);
        livesText.setFont(Font.font("System", 36));
        livesText.setFill(Color.BLACK);
        pane.getChildren().add(livesText);

        Level[] levels = Level.createLevels();
        enemies = levels[0].getEnemyList();
        enemies.forEach(e -> pane.getChildren().add(e.getCharacter()));

        inputs.initialize(scene);

        gameLoop = new GameLoop(
                ship, bullets, enemies, asteroidsToSplit,
                levels, livesText, scoreText, stage, pane, scene, inputs, lives, points);
        gameLoop.start();

        Endgame endgame = new Endgame(stage, menus, scoring, gameLoop);
        gameLoop.setEndgameManager(endgame);
    }
}