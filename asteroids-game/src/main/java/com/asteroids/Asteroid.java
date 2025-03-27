package com.asteroids;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import java.util.Random;

/// Represents an asteroid that moves, rotates, and can split into smaller ones.
public class Asteroid extends Character {

    // --- Fields ---
    private double rotationalMovement;
    private int accelerationAmount;

    // --- Constructor ---
    public Asteroid(int x, int y, int z) {
        super(new Polygon(
                25.0 * z, 0.0,
                50.0 * z, 15.0 * z,
                40.0 * z, 40.0 * z,
                10.0 * z, 40.0 * z,
                0.0, 15.0 * z), x, y);

        super.size = z;
        super.setMovement(0.1, 0.1);

        Random rnd = new Random();
        super.getCharacter().setRotate(rnd.nextInt(360));

        if (z == 3)
            this.accelerationAmount = 1 + rnd.nextInt(10);
        if (z == 2)
            this.accelerationAmount = 1 + rnd.nextInt(20, 30);
        if (z == 1)
            this.accelerationAmount = 1 + rnd.nextInt(40, 70);

        for (int i = 0; i < this.accelerationAmount; i++) {
            acc();
        }

        this.rotationalMovement = 0.5 - rnd.nextDouble();

        System.out.println("Asteroid spawned: size = " + z + ", position = (" + x + ", " + y + ")");
    }

    // --- Movement ---
    @Override
    public void move() {
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + this.rotationalMovement);
    }

    // --- Collision ---
    public boolean collide(Character other) {
        Shape collisionArea = Shape.intersect(super.getCharacter(), other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
}