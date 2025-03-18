package com.asteroids;

import javafx.animation.Timeline;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * The Ship class represents the player’s spaceship.
 * It handles movement, hyperspace jumps, collision detection, and
 * invincibility.
 */
public class Ship extends Character {

    // Determines if the ship is temporarily invincible.
    private boolean isInvincible = false;

    // Tracks if the ship is in hyperspace mode.
    private boolean isHyperspaced = false;

    // Timeline for handling invincibility duration.
    Timeline timeline = new Timeline();

    /**
     * Constructs a Ship object with a triangular polygon shape.
     * 
     * 'x' is the initial X-coordinate of the ship.
     * 'y' is the initial Y-coordinate of the ship.
     */
    public Ship(int x, int y) {
        super(new Polygon(0, 0, 100, 25, 0, 50, 25, 25), x, y);
    }

    // 'invincible' set to True if the ship is invincible, false otherwise.
    public void setInvincible(boolean invincible) {
        this.isInvincible = invincible;
    }

    // Checks if the ship is currently invincible.
    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * Moves the ship to a random location on the screen ("hyperspace jump").
     * Ensures the new position is at least 100 pixels away from the current
     * location.
     */
    public void Hyperspace() {
        this.setHyperspaced(true);

        double newX;
        double newY;

        do {
            // Generate a new random position within screen bounds.
            newX = Math.random() * (Controller.Width - 100 - this.getCharacter().getBoundsInParent().getWidth()) + 50;
            newY = Math.random() * (Controller.Height - 100 - this.getCharacter().getBoundsInParent().getHeight()) + 50;
        } while (Math.abs(newX - getCharacter().getTranslateX()) < 100
                && Math.abs(newY - getCharacter().getTranslateY()) < 100);

        // Move ship to new location and reset movement.
        super.getCharacter().setTranslateX(newX);
        super.getCharacter().setTranslateY(newY);
        super.setMovement(-super.getMovement().getX(), -super.getMovement().getY());
    }

    /**
     * Checks if this ship has collided with another character.
     * Uses JavaFX shape intersection for collision detection.
     **/
    public boolean collide(Character other) {
        Shape collisionArea = Shape.intersect(super.getCharacter(), other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    // Checks if the ship is currently in hyperspace mode.
    public boolean isHyperspaced() {
        return isHyperspaced;
    }

    // Sets the hyperspace mode of the ship.
    public void setHyperspaced(boolean hyperspaced) {
        isHyperspaced = hyperspaced;
    }
}