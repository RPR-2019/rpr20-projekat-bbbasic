package controllers;

import constants.ModelVozila;
import dao.KlijentDAO;
import dao.VozilaDAO;
import enums.MarkaVozila;
import enums.TipVozila;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import models.Klijent;
import models.Vozilo;

import java.util.Arrays;
import java.util.Observable;

public class PretragaController {
    public Label labelaNaslov, labelaInfo;
    public KlijentDAO klijentDAO;
    public VozilaDAO vozilaDAO;
    public ComboBox<TipVozila> choiceTipVozila;
    public ObservableList<TipVozila> tipVozila;
    public ComboBox<Klijent> choiceKlijent;
    public ObservableList<Klijent> klijenti;


    @FXML
    public void initialize() {
        labelaNaslov.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08); -fx-border-width: 5;");
        labelaInfo.setStyle("-fx-border-color: #a6d4fa;");
        choiceTipVozila.setItems(tipVozila);
        choiceKlijent.setItems(klijenti);

    }

    public PretragaController(BorderPane gridPane) {
        klijentDAO = new KlijentDAO();
        vozilaDAO = new VozilaDAO();
        tipVozila = FXCollections.observableArrayList(Arrays.asList(TipVozila.values()));
        klijenti = FXCollections.observableArrayList(klijentDAO.klijenti());
    }
}
