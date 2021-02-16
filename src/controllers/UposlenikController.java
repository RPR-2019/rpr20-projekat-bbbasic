package controllers;

import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.TehnickiPregled;
import models.Uposlenik;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


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
    public DatePicker fldDatumRodjenja;
    public  DatePicker fldDatumZaposlenja;

    public UposlenikController(Uposlenik uposlenik) {
        usersDAO = new UsersDAO();
        this.uposlenik = uposlenik;
    }

    @FXML
    public void initialize() {
        zauzetoKorisnickoIme.setVisible(false);

        if(uposlenik != null) {
            fldIme.setText(uposlenik.getIme());
            fldPrezime.setText(uposlenik.getPrezime());
            fldLozinka.setText(uposlenik.getLozinka());
            fldKorisnickoIme.setText(uposlenik.getKorisnickoIme());
            fldDatumRodjenja.setValue(uposlenik.getDatumRodjenja());
            fldDatumZaposlenja.setValue(uposlenik.getDatumZaposlenja());
        }

    }

    public Uposlenik getUposlenik() { return uposlenik; }

    public void clickCancel(ActionEvent actionEvent) {
        uposlenik = null;
        Stage stage = (Stage) fldIme.getScene().getWindow();
        stage.close();
    }

    public void clickOk(ActionEvent actionEvent) throws ParseException {
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
        if(usersDAO.zauzetoKorisnickoIme(fldKorisnickoIme.getText(), uposlenik) == false || fldKorisnickoIme.getText().isEmpty()) {
            System.out.println("Nesta nije ok");
            if(usersDAO.zauzetoKorisnickoIme(fldKorisnickoIme.getText(), uposlenik) == false)
                zauzetoKorisnickoIme.setVisible(true);
            fldKorisnickoIme.getStyleClass().removeAll("poljeIspravno");
            fldKorisnickoIme.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        }
        else {
            fldKorisnickoIme.getStyleClass().removeAll("poljeNijeIspravno");
            fldKorisnickoIme.getStyleClass().add("poljeIspravno");
        }
        //validacija ako uposlenik nije null korisnickoIme


        //validacija datumrodjenja
        if(fldDatumRodjenja.getValue() == null || Period.between(fldDatumRodjenja.getValue(), LocalDate.now()).getYears() < 18) {
            fldDatumRodjenja.getStyleClass().removeAll("poljeIspravno");
            fldDatumRodjenja.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        }
        else {
            fldDatumRodjenja.getStyleClass().removeAll("poljeNijeIspravno");
            fldDatumRodjenja.getStyleClass().add("poljeIspravno");
        }

        //validacija datumzaposlenja
        if(fldDatumZaposlenja.getValue() == null || fldDatumZaposlenja.getValue().isAfter(LocalDate.now())) {
            fldDatumZaposlenja.getStyleClass().removeAll("poljeIspravno");
            fldDatumZaposlenja.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        }
        else {
            fldDatumZaposlenja.getStyleClass().removeAll("poljeNijeIspravno");
            fldDatumZaposlenja.getStyleClass().add("poljeIspravno");
        }


        if (!sveOk) return;

        if (uposlenik == null) uposlenik = new Uposlenik();
        uposlenik.setIme(fldIme.getText());
        uposlenik.setPrezime(fldPrezime.getText());
        uposlenik.setLozinka(fldLozinka.getText());
        uposlenik.setKorisnickoIme(fldKorisnickoIme.getText());


        uposlenik.setDatumRodjenja(fldDatumRodjenja.getValue());
        uposlenik.setDatumZaposlenja(fldDatumZaposlenja.getValue());

        Stage stage = (Stage) fldIme.getScene().getWindow();
        stage.close();
    }
}
