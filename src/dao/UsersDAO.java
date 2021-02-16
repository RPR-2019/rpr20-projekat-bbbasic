package dao;

import models.Uposlenik;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class UsersDAO extends BaseDAO {
    private PreparedStatement sviUposleniUpit, dajUposlenogUpit, dajUposlenogSaKorisnickimImenomUpit, dodajUposlenogUpit, odrediIdUposlenikaUpit
                            , obrisiUposlenog, promijeniUposlenogUpit;

    protected void kreirajUpite() {
        try {
            sviUposleniUpit = dbConnection.getSession().prepareStatement("SELECT * FROM uposlenik");
            dajUposlenogUpit = dbConnection.getSession().prepareStatement("SELECT * FROM uposlenik WHERE id=?");
            dajUposlenogSaKorisnickimImenomUpit = dbConnection.getSession().prepareStatement("SELECT * FROM uposlenik WHERE korisnicko_ime=?");
            dodajUposlenogUpit = dbConnection.getSession().prepareStatement("INSERT INTO uposlenik VALUES(?,?,?,?,?,?,?,?)");
            odrediIdUposlenikaUpit = dbConnection.getSession().prepareStatement("SELECT MAX(id)+1 FROM uposlenik");
            obrisiUposlenog = dbConnection.getSession().prepareStatement("DELETE FROM uposlenik WHERE id=?");
            promijeniUposlenogUpit = dbConnection.getSession().prepareStatement("UPDATE uposlenik SET ime=?, prezime=?, lozinka=?, korisnicko_ime=?, datum_rodjenja=?, datum_uposlenja=? WHERE id=?");
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
                Uposlenik uposlenik = new Uposlenik(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), LocalDate.parse(rs.getString(6)), LocalDate.parse(rs.getString(7)), rs.getBoolean(8));
                rezultat.add(uposlenik);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void dodajUposlenog(Uposlenik uposlenik) {
        try {
            ResultSet rs = odrediIdUposlenikaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            uposlenik.setId(id);

            dodajUposlenogUpit.setInt(1, uposlenik.getId());
            dodajUposlenogUpit.setString(2, uposlenik.getIme());
            dodajUposlenogUpit.setString(3, uposlenik.getPrezime());
            dodajUposlenogUpit.setString(4, uposlenik.getLozinka());
            dodajUposlenogUpit.setString(5, uposlenik.getKorisnickoIme());
            dodajUposlenogUpit.setString(6, uposlenik.getDatumRodjenja().toString());
            dodajUposlenogUpit.setString(7, uposlenik.getDatumRodjenja().toString());
            dodajUposlenogUpit.setBoolean(8, uposlenik.isPristup());

            dodajUposlenogUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean zauzetoKorisnickoIme(String korisnickoIme, Uposlenik uposlenik) {
        try {
            dajUposlenogSaKorisnickimImenomUpit.setString(1, korisnickoIme);
            ResultSet resultSet = dajUposlenogSaKorisnickimImenomUpit.executeQuery();
            if(resultSet.next()) {
                if(uposlenik == null) return false;
                if(resultSet.getInt(1) == uposlenik.getId() && resultSet.getString(5).equals(uposlenik.getKorisnickoIme())) {
                    return true;
                }
                return false;
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void obrisiUposlenog(Uposlenik uposlenik) {
        try {
            obrisiUposlenog.setInt(1, uposlenik.getId());
            obrisiUposlenog.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniUposlenog(Uposlenik uposlenik) {
        try {
            promijeniUposlenogUpit.setString(1, uposlenik.getIme());
            promijeniUposlenogUpit.setString(2, uposlenik.getPrezime());
            promijeniUposlenogUpit.setString(3, uposlenik.getLozinka());
            promijeniUposlenogUpit.setString(4, uposlenik.getKorisnickoIme());
            promijeniUposlenogUpit.setString(5, uposlenik.getDatumRodjenja().toString());
            promijeniUposlenogUpit.setString(6, uposlenik.getDatumZaposlenja().toString());
            promijeniUposlenogUpit.setInt(7, uposlenik.getId());
            promijeniUposlenogUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
