package com.asteroids;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/// Represents the player’s ship. Handles movement, hyperspace, and invincibility.
public class Ship extends Character {
    private boolean isInvincible = false;
    private boolean isHyperspaced = false;

    public Ship(int x, int y) {
        super(new Polygon(0, 0, 100, 25, 0, 50, 25, 25), x, y);
    }

    public void setInvincible(boolean invincible) {
        this.isInvincible = invincible;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public void addInvincibility(int seconds) {
        this.setInvincible(true);

        Timeline blinkTimeline = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            // Toggle visibility
            Polygon shape = this.getCharacter();
            shape.setOpacity(shape.getOpacity() == 1 ? 0.3 : 1.0);
        }));

        blinkTimeline.setCycleCount((seconds * 1000) / 250); // Run for duration

        Timeline endTimeline = new Timeline(new KeyFrame(Duration.seconds(seconds), e -> {
            this.setInvincible(false);
            this.getCharacter().setOpacity(1.0); // Ensure it stays visible
        }));

        blinkTimeline.play();
        endTimeline.play();
    }

    /// Moves the ship to a random location away from nearby enemies.
    private void teleport(List<Character> enemies) {
        double newX, newY;
        boolean collision;

        do {
            newX = Math.random() * (Controller.Width - 100) + 50;
            newY = Math.random() * (Controller.Height - 100) + 50;

            collision = false;
            for (Character enemy : enemies) {
                double dx = newX - enemy.getCharacter().getTranslateX();
                double dy = newY - enemy.getCharacter().getTranslateY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance < 100) {
                    collision = true;
                    break;
                }
            }
        } while (collision);

        super.getCharacter().setTranslateX(newX);
        super.getCharacter().setTranslateY(newY);
        super.setMovement(0, 0);
    }

    public void hyperspace(List<Character> enemies) {
        this.setHyperspaced(true);
        teleport(enemies);
        this.setHyperspaced(false);
    }

    public boolean collide(Character other) {
        Shape collisionArea = Shape.intersect(super.getCharacter(), other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    public boolean isHyperspaced() {
        return isHyperspaced;
    }

    public void setHyperspaced(boolean hyperspaced) {
        isHyperspaced = hyperspaced;
    }

    private boolean reversing = false;

    public void setReversing(boolean reversing) {
        this.reversing = reversing;
    }

    public boolean isReversing() {
        return this.reversing;
    }
}