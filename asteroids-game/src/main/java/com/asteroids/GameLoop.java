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
    private long lastbullets = 0;
    private int currentLevel = 0;
    private long alienSpawnTime;

    private Ship ship;
    private List<Bullet> bullets;
    private List<Bullet> alien_bullets;
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

    public GameLoop(Ship ship, List<Bullet> bullets, List<Bullet> alien_bullets, List<Character> enemies,
            List<Asteroid> asteroidsToDowngrade, Level[] levels, Text livesText, Text text, Stage stage,
            Pane pane, Scene endgame, InputHandler inputHandler, int lives, int points) {
        this.ship = ship;
        this.bullets = bullets;
        this.alien_bullets = alien_bullets;
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
                for (Bullet bullet : alien_bullets) {
                    if (ship.collide(bullet)) {
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

        // Move Ship
        if (!ship.isHyperspaced())
            ship.move();

        // Move Enemies
        enemies.forEach(Character::move);

        // Handle Collisions (we'll move collision handling to its own class later)
        checkCollisions();

        // Level Progression
        if (enemies.isEmpty()) {
            advanceLevel();
        }
    }

    private void checkCollisions() {
        // Player-Enemy Collision
        Iterator<Character> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Character enemy = enemyIterator.next();
            if (enemy.collide(ship) && !ship.isInvincible()) {
                lives--;
                livesText.setText("Lives: " + lives);
                if (lives > 0) {
                    ship.getCharacter().setTranslateX(Controller.Width / 3);
                    ship.getCharacter().setTranslateY(Controller.Height / 3);
                    addInvincibility(5);
                } else {
                    pane.getChildren().remove(ship.getCharacter());
                    stage.setScene(endgame);
                    stop();
                    stage.close();
                    livesText.setText("Game Over");
                }
                enemyIterator.remove();
            }
        }

        // Bullet-Enemy Collision
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Character enemy = enemyIterator.next();
                if (enemy.collide(bullet)) {
                    points += 10; // Adjusted for simplicity
                    text.setText("Points: " + points);
                    pane.getChildren().remove(enemy.getCharacter());
                    enemyIterator.remove();
                    pane.getChildren().remove(bullet.getCharacter());
                    bulletIterator.remove();
                    break;
                }
            }
        }
    }

    private void advanceLevel() {
        currentLevel++;
        if (currentLevel >= levels.length) {
            pane.getChildren().remove(ship.getCharacter());
            stage.setScene(endgame);
            text.setText("You Win!");
            return;
        }

        bullets.forEach(bullet -> pane.getChildren().remove(bullet.getCharacter()));
        bullets.clear();

        alien_bullets.forEach(bullet -> pane.getChildren().remove(bullet.getCharacter()));
        alien_bullets.clear();

        enemies.clear();
        enemies = levels[currentLevel].getEnemyList();
        enemies.forEach(enemy -> pane.getChildren().add(enemy.getCharacter()));

        alienSpawnTime = System.nanoTime();
    }

    private void addInvincibility(int seconds) {
        ship.setInvincible(true);
        // Logic for removing invincibility later (e.g., use Timeline)
    }
}