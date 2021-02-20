package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class KompletiranTehnickiPregled {
    public ImageView imgdimenzije;
    public Label labelaNaslov;

    @FXML
    public void initialize() {
        labelaNaslov.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        imgdimenzije.setImage(new Image("/img/dimenzije.jpg"));
        imgdimenzije.setFitHeight(180);
        imgdimenzije.setFitWidth(230);

    }

}
