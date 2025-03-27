package com.asteroids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/// Handles collision detection between player, enemies, and bullets.
/// Also tracks game state like lives and points.
public class Collisions {

    // --- References ---
    private final Ship ship;
    private final List<Bullet> bullets;
    private final List<Character> enemies;
    private final List<int[]> asteroidsToSplit;
    private final Pane pane;
    private final Text livesText;
    private final Text scoreText;
    private final Stage stage;
    private final Scene endgame;
    private final Scoring scoreManager;

    // --- Game State ---
    private int lives;
    private int points;

    // --- Constructor ---

    public Collisions(
            Ship ship,
            List<Bullet> bullets,
            List<Character> enemies,
            List<int[]> asteroidsToSplit,
            Pane pane,
            Text livesText,
            Text scoreText,
            Stage stage,
            Scene endgame,
            Scoring scoreManager,
            int lives,
            int points) {

        this.ship = ship;
        this.bullets = bullets;
        this.enemies = enemies;
        this.asteroidsToSplit = asteroidsToSplit;
        this.pane = pane;
        this.livesText = livesText;
        this.scoreText = scoreText;
        this.stage = stage;
        this.endgame = endgame;
        this.scoreManager = scoreManager;
        this.lives = lives;
        this.points = points;
    }

    // --- Collision Checking ---

    /// Checks for collisions between player, enemies, and bullets.
    /// Returns true if the player has no remaining lives (game over).
    public boolean checkCollisions() {
        boolean gameOver = false;

        // --- Player-Enemy Collisions ---
        Iterator<Character> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Character enemy = enemyIterator.next();
            if (enemy.collide(ship) && !ship.isInvincible()) {
                lives--;
                livesText.setText("Lives: " + lives);
                if (lives > 0) {
                    ship.hyperspace(enemies);
                    ship.addInvincibility(5);
                } else {
                    pane.getChildren().remove(ship.getCharacter());
                    gameOver = true;
                }
            }
        }

        // --- Bullet-Enemy Collisions ---
        List<Character> enemiesToRemove = new ArrayList<>();
        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            for (Character enemy : new ArrayList<>(enemies)) {
                if (enemy.collide(bullet)) {
                    if (enemy.getSize() == 1) {
                        points += 10;
                    } else if (enemy.getSize() == 2 || enemy.getSize() == 3) {
                        points += 30;
                        double X = enemy.getCharacter().getTranslateX();
                        double Y = enemy.getCharacter().getTranslateY();
                        int Z = enemy.getSize();
                        asteroidsToSplit.add(new int[] { (int) X, (int) Y, Z - 1 });
                    }

                    scoreText.setText("Points: " + points);
                    bulletsToRemove.add(bullet);
                    enemiesToRemove.add(enemy);
                    break;
                }
            }
        }

        // --- Remove collided objects from scene and lists ---
        for (Bullet bullet : bulletsToRemove) {
            pane.getChildren().remove(bullet.getCharacter());
            bullets.remove(bullet);
        }

        for (Character enemy : enemiesToRemove) {
            pane.getChildren().remove(enemy.getCharacter());
            enemies.remove(enemy);
        }

        return gameOver;
    }

    // --- Asteroid Splitting ---

    /// Splits asteroids marked for division into two smaller ones.
    public void asteroidSplitting() {
        for (int[] data : asteroidsToSplit) {
            int x = data[0];
            int y = data[1];
            int size = data[2];

            Asteroid asteroid1 = new Asteroid(x + 10, y + 10, size);
            Asteroid asteroid2 = new Asteroid(x - 10, y - 10, size);

            enemies.add(asteroid1);
            enemies.add(asteroid2);

            pane.getChildren().add(asteroid1.getCharacter());
            pane.getChildren().add(asteroid2.getCharacter());
        }
        asteroidsToSplit.clear();
    }

    // --- Accessors ---

    /// Returns a fresh copy of the list of asteroid splits queued.
    public List<int[]> getNewAsteroids() {
        return new ArrayList<>(asteroidsToSplit);
    }

    public int getLives() {
        return lives;
    }

    public int getPoints() {
        return points;
    }
}