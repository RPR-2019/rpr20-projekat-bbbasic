package dao;

import models.Klijent;
import models.Uposlenik;

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

    public ArrayList<Klijent> klijenti() {
        ArrayList<Klijent> rezultat = new ArrayList();
        try {
            ResultSet rs = sviKlijentiUpit.executeQuery();
            while (rs.next()) {
                Klijent klijent = new Klijent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                rezultat.add(klijent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void dodajKlijenta(Klijent klijent) {
        try {
            if(postojiLiKlijentUBazi(klijent)) return;
            ResultSet rs = odrediIdKlijentaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            klijent.setId(id);

            dodajKlijentaUpit.setInt(1, klijent.getId());
            dodajKlijentaUpit.setString(2, klijent.getIme());
            dodajKlijentaUpit.setString(3, klijent.getPrezime());
            dodajKlijentaUpit.setString(4, klijent.getMjestoPrebivalista());
            dodajKlijentaUpit.setString(5, klijent.getBrojTelefona());

            dodajKlijentaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean postojiLiKlijentUBazi(Klijent klijent) {
        ArrayList<Klijent> rezultat = new ArrayList();
        try {
            ResultSet rs = sviKlijentiUpit.executeQuery();
            while (rs.next()) {
                Klijent klijent2 = new Klijent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
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
