package com.asteroids;

// Import JavaFX Polygon class for defining the bullet shape.
import javafx.scene.shape.Polygon;

/**
 * Represents a bullet in the game.
 * Bullets are fired by the player's ship and move in a straight line.
 * This class extends the Character class and inherits its movement behavior.
 */
public class Bullet extends Character {

    /**
     * Constructs a Bullet object at the specified (x, y) coordinates.
     * The bullet is represented as a small square polygon.
     *
     * x The initial X-coordinate of the bullet.
     * y The initial Y-coordinate of the bullet.
     */
    public Bullet(int x, int y) {
        // Call the superclass constructor with a small square-shaped polygon.
        super(new Polygon(5, -5, 5, 5, -5, 5, -5, -5), x + 50, y + 30);
    }

    /**
     * Moves the bullet forward in its current direction.
     * The movement is determined by its velocity vector.
     */
    @Override
    public void move() {
        super.getCharacter().setTranslateX(super.getCharacter().getTranslateX() + super.getMovement().getX());
        super.getCharacter().setTranslateY(super.getCharacter().getTranslateY() + super.getMovement().getY());
    }

    /**
     * Accelerates the bullet in the direction it is facing.
     * The speed is calculated using trigonometry based on its rotation angle.
     */
    @Override
    public void acc() {

        // Call the superclass acceleration method.
        super.acc();

        // Calculate the movement vector based on the bullet's rotation angle.
        double angle = Math.toRadians(super.getCharacter().getRotate());
        double X = Math.cos(angle) * 5;
        double Y = Math.sin(angle) * 5;

        // Apply the new movement values.
        super.setMovement(X, Y);
    }
}