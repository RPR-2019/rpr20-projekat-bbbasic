package controllers;

import dao.KlijentDAO;
import dao.TehnickiPregledDAO;
import dao.TimTehnickiDAO;
import dao.UsersDAO;
import enums.VrstaTehnickogPregleda;
import exceptions.NeispravanTelefonskiBroj;
import exceptions.ZakazanTermin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Customer;
import models.TehnickiPregled;
import models.Vozilo;
import services.UserSession;

import java.time.LocalDate;
import java.util.Arrays;


public class TP2Controller {
    public TehnickiPregledDAO tehnickiPregledDAO;
    public UsersDAO usersDAO;
    public TimTehnickiDAO timTehnickiDAO;
    public Label l2, l3,ltehnicki;
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
        ltehnicki.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        choiceVrstaPregleda.setItems(vrstaTehnickogPregleda);
        choiceVrstaPregleda.setValue(vrstaTehnickogPregleda.get(0));

    }

    public TP2Controller(Vozilo vozilo) {
        tehnickiPregledDAO = new TehnickiPregledDAO();
        usersDAO = new UsersDAO();
        timTehnickiDAO = new TimTehnickiDAO();
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
        Customer klijent= new Customer();
        klijent.setFirst_name(fldIme.getText());
        klijent.setLast_name(fldPrezime.getText());
        klijent.setAddress(fldMjestoPrebivalista.getText());
        klijent.setPhone_number(fldBrojTelefona.getText());

        klijentDAO.dodajKlijenta(klijent);

        //pravimo tehnicki da ga dodamo jer je sve ok
        TehnickiPregled tehnickiPregled = new TehnickiPregled();

        tehnickiPregled.setStatusTehnickogPregleda("Zakazan");
//        //ovdje smo mijenjali
//        tehnickiPregled.setKlijent(klijent);
        tehnickiPregled.setVozilo(vozilo);
        tehnickiPregled.setKlijent(klijent);
        //tehnickiPregled.setVoziloID(vozilo.getId());
        tehnickiPregled.setVrstaTehnickogPregleda(choiceVrstaPregleda.getValue().toString());
        tehnickiPregled.setDatumPregleda(choiceDatum.getValue());
        tehnickiPregled.getUposlenici().add(usersDAO.dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme()));

        try {
            tehnickiPregledDAO.dodajTehnicki(tehnickiPregled);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tehnicki pregled");
            alert.setHeaderText("Uspjesno ste zakazali tehnicki pregled!");
            System.out.println("ID novog tehnickog " + tehnickiPregled.getId());
            System.out.println("ID usera je" + usersDAO.dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme()).getId());
            timTehnickiDAO.spojiTehnickiUposlenik(tehnickiPregled.getId(), usersDAO.dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme()).getId());
            //alert.setContentText("I have a great message for you!");
            alert.showAndWait();
        }catch (ZakazanTermin zakazanTermin) {
            System.out.println(zakazanTermin);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Tehnicki pregled");
            alert.setHeaderText("Vec zakazan pregled!");
            alert.showAndWait();
        }

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
