package dao;

import models.Uposlenik;
import services.DBDateHelper;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

public class UsersDAO extends BaseDAO {
    private PreparedStatement sviUposleniUpit, dajUposlenogUpit, dajUposlenogSaKorisnickimImenomUpit;

    protected void kreirajUpite() {
        try {
            sviUposleniUpit = dbConnection.getSession().prepareStatement("SELECT * FROM uposlenik");
            dajUposlenogUpit = dbConnection.getSession().prepareStatement("SELECT * FROM uposlenik WHERE id=?");
            dajUposlenogSaKorisnickimImenomUpit = dbConnection.getSession().prepareStatement("SELECT * FROM uposlenik WHERE korisnicko_ime=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //funkcije

    public ArrayList<Uposlenik> uposlenici() {
        ArrayList<Uposlenik> rezultat = new ArrayList();
        try {
            ResultSet rs = sviUposleniUpit.executeQuery();
            while (rs.next()) {
                Uposlenik uposlenik = new Uposlenik(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), DBDateHelper.stringToDate(rs.getString(6)), DBDateHelper.stringToDate(rs.getString(7)), rs.getBoolean(8));
                rezultat.add(uposlenik);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void dodajUposlenog(Uposlenik uposlenik) {
    }

    public boolean zauzetoKorisnickoIme(String korisnickoIme) {
        try {
            dajUposlenogSaKorisnickimImenomUpit.setString(1, korisnickoIme);
            ResultSet resultSet = dajUposlenogSaKorisnickimImenomUpit.executeQuery();
            if(resultSet.next())
                return false;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
