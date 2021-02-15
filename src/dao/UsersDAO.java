package dao;

import models.User;

public class UsersDAO {

    public void login(String username, String password) {
        User user = new User(username);
        System.out.println(username +  password);
    }
}
