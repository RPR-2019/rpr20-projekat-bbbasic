package controllers;

import dao.UsersDAO;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;

import java.awt.*;
import java.util.ArrayList;

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
                closeWindow();
            }
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) fldLozinka.getScene().getWindow();
        stage.close();
    }

}

