package controllers;

import dao.UsersDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import models.Uposlenik;



import java.io.IOException;

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
}
