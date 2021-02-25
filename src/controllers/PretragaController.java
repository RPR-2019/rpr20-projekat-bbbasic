package controllers;

import dao.CustomerDAO;
import dao.TechnicalInspectionDAO;
import dao.VehicleDAO;
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
    public CustomerDAO customerDAO;
    public VehicleDAO vehicleDAO;
    public TechnicalInspectionDAO technicalInspectionDAO;
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

        colDatumPregleda.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("dateOfInspection"));
        colVozilo.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("vehicle"));
        colKlijent.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("customer"));
        colStatusPregleda.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, String>("statusOfTechnicalInspection"));
        colUposlenici.setCellValueFactory(new PropertyValueFactory<TechnicalInspection, ArrayList<Employee>>("employees"));
        tableView.setItems(technicalInspectionDAO.search(null, null, null));

    }

    public PretragaController(BorderPane gridPane) {
        customerDAO = new CustomerDAO();
        vehicleDAO = new VehicleDAO();
        technicalInspectionDAO = new TechnicalInspectionDAO();
        tipVozila = FXCollections.observableArrayList(Arrays.asList(TipVozila.values()));
        klijenti = FXCollections.observableArrayList(customerDAO.customers());
    }

    public void clickTrazi(ActionEvent actionEvent) {
        tableView.setItems(technicalInspectionDAO.search(choiceKlijent.getValue(), choiceTipVozila.getValue(), choiceDatum.getValue()));
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
        technicalInspectionDAO.recordFile(izabrani, tableView.getItems());
    }

}
