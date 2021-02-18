package controllers;

import constants.ModelVozila;
import dao.VozilaDAO;
import enums.MarkaVozila;
import exceptions.NeispravanBrojSasije;
import exceptions.NeispravnaTablica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import enums.TipVozila;
import models.Vozilo;
import services.VIN;

import java.time.LocalDate;
import java.util.Arrays;

public class VoziloController {
    private VozilaDAO vozilaDAO;
    public Vozilo vozilo;
    public CheckBox choiceBijela, choiceCrna, choiceSmeda, choiceCrvena, choiceSiva;

    public ChoiceBox<TipVozila> choiceTipVozila = new ChoiceBox<>();
    public  ObservableList<TipVozila> tipVozila;

    public ChoiceBox<MarkaVozila> choiceMarkaVozila = new ChoiceBox<>();
    public ObservableList<MarkaVozila> markaVozila;

    public ChoiceBox<String> choiceModelVozila = new ChoiceBox<>();
    public ObservableList<String> modelVozila;

    public TextField fldGodinjaProizvodnje, fldRegistracija;

    public TextField fldBrojSasije;

    public CheckBox novoVozilo;
    public ObservableList<Vozilo> listaVozila;
    public ChoiceBox<Vozilo> choiceVozilo;
    public Label fldPogrenaRegistracija, fldPogresnaSasija, lvrstaBoje, lboja;
    public RadioButton rbObicna, rbMetalik, rbFolija;
    public String boja, vrstaBoje;



