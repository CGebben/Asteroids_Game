package com.example.asteroidsadvanced;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.Random;

public class Asteroid extends Character{
    private double rotationalMovement;
    private int size;
    int accelerationAmount;
    public Asteroid(int x, int y, int z) {
        super(new Polygon(25.0*z, 0.0, 50.0*z, 15.0*z, 40.0*z, 40.0*z, 10.0*z, 40.0*z, 0.0, 15.0*z), x, y);
        this.size = z;
        super.setMovement(0.1,0.1);
        Random rnd = new Random();
        super.getCharacter().setRotate(rnd.nextInt(360));
        if (z == 3){this.accelerationAmount = 1 + rnd.nextInt(10);}
        if (z == 2){this.accelerationAmount = 1 + rnd.nextInt(20,30);}
        if (z == 1){this.accelerationAmount = 1 + rnd.nextInt(40,70);}
        for(int i = 0; i < this.accelerationAmount; i++){ acc();}

        this.rotationalMovement = 0.5 - rnd.nextDouble();
    }

    @Override
    public void move() {
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + this.rotationalMovement);
    }

    public int getSize(){
        return this.size;
    }

    public boolean collide(Character other){
        Shape collisionArea = Shape.intersect(super.getCharacter(), other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
}