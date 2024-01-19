package hpmays03.src;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewsBlock extends HBox{
    VBox info;
    Image articlePhoto;
    Label articleTitle;
    Label articleDate;
    Label articleURL;
    Label articlePublisher;
    ImageView articleImageShow;
    Button link;

    public NewsBlock(String title, String photo, String date, String publisher, String url) {
        super();
        Insets insets = new Insets(15,0,15,0);
        articleTitle = new Label(title);
        articlePhoto = new Image(photo);
        articleDate = new Label(date);
        articleURL = new Label(url);
        articlePublisher = new Label(publisher);
        info = new VBox();
        link = new Button();
        articleImageShow = new ImageView(articlePhoto);
        articleImageShow.setFitHeight(200);
        articleImageShow.setFitWidth(200);
        this.getChildren().addAll(articleImageShow, info);
        this.setPadding(insets);
        articleTitle.setPadding(insets);
        articleDate.setPadding(insets);
        articleURL.setPadding(insets);
        info.getChildren().addAll(articleTitle, articleDate, articlePublisher, articleURL);
    }

    public void setTitle(String title) {
        articleTitle.setText(title);
    }

    public void setImage(String photo) {
        articlePhoto = new Image(photo);
        articleImageShow.setImage(articlePhoto);
    }

    public void setPublishDate(String date) {
        articleDate.setText(date);
    }

    public void setUrl(String url) {
        articleURL.setText(url);
    }
}
