package controllers;

import dao.UsersDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Uposlenik;



import java.io.IOException;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdministratorGlavniController {
    public ListView listaUposlenici;
    private ObservableList<Uposlenik> lista;
    private UsersDAO usersDAO;

    public AdministratorGlavniController() {
        usersDAO = new UsersDAO();
        lista = FXCollections.observableArrayList(usersDAO.uposlenici());

    }

    @FXML
    public void initialize() {
        listaUposlenici.setItems(lista);

    }
    public void dodajUposlenika(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uposlenik.fxml"));
            //trece ce biti usersDAO.tehnickipregledi()
            UposlenikController uposlenikController = new UposlenikController(null);
            loader.setController(uposlenikController);
            root = loader.load();
            stage.setTitle("Uposlenik");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.getIcons().add(new Image("/img/icon.jpg"));
            stage.show();

            stage.setOnHiding( event -> {
                Uposlenik uposlenik = uposlenikController.getUposlenik();
                if (uposlenik != null) {
                    usersDAO.dodajUposlenog(uposlenik);
                    lista.setAll(usersDAO.uposlenici());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionIzmijeniUposlenika(ActionEvent actionEvent) {
        Uposlenik uposlenik = (Uposlenik) listaUposlenici.getSelectionModel().getSelectedItem();
        if (uposlenik == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uposlenik.fxml"));
            UposlenikController uposlenikController = new UposlenikController(uposlenik);
            loader.setController(uposlenikController);
            root = loader.load();
            stage.setTitle("Uposlenik");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.getIcons().add(new Image("/img/icon.jpg"));
            stage.show();

            stage.setOnHiding( event -> {
                Uposlenik uposlenik1 = uposlenikController.getUposlenik();
                if (uposlenik1 != null) {
                    // Ovdje ne smije doći do izuzetka, jer se prozor neće zatvoriti
                    try {
                        usersDAO.izmijeniUposlenog(uposlenik1);
                        lista.setAll(usersDAO.uposlenici());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionObrisiUposlenika(ActionEvent actionEvent) {
        Uposlenik uposlenik = (Uposlenik) listaUposlenici.getSelectionModel().getSelectedItem();
        if (uposlenik == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja Uposlenika");
        alert.setHeaderText("Brisanje uposlenika " + uposlenik.getIme() + " " + uposlenik.getPrezime());
        alert.setContentText("Da li ste sigurni da zelite obrisati uposlenog?");
        Stage stage = (Stage )alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/img/icon.jpg"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            usersDAO.obrisiUposlenog(uposlenik);
            lista.setAll(usersDAO.uposlenici());
        }
    }

    public void actiondodajVozilo(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vozilo.fxml"));
            VoziloController voziloController = new VoziloController();
            loader.setController(voziloController);
            root = loader.load();
            stage.setTitle("Vozilo");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.getIcons().add(new Image("/img/icon.jpg"));
            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}