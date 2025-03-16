module com.example.asteroidsadvanced {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.example.asteroidsadvanced to javafx.fxml;
    exports com.example.asteroidsadvanced;
}