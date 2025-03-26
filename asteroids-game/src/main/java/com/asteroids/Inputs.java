package com.asteroids;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;

/**
 * This class tracks key presses and releases to manage player input.
 */
public class Inputs {
    private final Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

    /**
     * Sets up input tracking for the game.
     * The scene listens for key presses and releases.
     */
    public void initialize(Scene scene) {
        scene.setOnKeyPressed(event -> pressedKeys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> pressedKeys.put(event.getCode(), false));
    }

    /**
     * Checks if a specific key is currently being pressed.
     * Returns true if the key is pressed, false otherwise.
     */
    public boolean isKeyPressed(KeyCode key) {
        return pressedKeys.getOrDefault(key, false);
    }

    /**
     * Removes a key from active tracking.
     * Useful for actions that should trigger once per press, such as hyperspace.
     */
    public void consumeKey(KeyCode key) {
        pressedKeys.remove(key);
    }
}