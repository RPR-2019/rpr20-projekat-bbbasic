package controllers;

import constants.ModelVozila;
import dao.VozilaDAO;
import enums.MarkaVozila;
import exceptions.NeispravanBrojSasije;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import enums.TipVozila;
import models.Vozilo;
import services.VIN;

import java.time.LocalDate;
import java.util.Arrays;

public class VoziloController {
    private VozilaDAO vozilaDAO;
    public Vozilo vozilo;
    public CheckBox choiceBijela, choiceCrna, choiceSmeda, choiceCrvena;

    public ChoiceBox<TipVozila> choiceTipVozila = new ChoiceBox<>();
    public  ObservableList<TipVozila> tipVozila;

    public ChoiceBox<MarkaVozila> choiceMarkaVozila = new ChoiceBox<>();
    public ObservableList<MarkaVozila> markaVozila;

    public ChoiceBox<String> choiceModelVozila = new ChoiceBox<>();
    public ObservableList<String> modelVozila;

    public TextField fldGodinjaProizvodnje, fldRegistracija;

    public TextField fldBrojSasije;

    public CheckBox novoVozilo;
    public ChoiceBox<Vozilo> choiceVozilo;
    public Label fldPogrenaRegistracija, fldPogresnaSasija;



    @FXML
    public void initialize() {
        choiceTipVozila.setItems(tipVozila);
        choiceMarkaVozila.setItems(markaVozila);
        choiceModelVozila.setDisable(true);
        novoVozilo.setSelected(false);
        fldPogrenaRegistracija.setVisible(false);
        fldPogresnaSasija.setVisible(false);


        choiceMarkaVozila.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            modelVozila = FXCollections.observableArrayList(ModelVozila.listaModelaPremaMarki.get(choiceMarkaVozila.getItems().get((Integer) number2)));
            choiceModelVozila.setDisable(false);

            choiceModelVozila.setItems(modelVozila);
        });

    }

    public VoziloController() {
        tipVozila =  FXCollections.observableArrayList(Arrays.asList(TipVozila.values()));
        markaVozila = FXCollections.observableArrayList(Arrays.asList(MarkaVozila.values()));
    }

    public void clickCancel(ActionEvent actionEvent) {
        vozilo = null;
        Stage stage = (Stage) choiceBijela.getScene().getWindow();
        stage.close();
    }


    public void clickDalje(ActionEvent actionEvent){
        boolean sveOk = true;

        //validacija tip
        if (choiceTipVozila.getValue() == null) {
            choiceTipVozila.getStyleClass().removeAll("poljeIspravno");
            choiceTipVozila.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            choiceTipVozila.getStyleClass().removeAll("poljeNijeIspravno");
            choiceTipVozila.getStyleClass().add("poljeIspravno");
        }

        //validacija marka
        if (choiceMarkaVozila.getValue() == null) {
            choiceMarkaVozila.getStyleClass().removeAll("poljeIspravno");
            choiceMarkaVozila.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            choiceMarkaVozila.getStyleClass().removeAll("poljeNijeIspravno");
            choiceMarkaVozila.getStyleClass().add("poljeIspravno");
        }

        //validacija model
        if (choiceModelVozila.getValue() == null) {
            choiceModelVozila.getStyleClass().removeAll("poljeIspravno");
            choiceModelVozila.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            choiceModelVozila.getStyleClass().removeAll("poljeNijeIspravno");
            choiceModelVozila.getStyleClass().add("poljeIspravno");
        }

        //validacija godinaProizvodnje
        if(validacijeGodineProizvodnje(fldGodinjaProizvodnje.getText()) == false) {
            fldGodinjaProizvodnje.getStyleClass().removeAll("poljeIspravno");
            fldGodinjaProizvodnje.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fldGodinjaProizvodnje.getStyleClass().removeAll("poljeNijeIspravno");
            fldGodinjaProizvodnje.getStyleClass().add("poljeIspravno");
        }
        //validacija tablica
        if(validacijaTablica(fldRegistracija.getText()) == false) {
            fldRegistracija.getStyleClass().removeAll("poljeIspravno");
            fldRegistracija.getStyleClass().add("poljeNijeIspravno");
            fldPogrenaRegistracija.setVisible(true);
            sveOk = false;
        } else {
            fldRegistracija.getStyleClass().removeAll("poljeNijeIspravno");
            fldRegistracija.getStyleClass().add("poljeIspravno");
            fldPogrenaRegistracija.setVisible(false);
        }

        //validacija sasije
        try {
            VIN.isVinValid(fldBrojSasije.getText());
            fldBrojSasije.getStyleClass().removeAll("poljeNijeIspravno");
            fldBrojSasije.getStyleClass().add("poljeIspravno");
            fldPogresnaSasija.setVisible(false);
        } catch (NeispravanBrojSasije neispravanBrojSasije) {
            fldBrojSasije.getStyleClass().removeAll("poljeIspravno");
            fldBrojSasije.getStyleClass().add("poljeNijeIspravno");
            fldPogresnaSasija.setVisible(true);
            sveOk = false;
            System.out.println(neispravanBrojSasije.getMessage());
        }


//        if(!sveOk) return;
//        vozilo = new Vozilo();
//        //vozilaDAO.dodajVozilo(vozilo);
//        Stage stage = (Stage) choiceBijela.getScene().getWindow();
//        stage.close();

    }

    public void actionNovoVozilo(ActionEvent actionEvent) {
        if(novoVozilo.isSelected()) {
            choiceVozilo.setDisable(true);
            ///
            choiceTipVozila.setDisable(false);
            choiceMarkaVozila.setDisable(false);
            if(choiceMarkaVozila.getValue() != null)
                choiceModelVozila.setDisable(false);
            fldGodinjaProizvodnje.setDisable(false);
            fldRegistracija.setDisable(false);
            fldBrojSasije.setDisable(false);

        }
        else {
            choiceVozilo.setDisable(false);
            //
            choiceTipVozila.setDisable(true);
            choiceMarkaVozila.setDisable(true);
            choiceModelVozila.setDisable(true);
            fldGodinjaProizvodnje.setDisable(true);
            fldRegistracija.setDisable(true);
            fldBrojSasije.setDisable(true);
        }

    }

    private boolean validacijeGodineProizvodnje(String text) {
        if(text.isEmpty()) return false;
        int godina = Integer.parseInt(text);
        if(godina > LocalDate.now().getYear() || godina < 1908) return false;
        return true;
    }

    private boolean validacijaTablica(String text) {
        if(text.length() != 9) return  false;
        if(text.charAt(3) != '-' || text.charAt(5) != '-') return false;
        if(!Character.isDigit(text.charAt(1))  || !Character.isDigit(text.charAt(2))) return false;
        if(!Character.isDigit(text.charAt(6))  || !Character.isDigit(text.charAt(7)) || !Character.isDigit(text.charAt(7))) return false;
        String string = "AEJKMOT";
        if(string.indexOf(text.charAt(0)) == -1 || string.indexOf(text.charAt(4)) == -1) return false;

        return true;
    }

}
