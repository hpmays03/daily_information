package hpmays03;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SearchBar extends HBox{
    Label ZipCode;
    TextField searchBar;
    Button search;
    static String result;

    public SearchBar() {
        ZipCode = new Label("ZipCode:");
        searchBar = new TextField();
        search = new Button("Enter");

        this.getChildren().addAll(ZipCode, searchBar, search);
        result = searchBar.getText();
        Runnable loadInfo = () -> {
            Weather.pullLatLong(searchBar.getText());
            Weather.pullWeather(Weather.latitude, Weather.longitude);
        };
        EventHandler<ActionEvent> loadInfoHandler = (event) -> {
            runNow(loadInfo);
        };
        search.setOnAction(loadInfoHandler);
    }

    public static String searchBarInfo() {
        return result;
    }

    private static void runNow(Runnable loadInfo) {
        Thread t = new Thread(loadInfo, "info");
        t.setDaemon(true);
        t.start();
    }
}
