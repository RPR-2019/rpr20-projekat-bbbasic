package controllers;

import dao.KlijentDAO;
import dao.VozilaDAO;
import enums.TipVozila;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import models.Klijent;

import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.util.Arrays;

public class PretragaController {
    public Label labelaNaslov, labelaInfo;
    public KlijentDAO klijentDAO;
    public VozilaDAO vozilaDAO;
    public ComboBox<TipVozila> choiceTipVozila;
    public ObservableList<TipVozila> tipVozila;
    public ComboBox<Klijent> choiceKlijent;
    public ObservableList<Klijent> klijenti;
    //datumi
    public DatePicker choiceDatumOd;
    public DatePicker choiceDatumDo;
    public Label greskaUDatumima;


    @FXML
    public void initialize() {
        labelaNaslov.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08); -fx-border-width: 5;");
        labelaInfo.setStyle("-fx-border-color: #a6d4fa;");
        choiceTipVozila.setItems(tipVozila);
        choiceKlijent.setItems(klijenti);
        greskaUDatumima.setVisible(false);

    }

    public PretragaController(BorderPane gridPane) {
        klijentDAO = new KlijentDAO();
        vozilaDAO = new VozilaDAO();
        tipVozila = FXCollections.observableArrayList(Arrays.asList(TipVozila.values()));
        klijenti = FXCollections.observableArrayList(klijentDAO.klijenti());
    }

    public void clickTrazi(ActionEvent actionEvent) {
        if(validacijaDatuma(choiceDatumOd.getValue(), choiceDatumDo.getValue()) == false) {
            greskaUDatumima.setVisible(true);
            choiceDatumDo.getStyleClass().removeAll("poljeIspravno");
            choiceDatumDo.getStyleClass().add("poljeNijeIspravno");

            choiceDatumOd.getStyleClass().removeAll("poljeIspravno");
            choiceDatumOd.getStyleClass().add("poljeNijeIspravno");
        }
        else {
            System.out.println("Trazimo....");
            greskaUDatumima.setVisible(false);
            choiceDatumDo.getStyleClass().removeAll("poljeNijeIspravno");
            choiceDatumDo.getStyleClass().add("poljeIspravno");

            choiceDatumOd.getStyleClass().removeAll("poljeNijeIspravno");
            choiceDatumOd.getStyleClass().add("poljeIspravno");
        }
    }

    private boolean validacijaDatuma(LocalDate datumod, LocalDate datumdo) {
        if(datumdo == null && datumod == null) return true;
        if(datumod == null && datumdo != null) return false;
        if(datumdo == null && datumod != null) return false;
        if(datumod.isAfter(datumdo) || datumdo.isBefore(datumod)) return false;
        return true;
    }
}
