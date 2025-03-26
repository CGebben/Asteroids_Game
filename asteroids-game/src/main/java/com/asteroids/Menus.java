package com.asteroids;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Menus {
    private final Pane root; // The game pane to which menus are added

    private VBox mainMenu;
    private VBox winMenu;
    private VBox loseMenu;

    public Menus(Pane root, Scoring scoreManager, Runnable onStart, Runnable onHighScores, Runnable onInstructions,
            Runnable onPlayAgain, Runnable onMainMenu, Runnable onQuit) {
        this.root = root;
        this.mainMenu = createMainMenu(onStart, onHighScores, onInstructions);
        this.winMenu = createWinMenu(onPlayAgain, onMainMenu, onQuit);
        this.loseMenu = createLoseMenu(onPlayAgain, onMainMenu, onQuit);
        this.scoreManager = scoreManager;

        this.root.getChildren().addAll(mainMenu, winMenu, loseMenu);

        // Show only main menu at start
        showMainMenu();
    }

    private VBox createMainMenu(Runnable onStart, Runnable onHighScores, Runnable onInstructions) {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setPrefSize(Controller.Width, Controller.Height);
        menu.setStyle("-fx-background-color: black;");

        Text title = new Text("ASTEROIDS");
        title.setFont(Font.font("System", 48));
        title.setFill(Color.WHITE);

        Button startButton = createButton("START", onStart);
        Button scoreButton = createButton("SCOREBOARD", onHighScores);
        Button howToButton = createButton("HOW TO PLAY", onInstructions);

        menu.getChildren().addAll(title, startButton, scoreButton, howToButton);
        return menu;
    }

    private VBox createWinMenu(Runnable onPlayAgain, Runnable onMainMenu, Runnable onQuit) {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setPrefSize(Controller.Width, Controller.Height);
        menu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);"); // Slightly transparent

        Text winText = new Text("YOU WIN!");
        winText.setFont(Font.font("System", 48));
        winText.setFill(Color.WHITE);

        Button enterScoreButton = createButton("ENTER SCORE", () -> {
            scoreManager.enterScore(finalScore);
        });

        Button playAgainButton = createButton("PLAY AGAIN", onPlayAgain);
        Button mainMenuButton = createButton("MAIN MENU", onMainMenu);
        Button quitButton = createButton("QUIT", onQuit);

        menu.getChildren().addAll(winText, enterScoreButton, playAgainButton, mainMenuButton, quitButton);
        return menu;
    }

    private VBox createLoseMenu(Runnable onPlayAgain, Runnable onMainMenu, Runnable onQuit) {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setPrefSize(Controller.Width, Controller.Height);
        menu.setStyle("-fx-background-color: black;");

        Text loseText = new Text("GAME OVER!");
        loseText.setFont(Font.font("System", 48));
        loseText.setFill(Color.WHITE);

        Button playAgainButton = createButton("TRY AGAIN", onPlayAgain);
        Button mainMenuButton = createButton("MAIN MENU", onMainMenu);
        Button quitButton = createButton("QUIT", onQuit);

        menu.getChildren().addAll(loseText, playAgainButton, mainMenuButton, quitButton);
        return menu;
    }

    public void showMainMenu() {
        mainMenu.setVisible(true);
        winMenu.setVisible(false);
        loseMenu.setVisible(false);
    }

    public void showWinMenu() {
        mainMenu.setVisible(false);
        winMenu.setVisible(true);
        loseMenu.setVisible(false);
    }

    public void showLoseMenu() {
        mainMenu.setVisible(false);
        winMenu.setVisible(false);
        loseMenu.setVisible(true);
    }

    public void hideMenus() {
        mainMenu.setVisible(false);
        winMenu.setVisible(false);
        loseMenu.setVisible(false);
    }

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setFont(Font.font("System", 24));
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: white; -fx-text-fill: black;"));

        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: white;"));

        button.setOnAction(e -> action.run());
        return button;
    }

    private final Scoring scoreManager;
    private int finalScore = 0;

    public void setFinalScore(int points) {
        this.finalScore = points;
    }
}
