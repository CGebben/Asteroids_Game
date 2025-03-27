package com.asteroids;

import javafx.stage.Stage;

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

    public void handleWin(int points) {
        System.out.println("Endgame.handleWin() called with points: " + points);
        if (gameLoop != null) {
            System.out.println("Stopping game loop...");
            gameLoop.stop();
        }

        // Re-add and show only the win menu
        menuManager.setFinalScore(points);
        menuManager.showWinMenu();
        menuManager.getRoot().getChildren().add(menuManager.getWinMenu());
    }

    public void handleLose(int points) {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        menuManager.setFinalScore(points); // reuse same method
        menuManager.getRoot().getChildren().clear();
        menuManager.showLoseMenu();
        menuManager.getRoot().getChildren().add(menuManager.getLoseMenu());
    }
}