package com.example.asteroidsadvanced;

// import JavaFX Polygon class
import javafx.scene.shape.Polygon;

// define a Bullets class that extends the Character class
public class Bullet extends Character{

    // constructor method for Bullets class that takes two integers as arguments
    public Bullet( int x, int y) {
        // create a new Polygon object with 4 points and pass it to the parent Character class constructor along with the x and y arguments
        super(new Polygon(5,-5,5,5,-5,5,-5,-5), x+50, y+30);
    }

    // move method for Bullets class that moves the Bullet object by the current movement value
    @Override
    public void move() {
        // set the new x and y position of the Bullet object based on its current position and movement values
        super.getCharacter().setTranslateX(super.getCharacter().getTranslateX()+ super.getMovement().getX());
        super.getCharacter().setTranslateY(super.getCharacter().getTranslateY()+ super.getMovement().getY());
    }

    // acc method for Bullets class that modifies the movement value of the Bullet object
    @Override
    public void acc() {
        // call the parent Character class acc method to increment the current movement value
        super.acc();
        // calculate the new x and y movement values based on the current angle of the Bullet object and set them using the parent Character class setMovement method
        double angle = Math.toRadians(super.getCharacter().getRotate());
        double X = Math.cos(angle) * 5;
        double Y = Math.sin(angle) * 5;
        super.setMovement(X,Y);
    }
}