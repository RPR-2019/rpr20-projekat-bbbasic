package controllers;

import constants.ModelVozila;
import dao.KlijentDAO;

import dao.TehnickiPregledDAO;
import enums.MarkaVozila;
import enums.VrstaTehnickogPregleda;
import exceptions.NeispravanTelefonskiBroj;
import exceptions.NeispravnaTablica;
import exceptions.ZakazanTermin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Klijent;
import models.TehnickiPregled;
import models.Vozilo;
import javafx.event.ActionEvent;
import services.UserSession;

import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;


public class TP2Controller {
    public TehnickiPregledDAO tehnickiPregledDAO;
    public Label l2, l3;
    public TextField fldIme, fldPrezime, fldMjestoPrebivalista, fldBrojTelefona;
    public KlijentDAO klijentDAO;
    public Label fldPogresanBroj;
    public Vozilo vozilo;

    public ChoiceBox<VrstaTehnickogPregleda> choiceVrstaPregleda;
    public ObservableList<VrstaTehnickogPregleda> vrstaTehnickogPregleda;

    public DatePicker choiceDatum;

    @FXML
    public void initialize() {
        fldPogresanBroj.setVisible(false);
        l2.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        l3.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        choiceVrstaPregleda.setItems(vrstaTehnickogPregleda);
        choiceVrstaPregleda.setValue(vrstaTehnickogPregleda.get(0));

    }

    public TP2Controller(Vozilo vozilo) {
        tehnickiPregledDAO = new TehnickiPregledDAO();
        this.vozilo = vozilo;
        klijentDAO = new KlijentDAO();
        vrstaTehnickogPregleda = FXCollections.observableArrayList(Arrays.asList(VrstaTehnickogPregleda.values()));

    }

    public void onOkClick(ActionEvent actionEvent) {
        //stanje zakazan
        //auto je rijesen, dodan ako je trebalo
        //trebamo sada dodati osobu u bazu ako nije dodana
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

        //validacija telefona
        try {
            validacijaTelefona(fldBrojTelefona.getText());
            fldBrojTelefona.getStyleClass().removeAll("poljeNijeIspravno");
            fldBrojTelefona.getStyleClass().add("poljeIspravno");
            fldPogresanBroj.setVisible(false);

        } catch (NeispravanTelefonskiBroj neispravanTelefonskiBroj) {
            fldBrojTelefona.getStyleClass().removeAll("poljeIspravno");
            fldBrojTelefona.getStyleClass().add("poljeNijeIspravno");
            fldPogresanBroj.setVisible(true);
            sveOk = false;
            System.out.println(neispravanTelefonskiBroj.getMessage());
        }

        //validacija prebivalista
        if (fldMjestoPrebivalista.getText().trim().isEmpty()) {
            fldMjestoPrebivalista.getStyleClass().removeAll("poljeIspravno");
            fldMjestoPrebivalista.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fldMjestoPrebivalista.getStyleClass().removeAll("poljeNijeIspravno");
            fldMjestoPrebivalista.getStyleClass().add("poljeIspravno");
        }

        //validacija datuma
        if (choiceDatum.getValue() == null || choiceDatum.getValue().isBefore(LocalDate.now())) {
            choiceDatum.getStyleClass().removeAll("poljeIspravno");
            choiceDatum.getStyleClass().add("poljeNijeIspravno");
            choiceVrstaPregleda.getStyleClass().add("poljeIspravno");
            sveOk = false;
        } else {
            choiceDatum.getStyleClass().removeAll("poljeNijeIspravno");
            choiceDatum.getStyleClass().add("poljeIspravno");
            choiceVrstaPregleda.getStyleClass().add("poljeIspravno");
        }



        if(sveOk != true) return;
        Klijent klijent= new Klijent();
        klijent.setIme(fldIme.getText());
        klijent.setPrezime(fldPrezime.getText());
        klijent.setMjestoPrebivalista(fldMjestoPrebivalista.getText());
        klijent.setBrojTelefona(fldBrojTelefona.getText());

        klijentDAO.dodajKlijenta(klijent);

        //pravimo tehnicki da ga dodamo jer je sve ok
        TehnickiPregled tehnickiPregled = new TehnickiPregled();

        tehnickiPregled.setStatusTehnickogPregleda("Zakazan");
        tehnickiPregled.setKlijentID(klijent.getId());
        tehnickiPregled.setVoziloID(vozilo.getId());
        tehnickiPregled.setVrstaTehnickogPregleda(choiceVrstaPregleda.getValue().toString());
        tehnickiPregled.setDatumPregleda(choiceDatum.getValue());

        try {
            tehnickiPregledDAO.dodajTehnicki(tehnickiPregled);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tehnicki pregled");
            alert.setHeaderText("Uspjesno ste zakazali tehnicki pregled!");
            tehnickiPregledDAO.spojiTehnickiUposlenik(tehnickiPregled.getId(), UserSession.getID());
            //alert.setContentText("I have a great message for you!");
            alert.showAndWait();
        }catch (ZakazanTermin zakazanTermin) {
            System.out.println(zakazanTermin);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Tehnicki pregled");
            alert.setHeaderText("Vec zakazan pregled!");
            alert.showAndWait();
        }

//        nemoj zaboraviti dodat ovog zaposlenog u tim tehnickog pregleda
//        System.out.println("Trebamo zakazati tehnicki pregled!");
//        System.out.println("A auto je " + vozilo.toString());

        //mozda da se vrati na pocetnu nez
    }

    private boolean validacijaTelefona(String fldBrojTelefona) throws NeispravanTelefonskiBroj {
        if(fldBrojTelefona.length() != 11) throw new NeispravanTelefonskiBroj("Duzina telefonskob broja treba biti 11");
        if(fldBrojTelefona.charAt(3) != '-' || fldBrojTelefona.charAt(7) != '-') throw new NeispravanTelefonskiBroj("Neispravan format telefonskog broja");
        for(int i = 0; i < fldBrojTelefona.length(); i++) {
            if(i == 3 || i == 7) continue;
            if(!Character.isDigit(fldBrojTelefona.charAt(i))) throw new NeispravanTelefonskiBroj("Neispravan format telefonskog broja");
        }
        return true;
    }
}
