package dao;

import models.Employee;

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
            promijeniUposlenogUpit = dbConnection.getSession().prepareStatement("UPDATE uposlenik SET ime=?, prezime=?, lozinka=?, korisnicko_ime=?, datum_rodjenja=?, datum_uposlenja=?, administrator=? WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //funkcije

    public ArrayList<Employee> uposlenici() {
        ArrayList<Employee> rezultat = new ArrayList();
        try {
            ResultSet rs = sviUposleniUpit.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), LocalDate.parse(rs.getString(6)), LocalDate.parse(rs.getString(7)), rs.getBoolean(8));
                rezultat.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void dodajUposlenog(Employee employee) {
        try {
            ResultSet rs = odrediIdUposlenikaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            employee.setId(id);

            dodajUposlenogUpit.setInt(1, employee.getId());
            dodajUposlenogUpit.setString(2, employee.getFirstName());
            dodajUposlenogUpit.setString(3, employee.getLastName());
            dodajUposlenogUpit.setString(4, employee.getPassword());
            dodajUposlenogUpit.setString(5, employee.getUserName());
            dodajUposlenogUpit.setString(6, employee.getBirthDate().toString());
            dodajUposlenogUpit.setString(7, employee.getBirthDate().toString());
            dodajUposlenogUpit.setBoolean(8, employee.isAdmin());

            dodajUposlenogUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean zauzetoKorisnickoIme(String korisnickoIme, Employee employee) {
        try {
            dajUposlenogSaKorisnickimImenomUpit.setString(1, korisnickoIme);
            ResultSet resultSet = dajUposlenogSaKorisnickimImenomUpit.executeQuery();
            if(resultSet.next()) {
                if(employee == null) return false;
                if(resultSet.getInt(1) == employee.getId() && resultSet.getString(5).equals(employee.getUserName())) {
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
    public Employee dajUposlenogSaKorisnickimImenom(String korisnickoIme) {
        try {
            dajUposlenogSaKorisnickimImenomUpit.setString(1, korisnickoIme);
            ResultSet rs = dajUposlenogSaKorisnickimImenomUpit.executeQuery();
            if(rs.next()) {
                Employee employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), LocalDate.parse(rs.getString(6)), LocalDate.parse(rs.getString(7)), rs.getBoolean(8));
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void obrisiUposlenog(Employee employee) {
        try {
            obrisiUposlenog.setInt(1, employee.getId());
            obrisiUposlenog.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniUposlenog(Employee employee) {
        try {
            promijeniUposlenogUpit.setString(1, employee.getFirstName());
            promijeniUposlenogUpit.setString(2, employee.getLastName());
            promijeniUposlenogUpit.setString(3, employee.getPassword());
            promijeniUposlenogUpit.setString(4, employee.getUserName());
            promijeniUposlenogUpit.setString(5, employee.getBirthDate().toString());
            promijeniUposlenogUpit.setString(6, employee.getHireDate().toString());
            promijeniUposlenogUpit.setBoolean(7, employee.isAdmin());
            promijeniUposlenogUpit.setInt(8, employee.getId());
            promijeniUposlenogUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
