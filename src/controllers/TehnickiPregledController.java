package controllers;

import dao.TehnickiPregledDAO;
import dao.TimTehnickiDAO;
import dao.UsersDAO;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.TehnickiPregled;
import models.Uposlenik;
import services.UserSession;

import java.util.ArrayList;
import java.util.Optional;


public class TehnickiPregledController {
    public UsersDAO usersDAO;
    public TehnickiPregledDAO tehnickiPregledDAO;
    public TimTehnickiDAO timTehnickiDAO;
    public Label labela;
    ObservableList<PieChart.Data> pieChartData;
    public PieChart chart;
    //tabelica
    public TableColumn colDatumPregleda;
    public TableColumn colVozilo;
    public TableColumn colVrstaPregleda;
    public TableColumn colStatusPregleda;
    public TableColumn colUposlenici;
    public TableView<TehnickiPregled> tableView;

    @FXML
    public void initialize() {
        labela.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        //brojTehnickih.setText("Ukupno Tehnickih pregleda: " + String.valueOf(tehnickiPregledDAO.brojTehnickihZaKorisnika(usersDAO.dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme()).getId())));
        chart.setData(pieChartData);
        chart.setLabelLineLength(20);
        chart.setLegendVisible(false);
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty()
                        )
                )
        );
        //tabelica
        colDatumPregleda.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, String>(""));
        colVozilo.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, String>(""));
        colVrstaPregleda.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, Integer>(""));
        colStatusPregleda.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, String>(""));
        colUposlenici.setCellValueFactory(new PropertyValueFactory<TehnickiPregled, ArrayList<Uposlenik>>(""));

        tableView.setItems(timTehnickiDAO.sviTehnicki());


    }
    public TehnickiPregledController() {
        tehnickiPregledDAO = new TehnickiPregledDAO();
        usersDAO = new UsersDAO();
        timTehnickiDAO = new TimTehnickiDAO();
        //inicijalizacija ove liste
        System.out.println("Zakazani" + timTehnickiDAO.brojZakazanihPregleda(usersDAO.dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme()).getId()));
        System.out.println("Otkazani" + timTehnickiDAO.brojOtkazanihPregleda(usersDAO.dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme()).getId()));
        System.out.println("Kompletirani" + timTehnickiDAO.brojKompletiranihPregleda(usersDAO.dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme()).getId()));


        pieChartData  = FXCollections.observableArrayList(
                        new PieChart.Data("Zakazani", timTehnickiDAO.brojZakazanihPregleda(usersDAO.dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme()).getId())),
                        new PieChart.Data("Otkazani", timTehnickiDAO.brojOtkazanihPregleda(usersDAO.dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme()).getId())),
                        new PieChart.Data("Kompletirani", timTehnickiDAO.brojKompletiranihPregleda(usersDAO.dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme()).getId())));
    }

}
