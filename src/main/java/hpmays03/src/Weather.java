package hpmays03.src;

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
    static ImageView imageShow;
    static Label location;
    static Label weatherStatus;
    static Label highLow;
    static Label windSpeed;
    static Label rainPercentage;
    boolean pressed;
    static String city;
    static String longitude;
    static String latitude;
    static String state;
    private static Alert a = new Alert(AlertType.NONE);
    public Weather() {
        super();
        image = new Image("file:resources/default.png");
        imageShow = new ImageView(image);
        imageShow.setFitHeight(200);
        imageShow.setFitWidth(200);
        location =  new Label("None shown since no ZipCode entered");
        weatherStatus = new Label("N/A");
        highLow = new Label("N/A");
        windSpeed = new Label("N/A");
        rainPercentage = new Label("N/A");
        Insets insets = new Insets(10, 0, 10, 0);
        this.getChildren().addAll(location, imageShow, weatherStatus, highLow, windSpeed, rainPercentage);
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

    private static final String GRID_API = "https://api.weather.gov/points/";
    public static String pullGrid() {
        try {
            String uri = GRID_API + latitude + "," + longitude;
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
            int parse = body.indexOf("gridY");
            String properties = body.substring(parse);
            parse = properties.indexOf("forecast");
            properties = properties.substring(parse);
            int linkEnd = properties.indexOf("\",");
            properties = properties.substring(12, linkEnd);
            return properties;
        } catch (IOException | InterruptedException cause) {
            System.err.println(cause);
            cause.printStackTrace();
            return "error";
        }
    }

    public static String pullData(String searchTerm, String time) {
        try {
            String uri = pullGrid();
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
            int parse = body.indexOf(time);
            body = body.substring(parse);
            parse = body.indexOf(searchTerm);
            body = body.substring(parse);
            parse = body.indexOf(": ");
            if (searchTerm == "icon") {
                int stringEnd = body.indexOf("\",");
                body = body.substring(parse + 3, stringEnd);
                return body;
            } else {
            int stringEnd = body.indexOf(",");
            body = body.substring(parse + 2, stringEnd);
            if (body.contains("\"")) {
                body = body.substring(1);
                body = body.substring(0, body.length() - 1);
            }
            if (searchTerm.equals("value")) {
                body = body.substring(0, 4);
            }
            return body;
            }
        } catch (IOException | InterruptedException cause) {
            System.err.println(cause);
            cause.printStackTrace();
            return "error";
        }
    }

    public static String pullDate() {
        try {
            String uri = pullGrid();
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
            int parse = body.indexOf("updated");
            body = body.substring(parse);
            body = body.substring(11, 21);
            System.out.println(body);
            return body;
        } catch (IOException | InterruptedException cause) {
            System.err.println(cause);
            cause.printStackTrace();
            return "error";
        }
    }

    public static void pullWeather() {
        String high = pullData("temperature", "number\": 1");
        String status = pullData("shortForecast", "number\": 1");
        String wind = pullData("windSpeed", "number\": 1");
        String windDirection = pullData("windDirection", "number\": 1");
        String rain = pullData("value", "number\": 1");
        String low = pullData("temperature", "number\": 2");
        String icon = pullData("icon", "number\": 1");
        System.out.println(icon);
        Platform.runLater(() -> {
            location.setText(city);
            weatherStatus.setText(status);
            highLow.setText(high + "/" + low);
            windSpeed.setText(wind + " " + windDirection);
            if (rain.equals("null")) {
                rainPercentage.setText("0%");
            } else {
                rainPercentage.setText(rain + "%");
            }
            Image updateImage = new Image(icon);
            imageShow.setImage(updateImage);
        });
    }
}
