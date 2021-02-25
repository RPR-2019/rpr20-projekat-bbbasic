package dao;

import exceptions.NeispravanBrojSasije;
import exceptions.NeispravnaTablica;
import models.Vehicle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VozilaDAO extends BaseDAO{

    private PreparedStatement dodajVoziloUpit, odrediIdVozilaUpit, svaVozilaUpit, dajVoziloSaSasijom, dajVoziloSaRegistracijom;


    protected void kreirajUpite() {
        try {
            svaVozilaUpit = dbConnection.getSession().prepareStatement("SELECT * FROM vehicle");
            dodajVoziloUpit = dbConnection.getSession().prepareStatement("INSERT INTO vehicle VALUES(?,?,?,?,?,?,?,?,?)");
            odrediIdVozilaUpit = dbConnection.getSession().prepareStatement("SELECT MAX(id)+1 FROM vehicle");
            dajVoziloSaSasijom = dbConnection.getSession().prepareStatement("SELECT * FROM vehicle WHERE broj_sasije=?");
            dajVoziloSaRegistracijom = dbConnection.getSession().prepareStatement("SELECT * FROM vehicle WHERE registracija=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Vehicle> vozila() {
        ArrayList<Vehicle> rezultat = new ArrayList();
        try {
            ResultSet rs = svaVozilaUpit.executeQuery();
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6),rs.getString(7),rs.getString(8), rs.getString(9));
                rezultat.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void dodajVozilo(Vehicle vehicle) {
        try {
            ResultSet rs = odrediIdVozilaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            vehicle.setId(id);

            dodajVoziloUpit.setInt(1, vehicle.getId());
            dodajVoziloUpit.setString(2, vehicle.getType());
            dodajVoziloUpit.setString(3, vehicle.getBrand());
            dodajVoziloUpit.setString(4, vehicle.getModel());
            dodajVoziloUpit.setInt(5, vehicle.getYearOfProduction());
            dodajVoziloUpit.setString(6, vehicle.getRegistration());
            dodajVoziloUpit.setString(7, vehicle.getChassisNumber());
            dodajVoziloUpit.setString(8, vehicle.getColor());
            dodajVoziloUpit.setString(9, vehicle.getColorType());

            dodajVoziloUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean jeLiZauzetaSasija(String text, Vehicle vehicle) throws NeispravanBrojSasije {
        try {
            dajVoziloSaSasijom.setString(1, text);
            ResultSet resultSet = dajVoziloSaSasijom.executeQuery();
            if(resultSet.next()) {
                if(vehicle == null) throw new NeispravanBrojSasije("VIN vec postoji u bazi");
                if(resultSet.getInt(1) == vehicle.getId() && resultSet.getString(7).equals(vehicle.getChassisNumber())) {
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

    public boolean jeLiZauzetaRegistracija(String text, Vehicle vehicle) throws NeispravnaTablica {
        try {
            dajVoziloSaRegistracijom.setString(1, text);
            ResultSet resultSet = dajVoziloSaRegistracijom.executeQuery();
            if(resultSet.next()) {
                if(vehicle == null) throw new NeispravnaTablica("Registracija vec postoji u bazi");
                if(resultSet.getInt(1) == vehicle.getId() && resultSet.getString(6).equals(vehicle.getChassisNumber())) {
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
