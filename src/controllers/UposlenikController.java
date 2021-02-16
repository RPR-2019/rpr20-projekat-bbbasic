package controllers;

import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.TehnickiPregled;
import models.Uposlenik;

import java.util.ArrayList;

public class UposlenikController {
    private UsersDAO usersDAO;
    private Uposlenik uposlenik;
    public ObservableList<TehnickiPregled> listTehnickiPregledi;
    public ChoiceBox<TehnickiPregled> choiceTehnicki;

    public TextField fldIme;
    public TextField fldPrezime;
    public PasswordField fldLozinka;
    public TextField fldKorisnickoIme;
    public Label zauzetoKorisnickoIme;

    public UposlenikController(Uposlenik uposlenik, ArrayList<Uposlenik> uposlenici) {
        usersDAO = new UsersDAO();
        this.uposlenik = uposlenik;
        //, ArrayList<TehnickiPregled> tehnickiPregledi
        //listTehnickiPregledi = FXCollections.observableArrayList(tehnickiPregledi);
    }

    @FXML
    public void initialize() {
        //choiceTehnicki.setItems(listTehnickiPregledi);
        zauzetoKorisnickoIme.setVisible(false);

        if(uposlenik != null) {
            //postavimo sve ukoliko mijenjamo grad hehe
        }

    }

    public Uposlenik getUposlenik() { return uposlenik; }

    public void clickCancel(ActionEvent actionEvent) {
        uposlenik = null;
        Stage stage = (Stage) fldIme.getScene().getWindow();
        stage.close();
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean sveOk = true;

        //validacija ime
        if (fldIme.getText().trim().isEmpty()) {
            fldIme.getStyleClass().removeAll("poljeIspravno");
            fldIme.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fldIme.getStyleClass().removeAll("poljeNijeIspravno");
            fldIme.getStyleClass().add("poljeIspravno");
        }
        //validacija prezime
        if (fldPrezime.getText().trim().isEmpty()) {
            fldPrezime.getStyleClass().removeAll("poljeIspravno");
            fldPrezime.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
            fldPrezime.getStyleClass().add("poljeIspravno");
        }
        //validacija lozinka
        if (fldLozinka.getText().trim().isEmpty()) {
            fldLozinka.getStyleClass().removeAll("poljeIspravno");
            fldLozinka.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fldLozinka.getStyleClass().removeAll("poljeNijeIspravno");
            fldLozinka.getStyleClass().add("poljeIspravno");
        }

        //valiadcija koricnickoIme
        if(usersDAO.zauzetoKorisnickoIme(fldKorisnickoIme.getText()) == false || fldKorisnickoIme.getText().isEmpty()) {
            zauzetoKorisnickoIme.setVisible(true);
            fldKorisnickoIme.getStyleClass().removeAll("poljeIspravno");
            fldKorisnickoIme.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        }


        if (!sveOk) return;

        if (uposlenik == null) uposlenik = new Uposlenik();
        uposlenik.setIme(fldIme.getText());
        uposlenik.setPrezime(fldPrezime.getText());
        uposlenik.setLozinka(fldLozinka.getText());
        uposlenik.setKorisnickoIme(fldKorisnickoIme.getText());

        //uposlenik.getLetovi().addAll(pomocni);
        Stage stage = (Stage) fldIme.getScene().getWindow();
        stage.close();
    }
}
