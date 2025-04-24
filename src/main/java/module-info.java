module com.juegofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    opens com.juegofx to javafx.fxml;
    exports com.juegofx;
}