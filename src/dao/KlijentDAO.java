package dao;

import models.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KlijentDAO extends BaseDAO {

    private PreparedStatement sviKlijentiUpit, dajKlijentaUpit, dodajKlijentaUpit, odrediIdKlijentaUpit;

    protected void kreirajUpite() {
        try {
            sviKlijentiUpit = dbConnection.getSession().prepareStatement("SELECT * FROM klijent");
            dajKlijentaUpit = dbConnection.getSession().prepareStatement("SELECT * FROM klijent WHERE id=?");
            dodajKlijentaUpit = dbConnection.getSession().prepareStatement("INSERT INTO klijent VALUES(?,?,?,?,?)");
            odrediIdKlijentaUpit = dbConnection.getSession().prepareStatement("SELECT MAX(id)+1 FROM klijent");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //funkcije

    public ArrayList<Customer> klijenti() {
        ArrayList<Customer> rezultat = new ArrayList();
        try {
            ResultSet rs = sviKlijentiUpit.executeQuery();
            while (rs.next()) {
                Customer klijent = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                rezultat.add(klijent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void dodajKlijenta(Customer klijent) {
        try {
            if(postojiLiKlijentUBazi(klijent)) return;
            ResultSet rs = odrediIdKlijentaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            klijent.setId(id);

            dodajKlijentaUpit.setInt(1, klijent.getId());
            dodajKlijentaUpit.setString(2, klijent.getFirst_name());
            dodajKlijentaUpit.setString(3, klijent.getLast_name());
            dodajKlijentaUpit.setString(4, klijent.getAddress());
            dodajKlijentaUpit.setString(5, klijent.getPhone_number());

            dodajKlijentaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean postojiLiKlijentUBazi(Customer klijent) {
        ArrayList<Customer> rezultat = new ArrayList();
        try {
            ResultSet rs = sviKlijentiUpit.executeQuery();
            while (rs.next()) {
                Customer klijent2 = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                if(klijent.equals(klijent2)) {
                    klijent.setId(klijent2.getId());
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
