package controllers;

import dao.CustomerDAO;
import dao.TechnicalInspectionDAO;
import dao.TechnicalInspectionTeamDAO;
import dao.EmployeeDAO;
import enums.VrstaTehnickogPregleda;
import exceptions.WrongPhoneNumber;
import exceptions.ScheduledDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Customer;
import models.TechnicalInspection;
import models.Vehicle;
import services.UserSession;

import java.time.LocalDate;
import java.util.Arrays;


public class TP2Controller {
    public TechnicalInspectionDAO technicalInspectionDAO;
    public EmployeeDAO employeeDAO;
    public TechnicalInspectionTeamDAO technicalInspectionTeamDAO;
    public Label l2, l3,ltehnicki;
    public TextField fldIme, fldPrezime, fldMjestoPrebivalista, fldBrojTelefona;
    public CustomerDAO customerDAO;
    public Label fldPogresanBroj;
    public Vehicle vehicle;

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

    public TP2Controller(Vehicle vehicle) {
        technicalInspectionDAO = new TechnicalInspectionDAO();
        employeeDAO = new EmployeeDAO();
        technicalInspectionTeamDAO = new TechnicalInspectionTeamDAO();
        this.vehicle = vehicle;
        customerDAO = new CustomerDAO();
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

        } catch (WrongPhoneNumber wrongPhoneNumber) {
            fldBrojTelefona.getStyleClass().removeAll("poljeIspravno");
            fldBrojTelefona.getStyleClass().add("poljeNijeIspravno");
            fldPogresanBroj.setVisible(true);
            sveOk = false;
            System.out.println(wrongPhoneNumber.getMessage());
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
        klijent.setFirstName(fldIme.getText());
        klijent.setLastName(fldPrezime.getText());
        klijent.setAddress(fldMjestoPrebivalista.getText());
        klijent.setPhoneNumber(fldBrojTelefona.getText());

        customerDAO.addCustomer(klijent);

        //pravimo tehnicki da ga dodamo jer je sve ok
        TechnicalInspection technicalInspection = new TechnicalInspection();

        technicalInspection.setStatusTehnickogPregleda("Zakazan");
//        //ovdje smo mijenjali
//        technicalInspection.setKlijent(klijent);
        technicalInspection.setVehicle(vehicle);
        technicalInspection.setCustomer(klijent);
        //technicalInspection.setVoziloID(vehicle.getId());
        technicalInspection.setVrstaTehnickogPregleda(choiceVrstaPregleda.getValue().toString());
        technicalInspection.setDateOfInspection(choiceDatum.getValue());
        technicalInspection.getEmployees().add(employeeDAO.getEmployeeWithUserName(UserSession.getKorisnickoIme()));

        try {
            technicalInspectionDAO.addTI(technicalInspection);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tehnicki pregled");
            alert.setHeaderText("Uspjesno ste zakazali tehnicki pregled!");
            System.out.println("ID novog tehnickog " + technicalInspection.getId());
            System.out.println("ID usera je" + employeeDAO.getEmployeeWithUserName(UserSession.getKorisnickoIme()).getId());
            technicalInspectionTeamDAO.connectTIAndEmployee(technicalInspection.getId(), employeeDAO.getEmployeeWithUserName(UserSession.getKorisnickoIme()).getId());
            //alert.setContentText("I have a great message for you!");
            alert.showAndWait();
        }catch (ScheduledDate scheduledDate) {
            System.out.println(scheduledDate);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Tehnicki pregled");
            alert.setHeaderText("Vec zakazan pregled!");
            alert.showAndWait();
        }

        //mozda da se vrati na pocetnu nez
    }

    private boolean validacijaTelefona(String fldBrojTelefona) throws WrongPhoneNumber {
        if(fldBrojTelefona.length() != 11) throw new WrongPhoneNumber("Duzina telefonskob broja treba biti 11");
        if(fldBrojTelefona.charAt(3) != '-' || fldBrojTelefona.charAt(7) != '-') throw new WrongPhoneNumber("Neispravan format telefonskog broja");
        for(int i = 0; i < fldBrojTelefona.length(); i++) {
            if(i == 3 || i == 7) continue;
            if(!Character.isDigit(fldBrojTelefona.charAt(i))) throw new WrongPhoneNumber("Neispravan format telefonskog broja");
        }
        return true;
    }
}
