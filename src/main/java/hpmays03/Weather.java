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
    static String high;
    static String low;
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
    private static final String ZIPCODE_API = "https://geocoding-api.open-meteo.com/v1/search";
    public static String pullLatLong(String location) {
        try {
            String count = "10";
            String language = "en";
            String format = "json";
            String query = String.format("?name=%s&count=%s&language=%s&format=%s", location, count, language, format);
            String uri = ZIPCODE_API + query;
            URI resource = URI.create(uri);
            System.out.println(uri);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(resource).build();
            BodyHandler<String> bodyHandler = BodyHandlers.ofString();
            HttpResponse<String> response = HTTP_CLIENT.<String>send(request, bodyHandler);
            int status = response.statusCode();
            if (status != 200) {
                throw new IOException("HTTP " + status);
            }
            String body = response.body();
            LocationResponse m = GSON.<LocationResponse>fromJson(body, LocationResponse.class);
            if (m.results == null) {
                Platform.runLater(() -> {
                    a.setAlertType(AlertType.ERROR);
                    a.setContentText("URI: " + resource + "\n" + "The zip-code you have entered does not exist.");
                    a.show();
            });
            }
            ZipCode output = m.results[0];
            city = output.name;
            latitude = output.latitude;
            longitude = output.longitude;
            state = output.admin1;
            return city;
        } catch (IOException | InterruptedException cause) {
            System.err.println(cause);
            cause.printStackTrace();
            return "inside catch";
        }
    }

    private static final String WEATHER_API = "https://api.open-meteo.com/v1/forecast?";
    public static String pullWeather(String latitude, String longitude) {
        try{
        String daily = "temperature_2m_max,temperature_2m_min";
        String temperature = "fahrenheit";
        String windUnit = "mph";
        String rainUnit = "inch";
        String days = "1";
        String query = String.format("latitude=%s&longitude=%s&daily=%s&temperature_unit=%s&wind_speed_unit=%s&precipitation_unit=%s&forecast_days=%s", latitude, longitude, daily, temperature, windUnit, rainUnit, days);
        String uri = WEATHER_API + query;
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
            System.out.println(body);
            return body;
        } catch (IOException | InterruptedException cause) {
            System.err.println(cause);
            cause.printStackTrace();
            return null;
        }
    }
    public static String pullInfo(String searchTerm, String daily) {
        String body = pullWeather(latitude, longitude);
        daily = daily + "\":";
        int parse = body.indexOf(daily);
        body = body.substring(parse);
        parse = body.indexOf(searchTerm);
        body = body.substring(parse, parse + 25);
        parse = body.indexOf("[");
        body = body.substring(parse + 1);
        return body;
    }
}
