package hpmays03.src;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private static List<String> uniqueTitles = new ArrayList<>();
    private static final String NEWS_API = "find a news API to use";
    
    public static NewsBlock pullNews(int num) {
        try {
            //parameters
            String query = String.format(); //complete the line when found news API
            String uri = NEWS_API + query;
            URI resource = URI.create(uri);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(resource).build();
            BodyHandler<String> bodyHandler = BodyHandlers.ofString();
            HttpResponse<String> response = HTTP_CLIENT.<String>send(request, bodyHandler);
            int status = response.statusCode();
            if (status != 200) {
                throw new IOException("HTTP " + status);
            }
            String body = response.body();
        } catch (IOException | InterruptedException cause) {
            System.err.println(cause);
            cause.printStackTrace();
            return null;
        }
    }

}
