package com.asteroids;

// Import JavaFX libraries for animations, shapes, and durations.
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.shape.*;
import javafx.util.Duration;

/**
 * Represents an Alien enemy in the game.
 * The Alien moves along a predefined path using a Timeline animation.
 * It extends the Character class, inheriting movement properties.
 */
public class Alien extends Character {

        // Timeline to control the alien's movement pattern.
        Timeline timeline = new Timeline();

        // Initial position of the Alien.
        int X = 0;
        int Y = 0;

        // The size of the Alien (default is 4, possibly indicating difficulty or hitbox
        // size).
        private int size = 4;

        /**
         * Constructs an Alien object at the specified (x, y) position.
         * The Alien's shape is defined using a Polygon.
         *
         * x The initial X-coordinate of the Alien.
         * y The initial Y-coordinate of the Alien.
         */
        public Alien(int x, int y) {
                // Call superclass constructor with a polygon shape representing the alien.
                super(new Polygon(130, 50, 110, 80, 0, 80, -20, 50, 20, 50, 30, 30, 80, 30, 90, 50), x, y);
                super.setMovement(0.1, 0.1);
                this.X = x;
                this.Y = y;
        }

        /**
         * Moves the Alien along a predefined looping path using a Timeline animation.
         * The path consists of multiple keyframes over an 8-second period.
         */
        @Override
        public void move() {
                timeline.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(1),
                                                new KeyValue(super.getCharacter().translateXProperty(), (150 + X)),
                                                new KeyValue(super.getCharacter().translateYProperty(), (100 + Y))));
                timeline.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(2),
                                                new KeyValue(super.getCharacter().translateXProperty(), (450 + X)),
                                                new KeyValue(super.getCharacter().translateYProperty(), (100 + Y))));
                timeline.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(3),
                                                new KeyValue(super.getCharacter().translateXProperty(), (600 + X)),
                                                new KeyValue(super.getCharacter().translateYProperty(), Y)));
                timeline.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(4),
                                                new KeyValue(super.getCharacter().translateXProperty(), (850 + X)),
                                                new KeyValue(super.getCharacter().translateYProperty(), Y)));
                timeline.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(5),
                                                new KeyValue(super.getCharacter().translateXProperty(), (1150 + X)),
                                                new KeyValue(super.getCharacter().translateYProperty(), (100 + Y))));
                timeline.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(6),
                                                new KeyValue(super.getCharacter().translateXProperty(), (1450 + X)),
                                                new KeyValue(super.getCharacter().translateYProperty(), (100 + Y))));
                timeline.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(7),
                                                new KeyValue(super.getCharacter().translateXProperty(), (1600 + X)),
                                                new KeyValue(super.getCharacter().translateYProperty(), Y)));
                timeline.getKeyFrames()
                                .add(new KeyFrame(Duration.seconds(8),
                                                new KeyValue(super.getCharacter().translateXProperty(), (1800 + X)),
                                                new KeyValue(super.getCharacter().translateYProperty(), Y)));

                // Set the movement pattern to loop back in reverse after reaching the last
                // point.
                timeline.setAutoReverse(true);

                // The movement cycle repeats indefinitely.
                timeline.setCycleCount(Timeline.INDEFINITE);

                // Start the timeline animation.
                timeline.play();
        }

        // Returns the size of the Alien.
        public int getSize() {
                return this.size;
        }
}