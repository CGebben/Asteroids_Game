package com.asteroids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class CollisionManager {

    private final Ship ship;
    private final List<Bullet> bullets;
    private final List<Character> enemies;
    private final List<int[]> asteroidsToSplit;
    private final Pane pane;
    private final Text livesText;
    private final Text scoreText;
    private final Stage stage;
    private final Scene endgame;
    private final ScoreManager scoreManager;
    private int lives;
    private int points;

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

    public void addInvincibility(int seconds) {
        ship.setInvincible(true);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(seconds), e -> {
            ship.setInvincible(false);
        }));

        timeline.play();
    }

    public CollisionManager(
            Ship ship,
            List<Bullet> bullets,
            List<Character> enemies,
            List<int[]> asteroidsToSplit,
            Pane pane,
            Text livesText,
            Text scoreText,
            Stage stage,
            Scene endgame,
            ScoreManager scoreManager,
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

    public boolean checkCollisions() {
        // Player-Enemy Collision
        boolean gameOver = false;
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
                    new ScoreManager().showScoreEntry(points);
                    livesText.setText("Game Over");
                    gameOver = true;
                }
            }
        }

        List<Character> enemiesToRemove = new ArrayList<>();
        List<Bullet> bulletsToRemove = new ArrayList<>();
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            for (Character enemy : new ArrayList<>(enemies)) {
                if (enemy.collide(bullet)) {
                    if (enemy.getSize() == 1) {
                        points += 10;
                        scoreText.setText("Points:" + points);
                    } else if (enemy.getSize() == 2 || enemy.getSize() == 3) {
                        points += 30;
                        scoreText.setText("Points:" + points);
                        double X = enemy.getCharacter().getTranslateX();
                        double Y = enemy.getCharacter().getTranslateY();
                        int Z = enemy.getSize();
                        asteroidsToSplit.add(new int[] { (int) X, (int) Y, Z - 1 });
                    }

                    bulletsToRemove.add(bullet);
                    enemiesToRemove.add(enemy);
                    break; // bullet is used up
                }
            }
        }

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