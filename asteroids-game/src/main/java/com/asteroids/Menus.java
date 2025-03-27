package com.asteroids;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/// Handles all game menu interfaces: main menu, win menu, and lose menu.
/// Responsible for displaying, styling, and attaching actions to buttons.
public class Menus {

    // --- Fields ---
    private final Pane root;
    private final Scoring scoreManager;

    private VBox mainMenu;
    private VBox winMenu;
    private VBox loseMenu;
    private int finalScore = 0;

    // --- Constructor ---

    public Menus(
            Pane root,
            Scoring scoreManager,
            Runnable onStart,
            Runnable onHighScores,
            Runnable onInstructions,
            Runnable onPlayAgain,
            Runnable onMainMenu, // currently unused due to transition bug
            Runnable onQuit) {

        this.root = root;
        this.scoreManager = scoreManager;

        this.mainMenu = createMainMenu(onStart, onHighScores, onInstructions, onQuit);
        this.winMenu = createWinMenu(onPlayAgain, onQuit);
        this.loseMenu = createLoseMenu(onPlayAgain, onQuit);

        root.getChildren().addAll(mainMenu, winMenu, loseMenu);
        showMainMenu();
    }

    // --- Menu Creation ---

    private VBox createMainMenu(Runnable onStart, Runnable onHighScores, Runnable onInstructions, Runnable onQuit) {
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
        Button quitButton = createButton("QUIT", onQuit);

        menu.getChildren().addAll(title, startButton, scoreButton, howToButton, quitButton);
        return menu;
    }

    private VBox createWinMenu(Runnable onPlayAgain, Runnable onQuit) {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setPrefSize(Controller.Width, Controller.Height);
        menu.setStyle("-fx-background-color: black;");

        Text winText = new Text("YOU WIN!");
        winText.setFont(Font.font("System", 48));
        winText.setFill(Color.WHITE);

        Button enterScoreButton = createButton("ENTER SCORE", () -> scoreManager.enterScore(finalScore));
        Button playAgainButton = createButton("PLAY AGAIN", onPlayAgain);
        Button quitButton = createButton("QUIT", onQuit);

        menu.getChildren().addAll(winText, enterScoreButton, playAgainButton, quitButton);
        return menu;
    }

    private VBox createLoseMenu(Runnable onPlayAgain, Runnable onQuit) {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setPrefSize(Controller.Width, Controller.Height);
        menu.setStyle("-fx-background-color: black;");

        Text loseText = new Text("GAME OVER!");
        loseText.setFont(Font.font("System", 48));
        loseText.setFill(Color.WHITE);

        Button playAgainButton = createButton("TRY AGAIN", onPlayAgain);
        Button quitButton = createButton("QUIT", onQuit);

        menu.getChildren().addAll(loseText, playAgainButton, quitButton);
        return menu;
    }

    // --- Button Factory ---

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setFont(Font.font("System", 24));
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: white;"));
        button.setOnAction(e -> action.run());

        return button;
    }

    // --- Menu Visibility Control ---

    public void showMainMenu() {
        mainMenu.setVisible(true);
        winMenu.setVisible(false);
        loseMenu.setVisible(false);
    }

    public void showWinMenu() {
        System.out.println("Menus.showWinMenu() called");
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

    // --- Utility ---

    public void setFinalScore(int points) {
        this.finalScore = points;
    }

    public Pane getRoot() {
        return root;
    }

    public VBox getWinMenu() {
        return winMenu;
    }

    public VBox getLoseMenu() {
        return loseMenu;
    }
}