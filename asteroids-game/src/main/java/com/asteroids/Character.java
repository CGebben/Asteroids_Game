package com.asteroids;

// Import necessary JavaFX classes for positioning and collision detection.
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/// Represents a character in the game, including the player’s ship and enemies.
/// Provides movement, rotation, and collision detection functionality.
public class Character {
    private int size;
    private Point2D movement;
    private Polygon character;

    public Character(Polygon shape, int x, int y) {
        this.character = shape;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.movement = new Point2D(0, 0);
    }

    public Polygon getCharacter() {
        return this.character;
    }

    public Point2D getMovement() {
        return this.movement;
    }

    public void setMovement(double x, double y) {
        this.movement = this.movement.add(x, y);
    }

    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 1);
    }

    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 1);
    }

    private static final double Max_Speed = 5.0;

    /// Adds directional vector to movement based on current rotation.
    public void forwardMotion() {
        double angle = Math.toRadians(this.character.getRotate());
        double X = Math.cos(angle) * 0.01;
        double Y = Math.sin(angle) * 0.01;
        Point2D push = new Point2D(X, Y);
        this.movement = this.movement.add(push);
    }

    /// Adds directional vector in the opposite direction (reverse).
    public void backwardMotion() {
        double angle = Math.toRadians(this.character.getRotate());
        double X = -Math.cos(angle) * 0.01;
        double Y = -Math.sin(angle) * 0.01;
        Point2D reverse = new Point2D(X, Y);
        this.movement = this.movement.add(reverse);
    }

    /// Caps the character's movement speed if it exceeds Max_Speed.
    public void acc() {
        double speed = this.movement.magnitude();
        if (speed > Max_Speed) {
            this.movement = this.movement.normalize().multiply(Max_Speed);
        }
    }

    /// Returns a normalized Point2D representing the character's forward-facing
    /// direction.
    public Point2D getForwardVector() {
        double angle = Math.toRadians(this.character.getRotate());
        return new Point2D(Math.cos(angle), Math.sin(angle)).normalize();
    }

    /// Decelerates the character slightly each frame.
    public void dec() {
        double decelerationRate = 0.98;
        this.movement = this.movement.multiply(decelerationRate);
    }

    /// Moves the character and wraps around screen edges.
    public void move() {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());

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

    /// Checks collision with another character using shape intersection.
    public boolean collide(Character other) {
        Shape collisionArea = Shape.intersect(this.getCharacter(), other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    public int getSize() {
        return this.size;
    }
}