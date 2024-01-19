package hpmays03.src;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    Stage stage;
    Scene scene;
    HBox root;
    Weather weather;
    SearchBar searcher;
    VBox compile;
    HBox weatherNews;

    public App() {
        root = new HBox();
        weather = new Weather();
        searcher = new SearchBar();
        compile = new VBox();
        weatherNews = new HBox();
    }
    public void init() {
        Insets insets = new Insets(10,20,10,20);
        weather.setPadding(insets);
        weatherNews.getChildren().addAll(weather);
        compile.getChildren().addAll(searcher, weatherNews);
        root.getChildren().addAll(compile);
        root.isResizable();
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

}