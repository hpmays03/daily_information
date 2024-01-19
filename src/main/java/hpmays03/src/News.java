package hpmays03.src;

import java.net.http.HttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class News extends VBox{

    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build();
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    NewsBlock block1;
    NewsBlock block2;
    NewsBlock block3;
    VBox root;
    static NewsBlock blocks[] = new NewsBlock[3];
    private static Alert a = new Alert(AlertType.NONE);
    public News() {
        super();
        block1 = new NewsBlock("Requires ZipCode", "waiting", null, null, null);
        block2 = new NewsBlock(null, null, null, null, null);
        block2 = new NewsBlock(null, null, null, null, null);
        root = new VBox();
        blocks[0] = block1;
        blocks[1] = block2;
        blocks[2] = block3;
        this.getChildren().addAll(blocks[0], blocks[1], blocks[2]);
    }
}
