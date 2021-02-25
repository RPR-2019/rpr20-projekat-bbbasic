package controllers;

import dao.EmployeeDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.TechnicalInspection;
import models.Employee;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;



public class UposlenikController {
    private EmployeeDAO employeeDAO;
    private Employee employee;
    public ObservableList<TechnicalInspection> listTehnickiPregledi;
    public ChoiceBox<TechnicalInspection> choiceTehnicki;

    public TextField fldIme;
    public TextField fldPrezime;
    public PasswordField fldLozinka;
    public TextField fldKorisnickoIme;
    public Label zauzetoKorisnickoIme;
    public DatePicker fldDatumRodjenja;
    public  DatePicker fldDatumZaposlenja;
    public RadioButton privilegija;

    public UposlenikController(Employee employee) {
        employeeDAO = new EmployeeDAO();
        this.employee = employee;
    }

    @FXML
    public void initialize() {
        zauzetoKorisnickoIme.setVisible(false);
        privilegija.setSelected(false);
        if(employee != null) {
            fldIme.setText(employee.getFirstName());
            fldPrezime.setText(employee.getLastName());
            fldLozinka.setText(employee.getPassword());
            fldKorisnickoIme.setText(employee.getUserName());
            fldDatumRodjenja.setValue(employee.getBirthDate());
            privilegija.setSelected(employee.isAdmin());
            fldDatumZaposlenja.setValue(employee.getHireDate());
        }

    }

    public Employee getUposlenik() { return employee; }

    public void clickCancel(ActionEvent actionEvent) {
        employee = null;
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
        if(employeeDAO.isUserNameTaken(fldKorisnickoIme.getText(), employee) == false || fldKorisnickoIme.getText().isEmpty()) {
            if(employeeDAO.isUserNameTaken(fldKorisnickoIme.getText(), employee) == false)
                zauzetoKorisnickoIme.setVisible(true);
            fldKorisnickoIme.getStyleClass().removeAll("poljeIspravno");
            fldKorisnickoIme.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        }
        else {
            fldKorisnickoIme.getStyleClass().removeAll("poljeNijeIspravno");
            fldKorisnickoIme.getStyleClass().add("poljeIspravno");
        }
        //validacija ako employee nije null korisnickoIme


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

        if (employee == null) employee = new Employee();
        employee.setFirstName(fldIme.getText());
        employee.setLastName(fldPrezime.getText());
        employee.setPassword(fldLozinka.getText());
        employee.setUserName(fldKorisnickoIme.getText());
        employee.setAdmin(privilegija.isSelected());


        employee.setBirthDate(fldDatumRodjenja.getValue());
        employee.setHireDate(fldDatumZaposlenja.getValue());

        Stage stage = (Stage) fldIme.getScene().getWindow();
        stage.close();
    }
}
