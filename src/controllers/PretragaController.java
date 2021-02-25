package controllers;

import dao.KlijentDAO;
import dao.TehnickiPregledDAO;
import dao.VozilaDAO;
import enums.TipVozila;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import models.Customer;

import javafx.event.ActionEvent;
import models.Employee;
import models.TechnicalInspection;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class PretragaController {
    public Label labelaNaslov, labelaInfo;
    public KlijentDAO klijentDAO;
    public VozilaDAO vozilaDAO;
    public TehnickiPregledDAO tehnickiPregledDAO;
    public ComboBox<TipVozila> choiceTipVozila;
    public ObservableList<TipVozila> tipVozila;
    public ComboBox<Customer> choiceKlijent;
    public ObservableList<Customer> klijenti;
    public Button btnSave;
    //datumi
    public DatePicker choiceDatum;
    public TableColumn colDatumPregleda;
    public TableColumn colKlijent;
    public TableColumn colVozilo;
    public TableColumn colStatusPregleda;
    public TableColumn colUposlenici;

    public TableView<TechnicalInspection> tableView;


    @FXML
    public void initialize() {
        labelaNaslov.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08); -fx-border-width: 5;");
        labelaInfo.setStyle("-fx-border-color: #a6d4fa;");
        choiceTipVozila.setItems(tipVozila);
        choiceKlijent.setItems(klijenti);

        colDatumPregleda.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("datumPregleda"));
        colVozilo.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("vehicle"));
        colKlijent.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("klijent"));
        colStatusPregleda.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("statusTehnickogPregleda"));
        colUposlenici.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, ArrayList<Employee>>("uposlenici"));
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
        tableView.setItems(tehnickiPregledDAO.pretraga(choiceKlijent.getValue(), choiceTipVozila.getValue(), choiceDatum.getValue()));
        tableView.refresh();
        if(tableView.getItems().isEmpty())
            btnSave.setDisable(true);
        else
            btnSave.setDisable(false);

    }
    public void clickSave(ActionEvent actionEvent) {
        FileChooser izbornik  = new FileChooser();
        izbornik.setTitle("Izaberite datoteku");
        izbornik.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tekstualna", "*.txt"));
        File izabrani = izbornik.showOpenDialog(labelaNaslov.getScene().getWindow());
        tehnickiPregledDAO.zapisiDatoteku(izabrani, tableView.getItems());
    }

}
