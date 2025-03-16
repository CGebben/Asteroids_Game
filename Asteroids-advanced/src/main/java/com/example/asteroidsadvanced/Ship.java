package com.example.asteroidsadvanced;

import javafx.animation.Timeline;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class Ship extends Character{
    private boolean isInvincible = false;

    private boolean isHyperspaced = false;

    Timeline timeline = new Timeline();
    public Ship(int x, int y) {
        super(new Polygon(0,0,100,25,0,50,25,25), x, y);
    }

    public void setInvincible(boolean invincible) {
        this.isInvincible = invincible;
    }

    public boolean isInvincible() {
        return isInvincible;
    }


    public void Hyperspace() {
        this.setHyperspaced(true);
    
        double newX;
        double newY;
    
        do {
            newX = Math.random() * (Controller.Width - 100 - this.getCharacter().getBoundsInParent().getWidth()) + 50;
            newY = Math.random() * (Controller.Height - 100 - this.getCharacter().getBoundsInParent().getHeight()) + 50;
        } while (Math.abs(newX - getCharacter().getTranslateX()) < 100 && Math.abs(newY - getCharacter().getTranslateY()) < 100);
    
        super.getCharacter().setTranslateX(newX);
        super.getCharacter().setTranslateY(newY);
        super.setMovement(-super.getMovement().getX(), -super.getMovement().getY());
    }

    public boolean collide(Character other){
        Shape collisionArea = Shape.intersect(super.getCharacter(), other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    public boolean isHyperspaced() {
        return isHyperspaced;
    }

    public void setHyperspaced(boolean hyperspaced) {
        isHyperspaced = hyperspaced;
    }
}