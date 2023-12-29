module hpmays03 {
    requires javafx.controls;
    requires javafx.fxml;

    opens hpmays03 to javafx.fxml;
    exports hpmays03;
}
