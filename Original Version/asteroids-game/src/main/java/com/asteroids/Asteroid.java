package com.asteroids;

// Import necessary JavaFX classes for graphical shapes.
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

// Import Java utility class for generating random numbers.
import java.util.Random;

/**
 * Represents an Asteroid in the game.
 * The Asteroid moves, rotates, and has different sizes with varying speeds.
 * It extends the Character class, inheriting movement properties.
 */
public class Asteroid extends Character {

    // Controls how much the asteroid rotates as it moves.
    private double rotationalMovement;

    // The size of the asteroid (1 = small, 2 = medium, 3 = large).
    private int size;

    // Determines the acceleration speed of the asteroid.
    int accelerationAmount;

    /**
     * Constructs an Asteroid object at the specified (x, y) position with a given
     * size.
     *
     * x The initial X-coordinate of the asteroid.
     * y The initial Y-coordinate of the asteroid.
     * z The size of the asteroid (1 = small, 2 = medium, 3 = large).
     */
    public Asteroid(int x, int y, int z) {

        // Call superclass constructor with a polygon shape representing the asteroid.
        super(new Polygon(25.0 * z, 0.0, 50.0 * z, 15.0 * z, 40.0 * z, 40.0 * z, 10.0 * z, 40.0 * z, 0.0, 15.0 * z), x,
                y);

        // Store the size of the asteroid.
        this.size = z;

        // Set a default movement speed.
        super.setMovement(0.1, 0.1);

        // Generate a random starting rotation.
        Random rnd = new Random();
        super.getCharacter().setRotate(rnd.nextInt(360));

        // Set acceleration speed based on the asteroid size.
        if (z == 3) {
            this.accelerationAmount = 1 + rnd.nextInt(10);
        }
        if (z == 2) {
            this.accelerationAmount = 1 + rnd.nextInt(20, 30);
        }
        if (z == 1) {
            this.accelerationAmount = 1 + rnd.nextInt(40, 70);
        }

        // Apply acceleration based on the determined speed.
        for (int i = 0; i < this.accelerationAmount; i++) {
            acc();
        }

        // Assign a random rotational movement direction.
        this.rotationalMovement = 0.5 - rnd.nextDouble();
    }

    /**
     * Moves the Asteroid forward and rotates it slightly.
     * The rotation speed is determined randomly in the constructor.
     */
    @Override
    public void move() {
        // Call the Character class's move method.
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + this.rotationalMovement);
    }

    // Returns the size of the Asteroid.
    public int getSize() {
        return this.size;
    }

    /**
     * Checks if this Asteroid collides with another game character.
     *
     * 'param other' check the other character for collision.
     * 'return True' if the asteroid collides (overlaps with the other Character,
     * false otherwise.
     */
    public boolean collide(Character other) {
        Shape collisionArea = Shape.intersect(super.getCharacter(), other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
}