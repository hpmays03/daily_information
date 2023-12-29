package hpmays03;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    Stage stage;
    Scene scene;
    VBox root;

    public App() {
        root = new VBox();
    }
    public void init() {
        
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        scene = new Scene(root, 640, 480);
        stage.setTitle("daily information");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}