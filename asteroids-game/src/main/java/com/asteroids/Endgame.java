package com.asteroids;

import javafx.stage.Stage;

/// Manages game end states: win or loss.
/// Displays the appropriate menu and stops the game loop.
public class Endgame {

    private final Stage stage;
    private final Menus menuManager;
    private final Scoring scoreManager;
    private final GameLoop gameLoop;

    public Endgame(Stage stage, Menus menuManager, Scoring scoreManager, GameLoop gameLoop) {
        this.stage = stage;
        this.menuManager = menuManager;
        this.scoreManager = scoreManager;
        this.gameLoop = gameLoop;
    }

    /// Handles the win condition: stops game, shows win menu, and stores score.
    public void handleWin(int points) {
        System.out.println("Endgame: Player wins with " + points + " points.");
        if (gameLoop != null) {
            System.out.println("Endgame: Stopping game loop...");
            gameLoop.stop();
        }

        menuManager.setFinalScore(points);
        menuManager.showWinMenu();
        menuManager.getRoot().getChildren().add(menuManager.getWinMenu());
    }

    /// Handles the loss condition: stops game and shows lose menu.
    public void handleLose(int points) {
        System.out.println("Endgame: Player loses with " + points + " points.");
        if (gameLoop != null) {
            gameLoop.stop();
        }

        menuManager.setFinalScore(points);
        menuManager.getRoot().getChildren().clear();
        menuManager.showLoseMenu();
        menuManager.getRoot().getChildren().add(menuManager.getLoseMenu());
    }
}