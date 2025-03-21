package com.asteroids;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Level class defines enemy configurations for different game levels.
 * It determines the number of asteroids and aliens present in a given level.
 */
public class Level {
    private int levelNumber;

    // Number of different types of enemies per level.
    private int numSize1Asteroids;
    private int numSize2Asteroids;
    private int numSize3Asteroids;
    private int numAliens;

    // List to store enemies (asteroids and aliens).
    private List<Character> enemyList;

    // Random number generator for enemy placement.
    private Random rnd = new Random();

    /**
     * Creates an array of Level objects, each defining different enemy setups.
     * The difficulty increases with each level.
     * 
     * Return an array containing predefined Level objects.
     */
    public static Level[] createLevels() {
        Level[] levels = new Level[5];

        levels[0] = new Level(1, 1, 0, 0, 0);
        levels[1] = new Level(2, 2, 1, 0, 0);
        levels[2] = new Level(3, 2, 1, 1, 0);
        levels[3] = new Level(4, 2, 2, 2, 0);
        levels[4] = new Level(5, 2, 2, 2, 1);

        return levels;
    }

    /**
     * Constructs a Level with a specified number of asteroids and aliens.
     * Generates enemy objects and adds them to the enemy list.
     **/

    public Level(int levelNumber, int numSize1Asteroids, int numSize2Asteroids, int numSize3Asteroids, int numAliens) {
        this.levelNumber = levelNumber;
        this.numSize1Asteroids = numSize1Asteroids;
        this.numSize2Asteroids = numSize2Asteroids;
        this.numSize3Asteroids = numSize3Asteroids;
        this.numAliens = numAliens;

        this.enemyList = new ArrayList<>();

        // Generate and place asteroids of different sizes.
        for (int i = 0; i < numSize1Asteroids; i++) {
            Asteroid asteroid = new Asteroid(rnd.nextInt(1000), rnd.nextInt(1000), 1);
            this.enemyList.add(asteroid);
        }

        for (int i = 0; i < numSize2Asteroids; i++) {
            Asteroid asteroid = new Asteroid(rnd.nextInt(1000), rnd.nextInt(1000), 2);
            this.enemyList.add(asteroid);
        }

        for (int i = 0; i < numSize3Asteroids; i++) {
            Asteroid asteroid = new Asteroid(rnd.nextInt(1000), rnd.nextInt(1000), 3);
            this.enemyList.add(asteroid);
        }

        for (int i = 0; i < numAliens; i++) {
            Alien alien = new Alien(rnd.nextInt(1000), rnd.nextInt(1000));
            this.enemyList.add(alien);
        }
    }

    // Retrieves the list of enemies present in this level.
    public List<Character> getEnemyList() {
        return enemyList;
    }

    // Getters and setters for level attributes.
    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getNumSize1Asteroids() {
        return numSize1Asteroids;
    }

    public void setNumSize1Asteroids(int numSize1Asteroids) {
        this.numSize1Asteroids = numSize1Asteroids;
    }

    public int getNumSize2Asteroids() {
        return numSize2Asteroids;
    }

    public void setNumSize2Asteroids(int numSize2Asteroids) {
        this.numSize2Asteroids = numSize2Asteroids;
    }

    public int getNumSize3Asteroids() {
        return numSize3Asteroids;
    }

    public void setNumSize3Asteroids(int numSize3Asteroids) {
        this.numSize3Asteroids = numSize3Asteroids;
    }

    public int getNumAliens() {
        return numAliens;
    }

    public void setNumAliens(int numAliens) {
        this.numAliens = numAliens;
    }
}