package com.asteroids;

// Import necessary JavaFX classes for positioning and collision detection.
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * Represents a character in the game, including the player’s ship and enemies.
 * This class provides movement, rotation, and collision detection
 * functionality.
 */
public class Character {
    private int size;

    // Movement vector representing the direction and speed of the character.
    private Point2D movement;

    // The graphical representation of the character using a polygon.
    private Polygon character;

    /**
     * Constructs a Character object with a specified shape and position.
     * 
     * shape The polygon shape representing the character.
     * x The initial X-coordinate.
     * y The initial Y-coordinate.
     */
    public Character(Polygon shape, int x, int y) {
        this.character = shape;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);

        // Initialize movement as stationary (0,0).
        this.movement = new Point2D(0, 0);
    }

    // Returns the polygon representing the character.
    public Polygon getCharacter() {
        return this.character;
    }

    // Returns the movement vector of the character.
    public Point2D getMovement() {
        return this.movement;
    }

    // Updates the movement vector by adding the specified values.
    public void setMovement(double x, double y) {
        this.movement = this.movement.add(x, y);
    }

    // Rotates the character to the right by 1 degree.
    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 1);
    }

    // Rotates the character to the left by 1 degree.
    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 1);
    }

    // Maximum movement speed to prevent infinite acceleration.
    private static final double Max_Speed = 5.0;

    /**
     * Accelerates the character in the direction it is currently facing.
     * Uses trigonometry to calculate movement based on rotation angle.
     */
    public void acc() {

        // Convert the character’s current rotation angle to radians.
        double angle = Math.toRadians(this.character.getRotate());

        // Calculate acceleration in the X and Y directions.
        double X = Math.cos(angle) * 0.01;
        double Y = Math.sin(angle) * 0.01;

        // // Apply acceleration to the movement vector.
        Point2D acceleration = new Point2D(X, Y);
        this.movement = this.movement.add(acceleration);

        // Enforce a maximum movement speed.
        double speed = this.movement.magnitude();
        if (speed > Max_Speed) {
            this.movement = this.movement.normalize().multiply(Max_Speed);
        }
    }

    /**
     * Moves the character based on its movement vector.
     * Also wraps the character around the screen edges.
     */
    public void move() {

        // Update the character’s position based on movement vector.
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());

        // Wrap around screen edges to create a seamless game space.
        if (this.character.getTranslateX() < 0) {
            this.character.setTranslateX(this.character.getTranslateX() + Controller.Width);
        }
        if (this.character.getTranslateX() > Controller.Width) {
            this.character.setTranslateX(this.character.getTranslateX() % Controller.Width);
        }
        if (this.character.getTranslateY() < 0) {
            this.character.setTranslateY(this.character.getTranslateY() + Controller.Height);
        }
        if (this.character.getTranslateY() > Controller.Height) {
            this.character.setTranslateY(this.character.getTranslateY() % Controller.Height);
        }

    }

    /**
     * Checks if this character collides with another character.
     * Uses JavaFX shape intersection for collision detection.
     * 
     * 'other' checks the other character to check for collision.
     * 'return True' if a collision is detected, false otherwise.
     */
    public boolean collide(Character other) {
        if (other instanceof Character) {
            Shape collisionArea = Shape.intersect(this.getCharacter(), other.getCharacter());
            return collisionArea.getBoundsInLocal().getWidth() != -1;
        } else {
            return false;
        }
    }

    // Returns the size of the character.
    public int getSize() {
        return this.size;
    }
}