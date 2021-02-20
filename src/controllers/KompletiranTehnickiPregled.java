package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.File;

public class KompletiranTehnickiPregled {
    public ImageView imgdimenzije;

    @FXML
    public void initialize() {
        imgdimenzije.setImage(new Image("/img/dimenzije.jpg"));
        imgdimenzije.setFitHeight(160);
        imgdimenzije.setFitWidth(206);

    }

}
