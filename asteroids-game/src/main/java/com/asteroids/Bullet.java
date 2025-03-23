package com.asteroids;

// Import JavaFX Polygon class for defining the bullet shape.
import javafx.scene.shape.Polygon;

/// Represents a bullet fired by the player's ship.
public class Bullet extends Character {

    public Bullet(int x, int y) {
        super(new Polygon(5, -5, 5, 5, -5, 5, -5, -5), x + 50, y + 30);
    }

    @Override
    public void move() {
        super.getCharacter().setTranslateX(super.getCharacter().getTranslateX() + super.getMovement().getX());
        super.getCharacter().setTranslateY(super.getCharacter().getTranslateY() + super.getMovement().getY());
    }

    @Override
    public void acc() {
        super.acc();
        double angle = Math.toRadians(super.getCharacter().getRotate());
        double X = Math.cos(angle) * 5;
        double Y = Math.sin(angle) * 5;
        super.setMovement(X, Y);
    }
}