package hpmays03;

import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Weather extends VBox{
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build();
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    
    Image image;
    static ImageView showImage;
    static Label location;
    static Label weatherStatus;
    static Label highLow;
    static Label windSpeed;
    static Label rainPercentage;
    static String city;
    static String longitude;
    static String latitude;
    static String state;
    private static Alert a = new Alert(AlertType.NONE);

    public Weather() {
        super();
        image = new Image("file:resources/default.png");
        showImage = new ImageView(image);
        showImage.setFitHeight(200);
        showImage.setFitWidth(200);
        location =  new Label("None shown since no ZipCode entered");
        weatherStatus = new Label("N/A");
        highLow = new Label("N/A");
        windSpeed = new Label("N/A");
        rainPercentage = new Label("N/A");
        Insets insets = new Insets(10, 0, 10, 0);
        this.getChildren().addAll(location, showImage, weatherStatus, highLow, windSpeed, rainPercentage);
        location.setPadding(insets);
        weatherStatus.setPadding(insets);
        highLow.setPadding(insets);
        windSpeed.setPadding(insets);
        rainPercentage.setPadding(insets);
        this.setAlignment(Pos.CENTER);
    }
}
