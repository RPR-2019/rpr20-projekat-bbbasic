package controllers;

import dao.UsersDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import models.User;


import java.util.ArrayList;

public class AdministratorController {
    public ListView listaUposlenici;
    private ObservableList<User> lista;

    public AdministratorController() {
        lista = FXCollections.observableArrayList(UsersDAO.getInstance().uposlenici());

    }

    @FXML
    public void initialize() {
        listaUposlenici.setItems(lista);

    }
}
