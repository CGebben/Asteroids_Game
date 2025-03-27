package com.asteroids;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/// Represents a character in the game (e.g., ship, asteroid).
/// Provides movement, rotation, speed control, and collision detection.
public class Character {

    // --- Fields ---
    protected int size;
    private Point2D movement;
    private Polygon character;

    // --- Constants ---
    private static final double Max_Speed = 5.0;

    // --- Constructor ---
    public Character(Polygon shape, int x, int y) {
        this.character = shape;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.movement = new Point2D(0, 0);
    }

    // --- Public Accessors ---
    public Polygon getCharacter() {
        return this.character;
    }

    public Point2D getMovement() {
        return this.movement;
    }

    public int getSize() {
        return this.size;
    }

    // --- Movement Control ---
    public void setMovement(double x, double y) {
        this.movement = this.movement.add(x, y);
    }

    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 1);
    }

    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 1);
    }

    public void forwardMotion() {
        double angle = Math.toRadians(this.character.getRotate());
        Point2D push = new Point2D(Math.cos(angle), Math.sin(angle)).multiply(0.01);
        this.movement = this.movement.add(push);
    }

    public void backwardMotion() {
        double angle = Math.toRadians(this.character.getRotate());
        Point2D reverse = new Point2D(-Math.cos(angle), -Math.sin(angle)).multiply(0.01);
        this.movement = this.movement.add(reverse);
    }

    // --- Speed Control ---

    public void acc() {
        if (this.movement.magnitude() > Max_Speed) {
            this.movement = this.movement.normalize().multiply(Max_Speed);
        }
    }

    public void dec() {
        this.movement = this.movement.multiply(0.98);
    }

    // --- Movement Execution ---
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

    // --- Utilities ---
    public Point2D getForwardVector() {
        double angle = Math.toRadians(this.character.getRotate());
        return new Point2D(Math.cos(angle), Math.sin(angle)).normalize();
    }

    // --- Collision ---
    public boolean collide(Character other) {
        Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
}