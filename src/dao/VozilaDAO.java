package dao;

import exceptions.NeispravanBrojSasije;
import exceptions.NeispravnaTablica;
import models.Vozilo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VozilaDAO extends BaseDAO{

    private PreparedStatement dodajVoziloUpit, odrediIdVozilaUpit, svaVozilaUpit, dajVoziloSaSasijom, dajVoziloSaRegistracijom;


    protected void kreirajUpite() {
        try {
            svaVozilaUpit = dbConnection.getSession().prepareStatement("SELECT * FROM vozilo");
            dodajVoziloUpit = dbConnection.getSession().prepareStatement("INSERT INTO vozilo VALUES(?,?,?,?,?,?,?,?,?)");
            odrediIdVozilaUpit = dbConnection.getSession().prepareStatement("SELECT MAX(id)+1 FROM vozilo");
            dajVoziloSaSasijom = dbConnection.getSession().prepareStatement("SELECT * FROM vozilo WHERE broj_sasije=?");
            dajVoziloSaRegistracijom = dbConnection.getSession().prepareStatement("SELECT * FROM vozilo WHERE registracija=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Vozilo> vozila() {
        ArrayList<Vozilo> rezultat = new ArrayList();
        try {
            ResultSet rs = svaVozilaUpit.executeQuery();
            while (rs.next()) {
                Vozilo vozilo = new Vozilo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6),rs.getString(7),rs.getString(8), rs.getString(9));
                rezultat.add(vozilo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void dodajVozilo(Vozilo vozilo) {
        try {
            ResultSet rs = odrediIdVozilaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            vozilo.setId(id);

            dodajVoziloUpit.setInt(1, vozilo.getId());
            dodajVoziloUpit.setString(2, vozilo.getTipVozila());
            dodajVoziloUpit.setString(3, vozilo.getMarka());
            dodajVoziloUpit.setString(4, vozilo.getModel());
            dodajVoziloUpit.setInt(5, vozilo.getGodinaProizvodnje());
            dodajVoziloUpit.setString(6, vozilo.getRegistracija());
            dodajVoziloUpit.setString(7, vozilo.getBrojsasije());
            dodajVoziloUpit.setString(8, vozilo.getBoja());
            dodajVoziloUpit.setString(9, vozilo.getVrstaBoje());

            dodajVoziloUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean jeLiZauzetaSasija(String text, Vozilo vozilo) throws NeispravanBrojSasije {
        try {
            dajVoziloSaSasijom.setString(1, text);
            ResultSet resultSet = dajVoziloSaSasijom.executeQuery();
            if(resultSet.next()) {
                if(vozilo == null) throw new NeispravanBrojSasije("VIN vec postoji u bazi");
                if(resultSet.getInt(1) == vozilo.getId() && resultSet.getString(7).equals(vozilo.getBrojsasije())) {
                    return true;
                }
                throw new NeispravanBrojSasije("VIN vec postoji u bazi");
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean jeLiZauzetaRegistracija(String text, Vozilo vozilo) throws NeispravnaTablica {
        try {
            dajVoziloSaRegistracijom.setString(1, text);
            ResultSet resultSet = dajVoziloSaRegistracijom.executeQuery();
            if(resultSet.next()) {
                if(vozilo == null) throw new NeispravnaTablica("Registracija vec postoji u bazi");
                if(resultSet.getInt(1) == vozilo.getId() && resultSet.getString(6).equals(vozilo.getBrojsasije())) {
                    return true;
                }
                throw new NeispravnaTablica("Registracija vec postoji u bazi");
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
}
