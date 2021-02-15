package dao;

import models.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UsersDAO {
    private static UsersDAO instance = null;
    private Connection conn;

    private PreparedStatement sviUposleniUpit, dajUposlenogUpit;
    private UsersDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            sviUposleniUpit = conn.prepareStatement("SELECT * FROM uposlenik");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                sviUposleniUpit = conn.prepareStatement("SELECT * FROM uposlenik");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            dajUposlenogUpit = conn.prepareStatement("SELECT * FROM uposlenik WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static UsersDAO getInstance() {
        if (instance == null) instance = new UsersDAO();
        return instance;
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void vratiBazuNaDefault() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM uposlenik");

        regenerisiBazu();
    }
    //funkcije

    public ArrayList<User> uposlenici() {
        ArrayList<User> rezultat = new ArrayList();
        try {
            ResultSet rs = sviUposleniUpit.executeQuery();
            while (rs.next()) {
                User uposlenik = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getBoolean(8));
                rezultat.add(uposlenik);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }
}
