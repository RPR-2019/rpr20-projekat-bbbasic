package controllers;

import constants.VehicleModel;
import dao.VehicleDAO;
import enums.VehicleBrand;
import exceptions.WrongVINNumber;
import exceptions.WrongRegistrationNumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import enums.VehicleType;
import models.Vehicle;
import services.VIN;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class TP1Controller {
    public Label l1;
    private VehicleDAO vehicleDAO;
    public Vehicle vehicle;
    public CheckBox choiceBijela, choiceCrna, choiceSmeda, choiceCrvena, choiceSiva;

    public ChoiceBox<VehicleType> choiceTipVozila = new ChoiceBox<>();
    public  ObservableList<VehicleType> vehicleType;

    public ChoiceBox<VehicleBrand> choiceMarkaVozila = new ChoiceBox<>();
    public ObservableList<VehicleBrand> vehicleBrand;

    public ChoiceBox<String> choiceModelVozila = new ChoiceBox<>();
    public ObservableList<String> modelVozila;

    public TextField fldGodinjaProizvodnje, fldRegistracija;

    public TextField fldBrojSasije;

    public CheckBox novoVozilo;
    public ObservableList<Vehicle> listaVozila;
    public ChoiceBox<Vehicle> choiceVozilo;
    public Label fldPogrenaRegistracija, fldPogresnaSasija, lvrstaBoje, lboja, ltehnicki;
    public RadioButton rbObicna, rbMetalik, rbFolija;
    public String boja, vrstaBoje;
    public BorderPane mainPane;
    public GridPane gridNovoVozilo, gridStaro;



    @FXML
    public void initialize() {
        l1.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        ltehnicki.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        choiceTipVozila.setItems(vehicleType);
        choiceMarkaVozila.setItems(vehicleBrand);
        //vidljivost
        gridNovoVozilo.setDisable(true);
        gridStaro.setDisable(false);
        choiceModelVozila.setDisable(true);
        novoVozilo.setSelected(false);
        fldPogresnaSasija.setVisible(false);
        fldPogrenaRegistracija.setVisible(false);
        lvrstaBoje.setVisible(false);
        lboja.setVisible(false);
        actionNovoVozilo(null);
        //lista vehicles vec unesenih
        choiceVozilo.setItems(listaVozila);
        choiceVozilo.setValue(listaVozila.get(0));

        choiceMarkaVozila.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            modelVozila = FXCollections.observableArrayList(VehicleModel.listFromBrands.get(choiceMarkaVozila.getItems().get((Integer) number2)));
            choiceModelVozila.setDisable(false);

            choiceModelVozila.setItems(modelVozila);
        });

    }

    public TP1Controller(BorderPane mainPane) {
        this.mainPane = mainPane;
        vehicleDAO = new VehicleDAO();
        vehicleType =  FXCollections.observableArrayList(Arrays.asList(VehicleType.values()));
        vehicleBrand = FXCollections.observableArrayList(Arrays.asList(VehicleBrand.values()));
        listaVozila = FXCollections.observableArrayList(vehicleDAO.vehicles());
    }

    public void clickCancel(ActionEvent actionEvent) {
        vehicle = null;
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
                vehicleDAO.isRegistrationTaken(fldRegistracija.getText(), vehicle);
                fldRegistracija.getStyleClass().removeAll("poljeNijeIspravno");
                fldRegistracija.getStyleClass().add("poljeIspravno");
                fldPogrenaRegistracija.setVisible(false);

            } catch (WrongRegistrationNumber wrongRegistrationNumber) {
                fldRegistracija.getStyleClass().removeAll("poljeIspravno");
                fldRegistracija.getStyleClass().add("poljeNijeIspravno");
                fldPogrenaRegistracija.setVisible(true);
                sveOk = false;
                System.out.println(wrongRegistrationNumber.getMessage());
            }

            //validacija sasije
            try {
                VIN.isVinValid(fldBrojSasije.getText());
                vehicleDAO.isChassisNumberTaken(fldBrojSasije.getText(), vehicle);
                fldBrojSasije.getStyleClass().removeAll("poljeNijeIspravno");
                fldBrojSasije.getStyleClass().add("poljeIspravno");
                fldPogresnaSasija.setVisible(false);
            } catch (WrongVINNumber wrongVINNumber) {
                fldBrojSasije.getStyleClass().removeAll("poljeIspravno");
                fldBrojSasije.getStyleClass().add("poljeNijeIspravno");
                fldPogresnaSasija.setVisible(true);
                sveOk = false;
                System.out.println(wrongVINNumber.getMessage());
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
            vehicle = new Vehicle();
            //POSTAVI SVE !!!!!!
            vehicle.setType(choiceTipVozila.getValue().toString());
            vehicle.setBrand(choiceMarkaVozila.getValue().toString());
            vehicle.setModel(choiceModelVozila.getValue());
            vehicle.setYearOfProduction(Integer.parseInt(fldGodinjaProizvodnje.getText()));
            vehicle.setRegistration(fldRegistracija.getText());
            vehicle.setChassisNumber(fldBrojSasije.getText());
            vehicle.setColor(boja);
            vehicle.setColorType(vrstaBoje);
            vehicleDAO.addVehicle(vehicle);
        }
        else {
            vehicle = choiceVozilo.getValue();
            System.out.println("Odabrat cemo iz vec postojecih " + vehicle);
        }
        //idemo dalje na klijenta i saljemo mu vehicle
            System.out.println("Uposlenici");
            Stage stage = new Stage();
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/zakazivanjeTP2.fxml"));
                TP2Controller klijentController = new TP2Controller(vehicle);
                loader.setController(klijentController);
                root = loader.load();
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.setResizable(true);
                stage.setWidth(450);
                stage.setHeight(580);
                //MIJENJANJE
                mainPane.setCenter(root);
                return;

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    public void actionNovoVozilo(ActionEvent actionEvent) {
        if(novoVozilo.isSelected()) {
            gridStaro.setDisable(true);
            gridNovoVozilo.setDisable(false);

        }
        else {
            gridStaro.setDisable(false);
            gridNovoVozilo.setDisable(true);
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

    private boolean validacijaTablica(String text) throws WrongRegistrationNumber {
        if(text.length() != 9) throw new WrongRegistrationNumber("Duzina Registracije treba biti 9");
        if(text.charAt(3) != '-' || text.charAt(5) != '-') throw new WrongRegistrationNumber("Neispravan format registracije");
        if(!Character.isDigit(text.charAt(1))  || !Character.isDigit(text.charAt(2))) throw new WrongRegistrationNumber("Neispravan format registracije");
        if(!Character.isDigit(text.charAt(6))  || !Character.isDigit(text.charAt(7)) || !Character.isDigit(text.charAt(7))) throw new WrongRegistrationNumber("Neispravan format registracije");
        String string = "AEJKMOT";
        if(string.indexOf(text.charAt(0)) == -1 || string.indexOf(text.charAt(4)) == -1) throw new WrongRegistrationNumber("Neispravan format registracije");

        return true;
    }

}
