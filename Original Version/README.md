# Asteroids_Game

## Overview

This is a JavaFX-based implementation of the classic arcade game Asteroids. The game was developed as a group project for an Introduction to Java module final project. It follows specific academic guidelines rather than being a fully customized implementation. The project features enemy AI, level progression, and a scoring system. This project follows specific academic guidelines, and while fully functional, improvements to code structure, UI, and gameplay are planned for future updates.

## Contributors

This project was developed by the following team members:

- Colton Gebben (Me)
- James Barlow Wareham
- Suwen Ji
- Yunni
- Mugesh

(Note: Some last names are unavailable due to limitations in project records.)

## Features

- Ship Movement & Physics: Players control a spaceship that can move, rotate, and shoot bullets.
- Enemy AI: Aliens and asteroids that move and collide with the player.
- Level Progression: Increasing difficulty across multiple levels.
- Score Tracking: Players earn points by destroying enemies.
- Game Over & High Score System: The game records player scores upon losing.

## My Contributions

I contributed to the following aspects of the project:

- Level System: Designed and implemented level progression.
- Game Loop: Managed the core game logic, specifically scene iteration updates.
- Object Iterations: Handled interactions between bullets, enemies, and asteroids.
- Debugging: Identified and resolved gameplay and logic issues.

## Code Breakdown

### Main Components:

- App.java - Initializes the JavaFX application and loads the main menu UI.
- Controller.java - Manages game logic, player input, and collision detection.
- Ship.java - Represents the player's ship and its movement mechanics.
- Bullet.java - Handles bullet movement and interactions.
- Asteroid.java - Defines asteroid behavior and movement.
- Alien.java - Controls enemy alien ships and their movement.
- Level.java - Manages level structure and enemy spawning.
- Character.java - Base class for all moving objects (ship, asteroids, bullets, etc.).

### Additional Files:

- main.fxml - Defines the JavaFX UI layout.
- pom.xml - Manages dependencies and build configuration using Maven.
- module-info.java - Specifies Java module dependencies.

## Areas for Improvement

While the game is functional, several aspects could be refined due to time constraints:

### Code Structure & Organization

- Compartmentalization of Code: The Controller.java class handles too many responsibilities and should be broken into smaller classes.

- Better Separation of Concerns: Game logic, UI elements, and physics should be more modular.

### UI & Game Design

- Game Over Screen: Needs a more structured design. Ideally, the “Game Over” message and score input should be on the same screen.

- Ship Acceleration & Drifting: The current mechanics are functional but not fun. Movement should feel more responsive and less restrictive.

- Consistent UI Scaling: The interface elements need adjustments for better readability and player experience.

## Setup & Running the Game

Requirements:

- Java 21+
- Maven
- JavaFX 21+

Steps:

1. Clone the repository.

2. Compile and run with Maven:

```
mvn javafx:run

```

## Future Plans

- Refactoring and cleaning up the code structure.
- Implementing smoother physics for the ship.
- Improving the UI for better user experience.
- Enhancing enemy AI behavior.

## License

This project was developed for academic purposes and is not intended for commercial use.
