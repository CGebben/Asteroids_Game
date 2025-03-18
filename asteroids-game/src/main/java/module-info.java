/**
 * The module-info.java file specifies dependencies and accessibility for the
 * Java module.
 * This ensures proper integration with JavaFX and encapsulation of the
 * application.
 */
module com.asteroids {

    // Require JavaFX modules transitively, allowing access in dependent modules.
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive javafx.base;

    // Open the package to JavaFX to allow reflection-based access.
    opens com.asteroids to javafx.fxml, javafx.graphics;

    // Export the package to make it accessible to other modules.
    exports com.asteroids;
}