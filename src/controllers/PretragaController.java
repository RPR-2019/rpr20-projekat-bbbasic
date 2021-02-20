package controllers;

import dao.KlijentDAO;
import dao.TehnickiPregledDAO;
import dao.TimTehnickiDAO;
import dao.VozilaDAO;
import enums.TipVozila;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import models.Klijent;

import javafx.event.ActionEvent;
import models.TehnickiPregled;
import models.Uposlenik;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class PretragaController {
    public Label labelaNaslov, labelaInfo;
    public KlijentDAO klijentDAO;
    public VozilaDAO vozilaDAO;
    public TehnickiPregledDAO tehnickiPregledDAO;
    public ComboBox<TipVozila> choiceTipVozila;
    public ObservableList<TipVozila> tipVozila;
    public ComboBox<Klijent> choiceKlijent;
    public ObservableList<Klijent> klijenti;
    //datumi
    public DatePicker choiceDatum;
    public TableColumn colDatumPregleda;
    public TableColumn colKlijent;
    public TableColumn colVozilo;
    public TableColumn colStatusPregleda;
    public TableColumn colUposlenici;

    public TableView<TehnickiPregled> tableView;


    @FXML
    public void initialize() {
        labelaNaslov.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08); -fx-border-width: 5;");
        labelaInfo.setStyle("-fx-border-color: #a6d4fa;");
        choiceTipVozila.setItems(tipVozila);
        choiceKlijent.setItems(klijenti);

        colDatumPregleda.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, String>("datumPregleda"));
        colVozilo.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, String>("vozilo"));
        colKlijent.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, String>("klijent"));
        colStatusPregleda.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, String>("statusTehnickogPregleda"));
        colUposlenici.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, ArrayList<Uposlenik>>("uposlenici"));
        tableView.setItems(tehnickiPregledDAO.pretraga(null, null, null));

    }

    public PretragaController(BorderPane gridPane) {
        klijentDAO = new KlijentDAO();
        vozilaDAO = new VozilaDAO();
        tehnickiPregledDAO = new TehnickiPregledDAO();
        tipVozila = FXCollections.observableArrayList(Arrays.asList(TipVozila.values()));
        klijenti = FXCollections.observableArrayList(klijentDAO.klijenti());
    }

    public void clickTrazi(ActionEvent actionEvent) {
        System.out.println("Trazimo");
        tableView.setItems(tehnickiPregledDAO.pretraga(choiceKlijent.getValue(), choiceTipVozila.getValue(), choiceDatum.getValue()));
        tableView.refresh();

    }

}
