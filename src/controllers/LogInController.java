package controllers;

import dao.UsersDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Uposlenik;
import services.UserSession;

import java.io.IOException;
import java.util.ArrayList;

public class LogInController {
    public TextField fldKorisnickoIme;
    public PasswordField fldLozinka;
    public Label fldGreska;


    private UsersDAO dao;

    @FXML
    public void initialize() {
        fldGreska.setVisible(false);
        fldKorisnickoIme.setText("mehomehic");
        fldLozinka.setText("lozinka");
    }


    public LogInController() {
        dao = new UsersDAO();
    }

    public void clickPrijava(ActionEvent actionEvent) throws InterruptedException {
        ArrayList<Uposlenik> pomocni = dao.uposlenici();
        for(int i = 0; i < pomocni.size(); i++) {
            if(pomocni.get(i).getKorisnickoIme().equals(fldKorisnickoIme.getText()) && pomocni.get(i).getLozinka().equals(fldLozinka.getText())) {
                fldLozinka.getStyleClass().removeAll("poljeNijeIspravno");
                fldLozinka.getStyleClass().add("poljeIspravno");

                fldKorisnickoIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldKorisnickoIme.getStyleClass().add("poljeIspravno");

                //nasli smo korisnika sada trebamo njega sacuvati
                System.out.println("Sada je " + pomocni.get(i).getKorisnickoIme());
                UserSession.getInstace(pomocni.get(i).getKorisnickoIme(), pomocni.get(i).isPristup());
                closeWindow();
            }
            else {
                fldGreska.setVisible(true);
                fldLozinka.getStyleClass().removeAll("poljeIspravno");
                fldLozinka.getStyleClass().add("poljeNijeIspravno");

                fldKorisnickoIme.getStyleClass().removeAll("poljeIspravno");
                fldKorisnickoIme.getStyleClass().add("poljeNijeIspravno");
            }
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) fldLozinka.getScene().getWindow();
        stage.close();

        //radi OK
        try {
            Stage pstage = new Stage();
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/administratorglavni.fxml"));
            //AdministratorGlavniController ctrl = new AdministratorGlavniController();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
            HomeController ctrl = new HomeController();
            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();
            stage.setTitle("MeCARnic");
            stage.setScene(new Scene(root, 396, 311));
            stage.setWidth(900);
            stage.setHeight(730);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

