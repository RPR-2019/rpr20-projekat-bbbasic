package controllers;

import dao.UsersDAO;

import javafx.event.ActionEvent;

public class LogInController {
    UsersDAO _user_dao;

    public LogInController() {
        this._user_dao = new UsersDAO();
    }

     public void handleLogIn(ActionEvent actionEvent) {
        System.out.println("Log in");
        this._user_dao.login("emina", "password");
    }
}
