module hpmays03 {
    requires transitive java.net.http;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.web;
    requires transitive com.google.gson;

    opens hpmays03.src;
    exports hpmays03.src;
}
