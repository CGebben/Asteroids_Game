package com.asteroids;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/// Defines enemy configurations for different game levels.
/// Each level specifies a number of asteroids of varying sizes.
public class Level {

    // --- Fields ---
    private int levelNumber;
    private int numSize1Asteroids;
    private int numSize2Asteroids;
    private int numSize3Asteroids;

    private final List<Character> enemyList;
    private final Random rnd = new Random();

    // --- Static Utility ---

    /// Creates an array of predefined levels with increasing difficulty.
    public static Level[] createLevels() {
        System.out.println("Creating predefined levels...");
        return new Level[] {
                new Level(1, 1, 0, 0),
                new Level(2, 0, 1, 0),
                new Level(3, 0, 0, 1)
        };
    }

    // --- Constructor ---

    /// Constructs a level with a specific number of asteroids.
    public Level(int levelNumber, int numSize1Asteroids, int numSize2Asteroids, int numSize3Asteroids) {
        this.levelNumber = levelNumber;
        this.numSize1Asteroids = numSize1Asteroids;
        this.numSize2Asteroids = numSize2Asteroids;
        this.numSize3Asteroids = numSize3Asteroids;

        this.enemyList = new ArrayList<>();

        System.out.println("Level " + levelNumber + " created:");
        System.out.println(" - Size 1 Asteroids: " + numSize1Asteroids);
        System.out.println(" - Size 2 Asteroids: " + numSize2Asteroids);
        System.out.println(" - Size 3 Asteroids: " + numSize3Asteroids);

        for (int i = 0; i < numSize1Asteroids; i++) {
            enemyList.add(new Asteroid(rnd.nextInt(1000), rnd.nextInt(1000), 1));
        }
        for (int i = 0; i < numSize2Asteroids; i++) {
            enemyList.add(new Asteroid(rnd.nextInt(1000), rnd.nextInt(1000), 2));
        }
        for (int i = 0; i < numSize3Asteroids; i++) {
            enemyList.add(new Asteroid(rnd.nextInt(1000), rnd.nextInt(1000), 3));
        }
    }

    // --- Accessors ---

    public List<Character> getEnemyList() {
        return enemyList;
    }

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
}