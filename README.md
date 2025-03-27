# Asteroids_Game (Refactored Version)

## Overview

This project is a refactored and cleaned-up version of a JavaFX-based implementation of the classic arcade game Asteroids. Originally developed as a group project for an academic module, the initial version followed specific assignment guidelines and had limited scope due to time constraints. This new version represents a solo upgrade and restructuring effort by Colton Gebben to improve gameplay, code organization, and visual consistency while maintaining the spirit of the original.

Special credit goes to Yunni, whose original contributions to character logic and aesthetic formed the foundation for much of the game's feel and behavior.

This refactor addresses areas that were previously rushed or incomplete, and introduces new systems to stabilize game flow and make the codebase easier to understand, maintain, and extend.

## Contributors

This project was developed by the following team members:

- Colton Gebben (Me)
- James Barlow Wareham
- Suwen Ji
- Yunni
- Mugesh

(Note: Some last names are unavailable due to limitations in project records.)

## My Contributions

### Original Version

- Designed and implemented the Level System.
- Managed the Game Loop and core scene update logic.
- Built the Object Interactions between ship, bullets, and enemies.
- Conducted Debugging for gameplay and logic flow.

### Refactored Version

- Moved major responsibilities out of Controller.java into dedicated classes:

GameLoop: All game update logic
Collisions: All hit detection and point/life handling
Instructions: Pop-up for instructions
Scoring: Scoreboard and saving functionality
Inputs: Key press tracking

- Created the Menus and Endgame classes for structured win/lose handling.
- Replaced FXML-based UI with JavaFX code-based menus for better flexibility.
- Smoothed out movement mechanics to be more responsive and satisfying.
- Simplified UI and cleaned up inconsistent visuals.

## Features

- Smooth ship movement and acceleration, including reverse controls.
- Bullet system and enemy asteroid destruction with score tracking.
- Cleanly separated logic for gameplay, scoring, and UI.
- Win and Lose menus with Play Again, Quit, and Score-saving functionality.

## Code Structure

### Game Flow

- App.java – Entry point for JavaFX application.
- Controller.java – Initializes scene and sets up UI and game components.
- GameLoop.java – Core animation loop and input handling.
- Endgame.java – Transitions between game and end states.

### Object & Logic

- Ship.java – Handles player movement, invincibility, and hyperspace.
- Bullet.java – Simple bullet object with movement.
- Asteroid.java – Rotating enemy object with sizes and break-up logic.
- Character.java – Base class for all moving entities.
- Level.java – Defines enemy layouts for each level.
- Collisions.java – Checks and handles all hit interactions.

### Utility

- Menus.java – Displays main, win, and lose menus.
- Scoring.java – Manages score saving and viewing.
- Instructions.java – Displays game instructions from file.
- Inputs.java – Key tracking system.

### Build & Configuration

- pom.xml – Maven dependencies and build config (JavaFX, Java 21).
- module-info.java – Java module system declaration.

## Setup & Running the Game

### Requirements

Java 21+
Maven
JavaFX 21+

### Run Instructions

cd asteroids-game
mvn javafx:run

## Changes from the Original Project

- Removed reliance on FXML; menus now defined in Java.
- Code refactored for better separation of concerns.
- Game flow clearly divided between setup, loop, and end states.
- Player movement enhanced for better responsiveness.
- Score input added for win screen.
- UI made consistent across screens.

## Areas for Future Work

- Reintroduce Alien enemy and associated logic.
- Add Main Menu button back into win/lose menus when transition bug is resolved.
- Create more distinct level designs beyond showcasing asteroid sizes.
- Add a timer or mechanic for temporary invincibility display.
- Implement life-rewarding mechanics (e.g., extra lives on score milestones).
- Improve instruction popup layout (currently too small).

## License

This project was developed for academic purposes and later refactored for personal growth and demonstration. It is not intended for commercial use.