    @FXML
    public void initialize() {
        choiceTipVozila.setItems(tipVozila);
        choiceMarkaVozila.setItems(markaVozila);
        choiceModelVozila.setDisable(true);
        novoVozilo.setSelected(false);
        fldPogrenaRegistracija.setVisible(false);
        fldPogresnaSasija.setVisible(false);
        lvrstaBoje.setVisible(false);
        lboja.setVisible(false);
        actionNovoVozilo(null);
        //lista vozila vec unesenih
        choiceVozilo.setItems(listaVozila);
        choiceVozilo.setValue(listaVozila.get(0));

        choiceMarkaVozila.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            modelVozila = FXCollections.observableArrayList(ModelVozila.listaModelaPremaMarki.get(choiceMarkaVozila.getItems().get((Integer) number2)));
            choiceModelVozila.setDisable(false);

            choiceModelVozila.setItems(modelVozila);
        });

    }

    public VoziloController() {
        vozilaDAO = new VozilaDAO();
        tipVozila =  FXCollections.observableArrayList(Arrays.asList(TipVozila.values()));
        markaVozila = FXCollections.observableArrayList(Arrays.asList(MarkaVozila.values()));
        listaVozila = FXCollections.observableArrayList(vozilaDAO.vozila());
    }

    public void clickCancel(ActionEvent actionEvent) {
        vozilo = null;
        Stage stage = (Stage) choiceBijela.getScene().getWindow();
        stage.close();
    }


    public void clickDalje(ActionEvent actionEvent){
        if(novoVozilo.isSelected()) {
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
            if (validacijeGodineProizvodnje(fldGodinjaProizvodnje.getText()) == false) {
                fldGodinjaProizvodnje.getStyleClass().removeAll("poljeIspravno");
                fldGodinjaProizvodnje.getStyleClass().add("poljeNijeIspravno");
                sveOk = false;
            } else {
                fldGodinjaProizvodnje.getStyleClass().removeAll("poljeNijeIspravno");
                fldGodinjaProizvodnje.getStyleClass().add("poljeIspravno");
            }
            //validacija tablica
            try {
                validacijaTablica(fldRegistracija.getText());
                vozilaDAO.jeLiZauzetaRegistracija(fldRegistracija.getText(), vozilo);
                fldRegistracija.getStyleClass().removeAll("poljeNijeIspravno");
                fldRegistracija.getStyleClass().add("poljeIspravno");
                fldPogrenaRegistracija.setVisible(false);

            } catch (NeispravnaTablica neispravnaTablica) {
                fldRegistracija.getStyleClass().removeAll("poljeIspravno");
                fldRegistracija.getStyleClass().add("poljeNijeIspravno");
                fldPogrenaRegistracija.setVisible(true);
                sveOk = false;
                System.out.println(neispravnaTablica.getMessage());
            }

//            if (validacijaTablica(fldRegistracija.getText()) == false) {
//
//                fldRegistracija.getStyleClass().removeAll("poljeIspravno");
//                fldRegistracija.getStyleClass().add("poljeNijeIspravno");
//                fldPogrenaRegistracija.setVisible(true);
//                sveOk = false;
//            } else {
//                fldRegistracija.getStyleClass().removeAll("poljeNijeIspravno");
//                fldRegistracija.getStyleClass().add("poljeIspravno");
//                fldPogrenaRegistracija.setVisible(false);
//            }

            //validacija sasije
            try {
                VIN.isVinValid(fldBrojSasije.getText());
                vozilaDAO.jeLiZauzetaSasija(fldBrojSasije.getText(), vozilo);
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

            //validacija boje
            if (choiceBijela.isSelected() == false && choiceCrna.isSelected() == false && choiceCrvena.isSelected() == false && choiceSmeda.isSelected() == false && choiceSiva.isSelected() == false) {
                lboja.setVisible(true);
                sveOk = false;
            } else {
                lboja.setVisible(false);
            }

            //validacija rb
            if (rbObicna.isSelected() == false && rbMetalik.isSelected() == false && rbFolija.isSelected() == false) {
                lvrstaBoje.setVisible(true);
                sveOk = false;
            } else {
                lvrstaBoje.setVisible(false);
            }

            if (!sveOk) return;
            vozilo = new Vozilo();
            //POSTAVI SVE !!!!!!
            vozilo.setTipVozila(choiceTipVozila.getValue().toString());
            vozilo.setMarka(choiceMarkaVozila.getValue().toString());
            vozilo.setModel(choiceModelVozila.getValue());
            vozilo.setGodinaProizvodnje(Integer.parseInt(fldGodinjaProizvodnje.getText()));
            vozilo.setRegistracija(fldRegistracija.getText());
            vozilo.setBrojsasije(fldBrojSasije.getText());
            vozilo.setBoja(boja);
            vozilo.setVrstaBoje(vrstaBoje);
            vozilaDAO.dodajVozilo(vozilo);
        }
        else {
            System.out.println("Odabrat cemo iz vec postojecih");
        }
        Stage stage = (Stage) choiceBijela.getScene().getWindow();
        stage.close();

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

            rbObicna.setDisable(false);
            rbMetalik.setDisable(false);
            rbFolija.setDisable(false);

            choiceCrvena.setDisable(false);
            choiceSiva.setDisable(false);
            choiceBijela.setDisable(false);
            choiceCrna.setDisable(false);
            choiceSmeda.setDisable(false);

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

            rbObicna.setDisable(true);
            rbMetalik.setDisable(true);
            rbFolija.setDisable(true);

            choiceCrvena.setDisable(true);
            choiceSiva.setDisable(true);
            choiceBijela.setDisable(true);
            choiceCrna.setDisable(true);
            choiceSmeda.setDisable(true);
        }

    }
    public void clickCrvena(ActionEvent actionEvent) {
        choiceSmeda.setSelected(false);
        choiceCrna.setSelected(false);
        choiceBijela.setSelected(false);
        choiceSiva.setSelected(false);
        boja = "Crvena";

    }

    public void clickCrna(ActionEvent actionEvent) {
        choiceSmeda.setSelected(false);
        choiceCrvena.setSelected(false);
        choiceBijela.setSelected(false);
        choiceSiva.setSelected(false);
        boja = "Crna";

    }

    public void clickSiva(ActionEvent actionEvent) {
        choiceSmeda.setSelected(false);
        choiceCrna.setSelected(false);
        choiceBijela.setSelected(false);
        choiceCrvena.setSelected(false);
        boja = "Siva";

    }

    public void clickBijela(ActionEvent actionEvent) {
        choiceSmeda.setSelected(false);
        choiceCrna.setSelected(false);
        choiceCrvena.setSelected(false);
        choiceSiva.setSelected(false);
        boja = "Bijela";

    }

    public void clickSmeda(ActionEvent actionEvent) {
        choiceCrvena.setSelected(false);
        choiceCrna.setSelected(false);
        choiceBijela.setSelected(false);
        choiceSiva.setSelected(false);
        boja = "Smeđa";
    }


    public void clickObicna(ActionEvent actionEvent) {
        rbFolija.setSelected(false);
        rbMetalik.setSelected(false);
        vrstaBoje = "Obična";
    }

    public void clickMetalik(ActionEvent actionEvent) {
        rbFolija.setSelected(false);
        rbObicna.setSelected(false);
        vrstaBoje = "Metalik";
    }

    public void clickFolija(ActionEvent actionEvent) {
        rbObicna.setSelected(false);
        rbMetalik.setSelected(false);
        vrstaBoje = "Folija";
    }

    private boolean validacijeGodineProizvodnje(String text) {
        if(text.isEmpty()) return false;
        int godina = Integer.parseInt(text);
        if(godina > LocalDate.now().getYear() || godina < 1908) return false;
        return true;
    }

    private boolean validacijaTablica(String text) throws NeispravnaTablica {
        if(text.length() != 9) throw new NeispravnaTablica("Duzina Registracije treba biti 9");
        if(text.charAt(3) != '-' || text.charAt(5) != '-') throw new NeispravnaTablica("Neispravan format registracije");
        if(!Character.isDigit(text.charAt(1))  || !Character.isDigit(text.charAt(2))) throw new NeispravnaTablica("Neispravan format registracije");
        if(!Character.isDigit(text.charAt(6))  || !Character.isDigit(text.charAt(7)) || !Character.isDigit(text.charAt(7))) throw new NeispravnaTablica("Neispravan format registracije");
        String string = "AEJKMOT";
        if(string.indexOf(text.charAt(0)) == -1 || string.indexOf(text.charAt(4)) == -1) throw new NeispravnaTablica("Neispravan format registracije");

        return true;
    }

}
