package controllers;

import dao.UsersDAO;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import models.UserSession;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LogInController {
    public TextField fldKorisnickoIme;
    public PasswordField fldLozinka;


    private UsersDAO dao;


    public LogInController() {
        dao = UsersDAO.getInstance();
    }

    public void clickPrijava(ActionEvent actionEvent) {


        if(fldKorisnickoIme.getText().trim().isEmpty() || fldLozinka.getLength() == 0) {
            fldKorisnickoIme.getStyleClass().add("poljeNijeIspravno");
            fldLozinka.getStyleClass().add("poljeNijeIspravno");
        }
        ArrayList<User> pomocni = dao.uposlenici();
        for(int i = 0; i < pomocni.size(); i++) {
            if(pomocni.get(i).getKorisnickoIme().equals(fldKorisnickoIme.getText()) && pomocni.get(i).getLozinka().equals(fldLozinka.getText())) {
                //nasli smo korisnika sada trebamo njega sacuvati
                UserSession.getInstace(pomocni.get(i).getKorisnickoIme(), pomocni.get(i).isPristup());
                closeWindow();
            }
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) fldLozinka.getScene().getWindow();
        stage.close();

        //radi OK
        try {
            Stage pstage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/administrator.fxml"));
            AdministratorController ctrl = new AdministratorController();
            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();
            stage.setTitle("Administrator");
            stage.setScene(new Scene(root, 396, 311));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

