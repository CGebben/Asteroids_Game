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
        if (gameLoop != null) {
            gameLoop.stop();
        }
        menuManager.setFinalScore(points);
        menuManager.showWinMenu();
    }

    public void handleLose(int points) {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        menuManager.showLoseMenu();
    }
}