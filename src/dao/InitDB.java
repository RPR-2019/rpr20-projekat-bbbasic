package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InitDB {
    protected DBConnection dbConnection;

    public InitDB() {
        dbConnection = DBConnection.getInstance();
    }

    public void obrisiSve() throws SQLException {
        Statement stmt = dbConnection.getSession().createStatement();
        stmt.executeUpdate("DELETE FROM uposlenik");
        stmt.executeUpdate("DELETE FROM vehicle");
        stmt.executeUpdate("DELETE FROM klijent");
        stmt.executeUpdate("DELETE FROM tim_tehnicki_pregled");
        stmt.executeUpdate("DELETE FROM tehnicki_pregled");
    }

    public void kreirajBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = dbConnection.getSession().createStatement();
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
}
