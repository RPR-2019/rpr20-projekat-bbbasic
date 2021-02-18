package controllers;

import dao.KlijentDAO;

import exceptions.NeispravanTelefonskiBroj;
import exceptions.NeispravnaTablica;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Klijent;
import models.Vozilo;
import javafx.event.ActionEvent;


public class TP2Controller {
    public Label l2, l3;
    public TextField fldIme, fldPrezime, fldMjestoPrebivalista, fldBrojTelefona;
    public KlijentDAO klijentDAO;
    public Label fldPogresanBroj;
    public Vozilo vozilo;

    @FXML
    public void initialize() {
        fldPogresanBroj.setVisible(false);
        l2.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        l3.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
    }

    public TP2Controller(Vozilo vozilo) {
        this.vozilo = vozilo;
        klijentDAO = new KlijentDAO();
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

        if(sveOk != true) return;
        Klijent klijent= new Klijent();
        klijent.setIme(fldIme.getText());
        klijent.setPrezime(fldPrezime.getText());
        klijent.setMjestoPrebivalista(fldMjestoPrebivalista.getText());
        klijent.setBrojTelefona(fldBrojTelefona.getText());

        klijentDAO.dodajKlijenta(klijent);
        System.out.println("Trebamo zakazati tehnicki pregled!");
        System.out.println("A auto je " + vozilo.toString());
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
