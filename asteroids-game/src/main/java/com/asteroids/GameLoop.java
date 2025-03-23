package com.asteroids;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Iterator;
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
    private List<Asteroid> asteroidsToDowngrade;
    private Level[] levels;
    private Text livesText;
    private Text text;
    private Stage stage;
    private Pane pane;
    private Scene endgame;
    private InputHandler inputHandler;

    private int lives;
    private int points;

    public GameLoop(Ship ship, List<Bullet> bullets, List<Character> enemies,
            List<Asteroid> asteroidsToDowngrade, Level[] levels, Text livesText, Text text, Stage stage,
            Pane pane, Scene endgame, InputHandler inputHandler, int lives, int points) {
        this.ship = ship;
        this.bullets = bullets;
        this.enemies = enemies;
        this.asteroidsToDowngrade = asteroidsToDowngrade;
        this.levels = levels;
        this.livesText = livesText;
        this.text = text;
        this.stage = stage;
        this.pane = pane;
        this.endgame = endgame;
        this.inputHandler = inputHandler;
        this.lives = lives;
        this.points = points;
    }

    @Override
    public void handle(long now) {

        // Handle Hyperspace (A Key)
        if (inputHandler.isKeyPressed(KeyCode.A)) {
            inputHandler.consumeKey(KeyCode.A);
            boolean collision;

            do {
                collision = false;
                ship.Hyperspace();
                for (Character enemy : enemies) {
                    if (enemy.collide(ship)) {
                        collision = true;
                        break;
                    }
                }
            } while (collision);
            addInvincibility(5);
        }

        // Handle Ship Movement
        if (inputHandler.isKeyPressed(KeyCode.LEFT))
            ship.turnLeft();
        if (inputHandler.isKeyPressed(KeyCode.RIGHT))
            ship.turnRight();
        if (inputHandler.isKeyPressed(KeyCode.UP))
            ship.acc();
        if (inputHandler.isKeyPressed(KeyCode.DOWN))
            ship.dec(); // Call the new deceleration method

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

        // Handle Collisions (we'll move collision handling to its own class later)
        checkCollisions();

        // Level Progression
        if (enemies.isEmpty()) {
            advanceLevel();
        }
    }

    /// Downgrades asteroid when hit.
    private void downgrade(int x, int y, int z) {
        // Create a new asteroid with the downgraded size.
        Asteroid asteroid = new Asteroid(x, y, z);

        // Add the downgraded asteroid to the enemy list.
        enemies.add(asteroid);
        pane.getChildren().add(asteroid.getCharacter());
        }

    // Downgrade asteroids when hit.
    for(

    Asteroid asteroid:asteroidsToDowngrade)
    {
        downgrade((int) asteroid.getCharacter().getTranslateX(),
                (int) asteroid.getCharacter().getTranslateY(), asteroid.getSize());
    }asteroidsToDowngrade.clear();

    private void advanceLevel() {
        currentLevel++;
        if (currentLevel >= levels.length) {
            pane.getChildren().remove(ship.getCharacter());
            stage.setScene(endgame);
            text.setText("You Win!");
            new ScoreManager().showScoreEntry(points);
            stop();
            stage.close();
            return;
        }

        bullets.forEach(bullet -> pane.getChildren().remove(bullet.getCharacter()));
        bullets.clear();

        enemies.clear();
        enemies = levels[currentLevel].getEnemyList();
        enemies.forEach(enemy -> pane.getChildren().add(enemy.getCharacter()));
    }

    private void addInvincibility(int seconds) {
        ship.setInvincible(true);
        // Logic for removing invincibility later (e.g., use Timeline)
    }
}