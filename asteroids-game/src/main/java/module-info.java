module com.asteroids {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive javafx.base;

    opens com.asteroids to javafx.fxml, javafx.graphics;

    exports com.asteroids;
}