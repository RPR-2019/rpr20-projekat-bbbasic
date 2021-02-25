package controllers;

import dao.EmployeeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Employee;

import java.io.IOException;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdminPristupKorisnicimaController {
    public ListView listaUposlenici;
    private ObservableList<Employee> lista;
    private EmployeeDAO employeeDAO;
    public Label lUposlenici;

    public AdminPristupKorisnicimaController() {
        employeeDAO = new EmployeeDAO();
        lista = FXCollections.observableArrayList(employeeDAO.employees());

    }

    @FXML
    public void initialize() {
        lUposlenici.setStyle("-fx-background-color: rgba(0, 0, 0, 0.08)");
        listaUposlenici.setItems(lista);

    }
    public void dodajUposlenika(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uposlenik.fxml"));
            //trece ce biti employeeDAO.tehnickipregledi()
            UposlenikController uposlenikController = new UposlenikController(null);
            loader.setController(uposlenikController);
            root = loader.load();
            stage.setTitle("Employee");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.getIcons().add(new Image("/img/mainicon.png"));
            stage.show();

            stage.setOnHiding( event -> {
                Employee employee = uposlenikController.getUposlenik();
                if (employee != null) {
                    employeeDAO.addEmployee(employee);
                    lista.setAll(employeeDAO.employees());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionIzmijeniUposlenika(ActionEvent actionEvent) {
        Employee employee = (Employee) listaUposlenici.getSelectionModel().getSelectedItem();
        if (employee == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uposlenik.fxml"));
            UposlenikController uposlenikController = new UposlenikController(employee);
            loader.setController(uposlenikController);
            root = loader.load();
            stage.setTitle("Employee");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.getIcons().add(new Image("/img/mainicon.png"));
            stage.show();

            stage.setOnHiding( event -> {
                Employee employee1 = uposlenikController.getUposlenik();
                if (employee1 != null) {
                    // Ovdje ne smije doći do izuzetka, jer se prozor neće zatvoriti
                    try {
                        employeeDAO.updateEmployee(employee1);
                        lista.setAll(employeeDAO.employees());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionObrisiUposlenika(ActionEvent actionEvent) {
        Employee employee = (Employee) listaUposlenici.getSelectionModel().getSelectedItem();
        if (employee == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja Uposlenika");
        alert.setHeaderText("Brisanje uposlenika " + employee.getFirstName() + " " + employee.getLastName());
        alert.setContentText("Da li ste sigurni da zelite obrisati uposlenog?");
        Stage stage = (Stage )alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/img/mainicon.png"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            employeeDAO.deleteEmployee(employee);
            lista.setAll(employeeDAO.employees());
        }
    }
}
