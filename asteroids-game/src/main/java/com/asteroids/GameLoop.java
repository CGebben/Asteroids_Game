package com.asteroids;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import javafx.scene.Scene;

/**
 * Handles the main game loop, updating all entities, collisions, and game
 * progression.
 */
public class GameLoop extends AnimationTimer {

    private long lastUpdate = 0;
    private int currentLevel = 0;
    private Ship ship;
    private List<Bullet> bullets;
    private List<Character> enemies;
    private Level[] levels;
    private Text livesText;
    private Text text;
    private Stage stage;
    private Pane pane;
    private Scene endgame;
    private Inputs inputHandler;
    private Collisions collisionManager;
    private int lives;
    private int points;
    private Endgame endgameManager;
    private boolean gameEnded = false;

    public GameLoop(Ship ship, List<Bullet> bullets, List<Character> enemies,
            List<int[]> asteroidsToSplit, Level[] levels, Text livesText, Text text, Stage stage,
            Pane pane, Scene endgame, Inputs inputHandler, int lives, int points) {
        this.ship = ship;
        this.bullets = bullets;
        this.enemies = enemies;
        this.levels = levels;
        this.text = text;
        this.stage = stage;
        this.pane = pane;
        this.endgame = endgame;
        this.inputHandler = inputHandler;
        this.points = points;
        this.collisionManager = new Collisions(ship, bullets, enemies, asteroidsToSplit, pane, livesText, text,
                stage, endgame, new Scoring(), lives, points);
    }

    public void setEndgameManager(Endgame endgameManager) {
        this.endgameManager = endgameManager;
    }

    @Override
    public void handle(long now) {

        // Handle Hyperspace (A Key)
        if (inputHandler.isKeyPressed(KeyCode.A)) {
            inputHandler.consumeKey(KeyCode.A);
            ship.hyperspace(enemies);
            ship.addInvincibility(5);
        }

        // Handle Ship Movement
        if (inputHandler.isKeyPressed(KeyCode.LEFT))
            ship.turnLeft();
        if (inputHandler.isKeyPressed(KeyCode.RIGHT))
            ship.turnRight();

        // UP = Forward
        if (inputHandler.isKeyPressed(KeyCode.UP)) {
            // If currently reversing, decelerate until we stop
            if (ship.isReversing() && ship.getMovement().magnitude() > 0.1) {
                ship.dec();
            } else {
                ship.setReversing(false); // We're going forward now
                ship.forwardMotion();
                ship.acc();
            }
        }

        // DOWN = Reverse
        if (inputHandler.isKeyPressed(KeyCode.DOWN)) {
            // If currently going forward, decelerate until we stop
            if (!ship.isReversing() && ship.getMovement().magnitude() > 0.1) {
                ship.dec();
            } else {
                ship.setReversing(true); // We're going backward now
                ship.backwardMotion();
                ship.acc();
            }
        }

        // Handle Shooting
        if (inputHandler.isKeyPressed(KeyCode.SPACE) && (now - lastUpdate > 330_000_000)) {
            inputHandler.consumeKey(KeyCode.SPACE);
            Bullet bullet = new Bullet((int) ship.getCharacter().getTranslateX(),
                    (int) ship.getCharacter().getTranslateY());
            bullet.getCharacter().setRotate(ship.getCharacter().getRotate());
            bullets.add(bullet);
            bullet.acc();
            pane.getChildren().add(bullet.getCharacter());
            lastUpdate = now;
        }

        // Move Enemies
        enemies.forEach(Character::move);

        // Move Bullets
        bullets.forEach(Bullet::move);

        // Move Ship
        if (!ship.isHyperspaced())
            ship.move();

        // Handle Collisions

        boolean gameOver = collisionManager.checkCollisions();
        if (gameOver && !gameEnded) {
            gameEnded = true;
            endgameManager.handleLose(collisionManager.getPoints());
            return;
        }

        collisionManager.asteroidSplitting();

        // Level Progression
        if (enemies.isEmpty()) {
            System.out.println("All enemies cleared, advancing level...");
            advanceLevel();
        }
    }

    private void advanceLevel() {
        if (gameEnded) {
            System.out.println("Game already ended. Skipping advanceLevel().");
            return;
        }

        System.out
                .println("advanceLevel() called. currentLevel: " + currentLevel + ", levels.length: " + levels.length);

        if (currentLevel >= levels.length - 1) {
            System.out.println("Final level reached. Triggering win.");
            gameEnded = true;
            endgameManager.handleWin(points);
            return;
        }

        currentLevel++;
        System.out.println("Now on level " + currentLevel);

        bullets.forEach(bullet -> pane.getChildren().remove(bullet.getCharacter()));
        bullets.clear();

        enemies.clear();
        enemies.addAll(levels[currentLevel].getEnemyList());

        System.out.println("Enemies after loading new level: " + enemies.size());

        enemies.forEach(enemy -> pane.getChildren().add(enemy.getCharacter()));

        ship.hyperspace(enemies);
        ship.addInvincibility(5);
    }
}