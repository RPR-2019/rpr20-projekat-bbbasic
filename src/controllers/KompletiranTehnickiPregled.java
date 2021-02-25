package controllers;

import dao.TechnicalInspectionDAO;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.TechnicalInspection;


public class KompletiranTehnickiPregled {
    public TechnicalInspectionDAO technicalInspectionDAO;
    public ImageView imgdimenzije;
    public Label labelaNaslov;
    //odabir
    public ChoiceBox<String> choiceVrstaMotora, vrstaGoriva, vrstaMjenjaca, taktnostMotora;
    //dimenzije vehicles
    public TextField sirinaVozila, visinaVozila, duzinaVozila;
    //mjesta
    public TextField mjestaZaSjesti,mjestaZaStati, mjestaZaLezanje;
    //komentar
    public TextArea komentar;
    //check ispravnost
    public CheckBox checkIspravno, checkNeispravno;
    public Button btnNeispravno, btnIspravno;
    public TextField cijena, masaPraznogVozila;
    public TechnicalInspection technicalInspection;

    public KompletiranTehnickiPregled(TechnicalInspection selectedItem) {
        technicalInspectionDAO = new TechnicalInspectionDAO();
        technicalInspection = selectedItem;
    }

    @FXML
    public void initialize() {
        labelaNaslov.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        imgdimenzije.setImage(new Image("/img/dimenzije.jpg"));
        imgdimenzije.setFitHeight(180);
        imgdimenzije.setFitWidth(230);
        //vrsta motora
        choiceVrstaMotora.getItems().add("Otto");
        choiceVrstaMotora.getItems().add("Diesel");
        choiceVrstaMotora.getItems().add("Kombinovani pogon");
        choiceVrstaMotora.setValue(choiceVrstaMotora.getItems().get(0));

        //vrstaGoriva
        vrstaGoriva.getItems().add("Benzin");
        vrstaGoriva.getItems().add("Diesel");
        vrstaGoriva.setValue(vrstaGoriva.getItems().get(0));

        //vrstaMjenjaca
        vrstaMjenjaca.getItems().add("Automatski");
        vrstaMjenjaca.getItems().add("Ručni");
        vrstaMjenjaca.setValue(vrstaMjenjaca.getItems().get(0));

        //taktnostMotora
        taktnostMotora.getItems().add("Dvotaktni");
        taktnostMotora.getItems().add("Četverotaktni");
        taktnostMotora.setValue(vrstaMjenjaca.getItems().get(0));

        //check
        btnIspravno.setVisible(false);
        btnNeispravno.setVisible(true);
        checkNeispravno.setSelected(true);
        checkIspravno.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    checkNeispravno.setSelected(!new_val);
                    btnIspravno.setVisible(!checkNeispravno.isSelected());
                    btnNeispravno.setVisible(checkNeispravno.isSelected());
                });
        checkNeispravno.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    checkIspravno.setSelected(!new_val);
                    btnIspravno.setVisible(!checkNeispravno.isSelected());
                    btnNeispravno.setVisible(checkNeispravno.isSelected());
        });

    }

    public void clickKompletiraj(ActionEvent actionEvent) {
        boolean sveOK = true;
        System.out.println("Unosimo TP ehehehhhehehe");
        //validacija dimenzija
        if (!sirinaVozila.getText().matches("[0-9]+") || Integer.parseInt(sirinaVozila.getText()) < 1000) {
            sirinaVozila.getStyleClass().removeAll("poljeIspravno");
            sirinaVozila.getStyleClass().add("poljeNijeIspravno");
            sveOK = false;
        }
        else {
            sirinaVozila.getStyleClass().removeAll("poljeNijeIspravno");
            sirinaVozila.getStyleClass().add("poljeIspravno");

        }
        if (!visinaVozila.getText().matches("[0-9]+") || Integer.parseInt(visinaVozila.getText()) < 1000) {
            visinaVozila.getStyleClass().removeAll("poljeIspravno");
            visinaVozila.getStyleClass().add("poljeNijeIspravno");
            sveOK = false;
        }
        else {
            visinaVozila.getStyleClass().removeAll("poljeNijeIspravno");
            visinaVozila.getStyleClass().add("poljeIspravno");
        }
        if (!duzinaVozila.getText().matches("[0-9]+") || Integer.parseInt(duzinaVozila.getText()) < 1000) {
            duzinaVozila.getStyleClass().removeAll("poljeIspravno");
            duzinaVozila.getStyleClass().add("poljeNijeIspravno");
            sveOK = false;
        }
        else {
            duzinaVozila.getStyleClass().removeAll("poljeNijeIspravno");
            duzinaVozila.getStyleClass().add("poljeIspravno");
        }
        //validacija mjesta
        if (!mjestaZaSjesti.getText().matches("[0-9]+") || Integer.parseInt(mjestaZaSjesti.getText()) < 1) {
            mjestaZaSjesti.getStyleClass().removeAll("poljeIspravno");
            mjestaZaSjesti.getStyleClass().add("poljeNijeIspravno");
            sveOK = false;
        }
        else {
            mjestaZaSjesti.getStyleClass().removeAll("poljeNijeIspravno");
            mjestaZaSjesti.getStyleClass().add("poljeIspravno");
        }
        if (!mjestaZaLezanje.getText().matches("[0-9]+") || Integer.parseInt(mjestaZaLezanje.getText()) < 0) {
            mjestaZaLezanje.getStyleClass().removeAll("poljeIspravno");
            mjestaZaLezanje.getStyleClass().add("poljeNijeIspravno");
            sveOK = false;
        }
        else {
            mjestaZaLezanje.getStyleClass().removeAll("poljeNijeIspravno");
            mjestaZaLezanje.getStyleClass().add("poljeIspravno");
        }
        if (!mjestaZaStati.getText().matches("[0-9]+") || Integer.parseInt(mjestaZaStati.getText()) < 1) {
            mjestaZaStati.getStyleClass().removeAll("poljeIspravno");
            mjestaZaStati.getStyleClass().add("poljeNijeIspravno");
            sveOK = false;
        }
        else {
            mjestaZaStati.getStyleClass().removeAll("poljeNijeIspravno");
            mjestaZaStati.getStyleClass().add("poljeIspravno");
        }
        //cijena
        if(cijena.getText().isEmpty() || Double.parseDouble(cijena.getText()) > 500 || Double.parseDouble(cijena.getText()) < 0) {
            cijena.getStyleClass().removeAll("poljeIspravno");
            cijena.getStyleClass().add("poljeNijeIspravno");
            sveOK = false;
        }
        else {
            cijena.getStyleClass().removeAll("poljeNijeIspravno");
            cijena.getStyleClass().add("poljeIspravno");
        }
        if(sveOK == false) return;

        System.out.println("Sve oki doki");
        //mijenjamo
        technicalInspection.setEngineType(choiceVrstaMotora.getValue());
        technicalInspection.setEngineTact(taktnostMotora.getValue());
        technicalInspection.setTypeOfFuel(vrstaGoriva.getValue());
        technicalInspection.setTypeOfGearbox(vrstaMjenjaca.getValue());

        technicalInspection.setHeight(Double.parseDouble(visinaVozila.getText()));
        technicalInspection.setWidth(Double.parseDouble(sirinaVozila.getText()));
        technicalInspection.setLength(Double.parseDouble(duzinaVozila.getText()));

        technicalInspection.setPlacesToSit(Integer.parseInt(mjestaZaSjesti.getText()));
        technicalInspection.setPlacesToStand(Integer.parseInt(mjestaZaStati.getText()));
        technicalInspection.setPlacesToLieDown(Integer.parseInt(mjestaZaLezanje.getText()));

        technicalInspection.setComment(komentar.getText());
        technicalInspection.setValid(checkIspravno.isSelected());
        technicalInspection.setPrice(Double.parseDouble(cijena.getText()));

        technicalInspection.setStatusTehnickogPregleda("Kompletiran");
        technicalInspectionDAO.updateTI(technicalInspection);

    }

}
