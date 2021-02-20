package controllers;

import dao.TehnickiPregledDAO;
import dao.TimTehnickiDAO;
import dao.UsersDAO;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import models.TehnickiPregled;
import models.Uposlenik;
import services.UserSession;

import java.time.LocalDate;
import java.util.ArrayList;


public class TehnickiPregledController {
    public UsersDAO usersDAO;
    public ObservableList<Uposlenik> uposlenici;
    public TehnickiPregledDAO tehnickiPregledDAO;
    public TimTehnickiDAO timTehnickiDAO;
    public Label labela;
    private ObservableList<PieChart.Data> pieChartData;
    public PieChart chart;
    //tabelica
    public TableColumn colDatumPregleda;
    public TableColumn colVozilo;
    public TableColumn colVrstaPregleda;
    public TableColumn colStatusPregleda;
    public TableColumn colUposlenici;
    public TableView<TehnickiPregled> tableView;
    //dugmadi
    public Button btnOtkaziTehnicki;
    public Button btnDodajUposlenika;
    public ChoiceBox<Uposlenik> choiceUposlenik;

    @FXML
    public void initialize() {
        if(UserSession.getPrivileges()) {
            btnDodajUposlenika.setVisible(true);
            choiceUposlenik.setVisible(true);
        }
        else {
            btnDodajUposlenika.setVisible(false);
            choiceUposlenik.setVisible(false);
        }

        choiceUposlenik.setItems(uposlenici);
        labela.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        //tabelica
        colDatumPregleda.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, String>("datumPregleda"));
        colVozilo.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, String>("vozilo"));
        colVrstaPregleda.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, String>("vrstaTehnickogPregleda"));
        colStatusPregleda.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, String>("statusTehnickogPregleda"));
        colUposlenici.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, ArrayList<Uposlenik>>("uposlenici"));

        tableView.setItems(tehnickiPregledDAO.pretraga(null,null,null));
        btnOtkaziTehnicki.setDisable(true);
        btnDodajUposlenika.setDisable(true);
        choiceUposlenik.setDisable(true);
        refresh(chart);
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                //brisanje
                if(tableView.getSelectionModel().getSelectedItem().getStatusTehnickogPregleda() == "Zakazan" ||
                        (tableView.getSelectionModel().getSelectedItem().getDatumPregleda().isEqual(LocalDate.now()))) {
                        btnOtkaziTehnicki.setDisable(false);
                }
                else {
                    btnOtkaziTehnicki.setDisable(true);
                }
                choiceUposlenik.setDisable(false);
            }
        });

        choiceUposlenik.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                btnDodajUposlenika.setDisable(false);
            }
        });


    }
    public TehnickiPregledController() {
        tehnickiPregledDAO = new TehnickiPregledDAO();
        usersDAO = new UsersDAO();
        timTehnickiDAO = new TimTehnickiDAO();
        uposlenici = FXCollections.observableArrayList(usersDAO.uposlenici());
    }

    public void otkaziTehnickiPregled(ActionEvent actionEvent) {
            tehnickiPregledDAO.otkaziTehnickiPregled(tableView.getSelectionModel().getSelectedItem());
            tableView.setItems(tehnickiPregledDAO.pretraga(null,null,null));
            tableView.refresh();
            refresh(chart);


    }

    public void dodajUposlenika(ActionEvent actionEvent) {
        if(tableView.getSelectionModel().getSelectedItem().getUposlenici().contains(choiceUposlenik.getValue())) return;
        timTehnickiDAO.spojiTehnickiUposlenik(tableView.getSelectionModel().getSelectedItem().getId(), choiceUposlenik.getValue().getId());
        tableView.setItems(tehnickiPregledDAO.pretraga(null,null,null));
        tableView.refresh();
    }

    public void refresh(PieChart chart) {
        pieChartData  = FXCollections.observableArrayList(
                new PieChart.Data("Zakazani", timTehnickiDAO.brojZakazanihPregleda(usersDAO.dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme()).getId())),
                new PieChart.Data("Otkazani", timTehnickiDAO.brojOtkazanihPregleda(usersDAO.dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme()).getId())),
                new PieChart.Data("Kompletirani", timTehnickiDAO.brojKompletiranihPregleda(usersDAO.dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme()).getId())));
        chart.setData(pieChartData);
        chart.setLegendVisible(false);
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty()
                        )
                )
        );
    }
}
