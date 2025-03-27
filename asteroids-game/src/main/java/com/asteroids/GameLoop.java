package com.asteroids;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.List;

/// Main animation loop for the game.
/// Handles updates, inputs, entity movement, collisions, and level progression.
public class GameLoop extends AnimationTimer {

    // --- Fields ---
    private long lastUpdate = 0;
    private int currentLevel = 0;
    private boolean gameEnded = false;

    private final Ship ship;
    private final List<Bullet> bullets;
    private final List<Character> enemies;
    private final Level[] levels;
    private final Text livesText;
    private final Text text;
    private final Stage stage;
    private final Pane pane;
    private final Scene endgame;
    private final Inputs inputHandler;
    private final Collisions collisionManager;

    private int lives;
    private int points;
    private Endgame endgameManager;

    // --- Constructor ---
    public GameLoop(
            Ship ship, List<Bullet> bullets, List<Character> enemies,
            List<int[]> asteroidsToSplit, Level[] levels, Text livesText, Text text,
            Stage stage, Pane pane, Scene endgame, Inputs inputHandler, int lives, int points) {

        this.ship = ship;
        this.bullets = bullets;
        this.enemies = enemies;
        this.levels = levels;
        this.livesText = livesText;
        this.text = text;
        this.stage = stage;
        this.pane = pane;
        this.endgame = endgame;
        this.inputHandler = inputHandler;
        this.lives = lives;
        this.points = points;

        this.collisionManager = new Collisions(
                ship, bullets, enemies, asteroidsToSplit, pane, livesText, text,
                stage, endgame, new Scoring(), lives, points);

        System.out.println("GameLoop initialized.");
    }

    /// Assigns the Endgame handler used for win/lose conditions.
    public void setEndgameManager(Endgame endgameManager) {
        this.endgameManager = endgameManager;
    }

    // --- Main Loop ---
    @Override
    public void handle(long now) {
        handleInputs(now);

        // Move everything
        enemies.forEach(Character::move);
        bullets.forEach(Bullet::move);
        if (!ship.isHyperspaced()) {
            ship.move();
        }

        // Check for collisions
        boolean gameOver = collisionManager.checkCollisions();
        if (gameOver && !gameEnded) {
            gameEnded = true;
            endgameManager.handleLose(collisionManager.getPoints());
            return;
        }

        collisionManager.asteroidSplitting();

        // Move to next level if enemies are cleared
        if (enemies.isEmpty()) {
            System.out.println("GameLoop: All enemies defeated. Advancing level...");
            advanceLevel();
        }
    }

    // --- Input Handling ---
    /// Handles key-based player input each frame.
    private void handleInputs(long now) {
        if (inputHandler.isKeyPressed(KeyCode.A)) {
            inputHandler.consumeKey(KeyCode.A);
            System.out.println("Hyperspace activated.");
            ship.hyperspace(enemies);
            ship.addInvincibility(5);
        }

        if (inputHandler.isKeyPressed(KeyCode.LEFT)) {
            ship.turnLeft();
        }

        if (inputHandler.isKeyPressed(KeyCode.RIGHT)) {
            ship.turnRight();
        }

        if (inputHandler.isKeyPressed(KeyCode.UP)) {
            if (ship.isReversing() && ship.getMovement().magnitude() > 0.1) {
                ship.dec();
            } else {
                ship.setReversing(false);
                ship.forwardMotion();
                ship.acc();
            }
        }

        if (inputHandler.isKeyPressed(KeyCode.DOWN)) {
            if (!ship.isReversing() && ship.getMovement().magnitude() > 0.1) {
                ship.dec();
            } else {
                ship.setReversing(true);
                ship.backwardMotion();
                ship.acc();
            }
        }

        if (inputHandler.isKeyPressed(KeyCode.SPACE) && (now - lastUpdate > 330_000_000)) {
            inputHandler.consumeKey(KeyCode.SPACE);
            Bullet bullet = new Bullet((int) ship.getCharacter().getTranslateX(),
                    (int) ship.getCharacter().getTranslateY());
            bullet.getCharacter().setRotate(ship.getCharacter().getRotate());
            bullets.add(bullet);
            bullet.acc();
            pane.getChildren().add(bullet.getCharacter());
            lastUpdate = now;
            System.out.println("Bullet fired.");
        }
    }

    // --- Level Progression ---
    /// Moves to the next level, resets enemies, and adds invincibility.
    private void advanceLevel() {
        if (gameEnded) {
            System.out.println("GameLoop: Game already ended. Skipping level advance.");
            return;
        }

        System.out.println("Advancing from level " + currentLevel + " of " + (levels.length - 1));

        if (currentLevel >= levels.length - 1) {
            System.out.println("GameLoop: Final level completed. Triggering win.");
            gameEnded = true;
            endgameManager.handleWin(points);
            return;
        }

        currentLevel++;
        System.out.println("GameLoop: Now on level " + currentLevel);

        bullets.forEach(b -> pane.getChildren().remove(b.getCharacter()));
        bullets.clear();

        enemies.clear();
        enemies.addAll(levels[currentLevel].getEnemyList());
        enemies.forEach(e -> pane.getChildren().add(e.getCharacter()));

        ship.hyperspace(enemies);
        ship.addInvincibility(5);
    }
}