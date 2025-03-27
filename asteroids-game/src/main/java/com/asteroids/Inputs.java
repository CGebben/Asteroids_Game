package com.asteroids;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;

/// Tracks keyboard input by monitoring key press and release events.
/// Used to handle player controls during gameplay.
public class Inputs {

    // --- Fields ---
    private final Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

    // --- Setup ---

    /// Initializes key listeners on the given scene.
    public void initialize(Scene scene) {
        System.out.println("Input handler initialized.");
        scene.setOnKeyPressed(event -> pressedKeys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> pressedKeys.put(event.getCode(), false));
    }

    // --- Input Queries ---

    /// Returns true if the given key is currently pressed.
    public boolean isKeyPressed(KeyCode key) {
        return pressedKeys.getOrDefault(key, false);
    }

    /// Removes the key from tracking (useful for single-trigger actions).
    public void consumeKey(KeyCode key) {
        pressedKeys.remove(key);
    }
}