package dao;

import enums.TipVozila;
import exceptions.ZakazanTermin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Customer;
import models.Employee;
import models.TechnicalInspection;
import models.Vehicle;
import services.UserSession;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TehnickiPregledDAO extends BaseDAO{

    private PreparedStatement dajKlijentaUpit, dajUposlenikeZaTPUpit, otkaziTehnickiUpit,voziloUpit, sviTehnickiUpit, dodajTehnickiUpit, odrediIDTehnickogUpit, izmijeniTehnickiUpit, spojiTehnickiUposlenikUpit, dajUposlenogSaKorisnickimImenomUpit, brojTehnickihZaID;

    protected void kreirajUpite() {
        try {
            sviTehnickiUpit = dbConnection.getSession().prepareStatement("SELECT * FROM tehnicki_pregled");
            dodajTehnickiUpit = dbConnection.getSession().prepareStatement("INSERT INTO tehnicki_pregled VALUES(?,?,?,?,?,?,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL)");
            odrediIDTehnickogUpit = dbConnection.getSession().prepareStatement("SELECT MAX(id)+1 FROM tehnicki_pregled");
            voziloUpit = dbConnection.getSession().prepareStatement("SELECT * FROM vehicle WHERE id=?");
            otkaziTehnickiUpit = dbConnection.getSession().prepareStatement("UPDATE tehnicki_pregled SET status_tehnickog_pregleda=? WHERE id=?");
            dajUposlenikeZaTPUpit = dbConnection.getSession().prepareStatement("SELECT * FROM uposlenik JOIN tim_tehnicki_pregled ON uposlenik.id = tim_tehnicki_pregled.uposlenik_id WHERE tim_tehnicki_pregled.tehnicki_pregled_id =?");
            dajKlijentaUpit = dbConnection.getSession().prepareStatement("SELECT * FROM klijent WHERE id=?");
            dajUposlenogSaKorisnickimImenomUpit = dbConnection.getSession().prepareStatement("SELECT * FROM uposlenik WHERE korisnicko_ime=?");
            //ovdje ce puno trebat
            izmijeniTehnickiUpit = dbConnection.getSession().prepareStatement("UPDATE tehnicki_pregled" +
                    " SET status_tehnickog_pregleda=?, vrsta_motora=?, taktnost_motora=?, vrsta_goriva=?, vrsta_mjenjaca=?," +
                    "sirina=?, duzina=?, visina=?," +
                    "mjesta_za_sjesti=?, mjesta_za_stati=?, mjesta_za_lezati=?," +
                    "komentar=?, ispravnost=?, cijena=?" +
                    "WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //funkcije

    public ArrayList<TechnicalInspection> tehnicki() {
        ArrayList<TechnicalInspection> rezultat = new ArrayList();
        try {
            ResultSet rs = sviTehnickiUpit.executeQuery();
            while (rs.next()) {
                TechnicalInspection technicalInspection = new TechnicalInspection(rs.getInt(1),  LocalDate.parse(rs.getString(2)), dajVozilo(rs.getInt(3)), dajKlijenta(rs.getInt(4)), rs.getString(5), rs.getString(6));
                rezultat.add(technicalInspection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    private Customer dajKlijenta(int id) {
        try {
            dajKlijentaUpit.setInt(1, id);
            ResultSet rs = dajKlijentaUpit.executeQuery();
            while (rs.next()) {
                Customer klijent = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                return klijent;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void dodajTehnicki(TechnicalInspection technicalInspection) throws ZakazanTermin {
        try {
            if(postojiZakazanTehnicki(technicalInspection)) return;
            ResultSet rs = odrediIDTehnickogUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            technicalInspection.setId(id);

            dodajTehnickiUpit.setInt(1, technicalInspection.getId());
            dodajTehnickiUpit.setString(2, technicalInspection.getDateOfInspection().toString());
            dodajTehnickiUpit.setInt(3, technicalInspection.getVozilo().getId());
            dodajTehnickiUpit.setInt(4, technicalInspection.getCustomer().getId());
            dodajTehnickiUpit.setString(5, technicalInspection.getTypeOfTechnicalInspection());
            dodajTehnickiUpit.setString(6, technicalInspection.getStatusOfTechnicalInspection());


            dodajTehnickiUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean postojiZakazanTehnicki(TechnicalInspection technicalInspection) throws ZakazanTermin {
        ArrayList<TechnicalInspection> rezultat = new ArrayList();
        try {
            ResultSet rs = sviTehnickiUpit.executeQuery();
            while (rs.next()) {
                TechnicalInspection technicalInspection1 = new TechnicalInspection(rs.getInt(1),  LocalDate.parse(rs.getString(2)), dajVozilo(rs.getInt(3)), dajKlijenta(rs.getInt(4)), rs.getString(5), rs.getString(6));
                if(technicalInspection.equals(technicalInspection1)) {
                    throw new ZakazanTermin("Vec zakazan termin");
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void izmijeniTehnicki(TechnicalInspection technicalInspection) {
        try {
            izmijeniTehnickiUpit.setString(1, technicalInspection.getStatusOfTechnicalInspection());
            izmijeniTehnickiUpit.setString(2, technicalInspection.getEngineType());
            izmijeniTehnickiUpit.setString(3, technicalInspection.getEngineTact());
            izmijeniTehnickiUpit.setString(4, technicalInspection.getTypeOfFuel());
            izmijeniTehnickiUpit.setString(5, technicalInspection.getTypeOfGearbox());

            izmijeniTehnickiUpit.setDouble(6, technicalInspection.getWidth());
            izmijeniTehnickiUpit.setDouble(7, technicalInspection.getLength());
            izmijeniTehnickiUpit.setDouble(8, technicalInspection.getHeight());

            izmijeniTehnickiUpit.setInt(9, technicalInspection.getPlacesToSit());
            izmijeniTehnickiUpit.setInt(10, technicalInspection.getPlacesToStand());
            izmijeniTehnickiUpit.setInt(11, technicalInspection.getPlacesToLieDown());

            izmijeniTehnickiUpit.setString(12, technicalInspection.getComment());
            izmijeniTehnickiUpit.setBoolean(13, technicalInspection.isValid());
            izmijeniTehnickiUpit.setDouble(14, technicalInspection.getPrice());
            //zadnji
            izmijeniTehnickiUpit.setInt(15, technicalInspection.getId());

            izmijeniTehnickiUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vehicle dajVozilo(int id) {
        try {
            voziloUpit.setInt(1, id);
            ResultSet rs = voziloUpit.executeQuery();
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6),rs.getString(7),rs.getString(8), rs.getString(9));
                return vehicle;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void otkaziTehnickiPregled(TechnicalInspection technicalInspection) {

        try {
            otkaziTehnickiUpit.setString(1,"Otkazan");
            otkaziTehnickiUpit.setInt(2, technicalInspection.getId());
            otkaziTehnickiUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<TechnicalInspection> pretraga(Customer klijent, TipVozila tipVozila, LocalDate localDate) {
        ObservableList<TechnicalInspection> technicalInspection = FXCollections.observableArrayList();
        try {
            ResultSet rs = sviTehnickiUpit.executeQuery();
            while (rs.next()) {
                TechnicalInspection technicalInspection1 = new TechnicalInspection(rs.getInt(1),  LocalDate.parse(rs.getString(2)), dajVozilo(rs.getInt(3)), dajKlijenta(rs.getInt(4)), rs.getString(5), rs.getString(6));
                technicalInspection1.setEmployees(dajUposlenike(technicalInspection1));
                if(UserSession.getPrivileges() || technicalInspection1.getEmployees().contains(dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme())))
                    technicalInspection.add(technicalInspection1);
            }
            if(klijent == null && tipVozila == null && localDate == null) return technicalInspection;
            //klijjent
            if(klijent != null && tipVozila == null && localDate == null)
                return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getCustomer().getId() == klijent.getId())
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
            //tipvozila
            if(klijent == null && tipVozila != null && localDate == null)
                return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getVozilo().getType().equals(tipVozila.name()))
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
            //datum
            if(klijent == null && tipVozila == null && localDate != null)
                return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getDateOfInspection().toString().equals(localDate.toString()))
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
            //klijent + tip vozila
            if(klijent != null && tipVozila != null && localDate == null)
                return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getVozilo().getType().equals(tipVozila.name()) && tehnickiPregled1.getCustomer().getId() == klijent.getId())
                    .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

            //klijent + datum
            if(klijent != null && tipVozila == null && localDate != null)
                return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getDateOfInspection()
                        .toString()
                        .equals(localDate.toString()) && tehnickiPregled1.getCustomer().getId() == klijent.getId())
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

            //tip vozila + datum
            if(klijent == null && tipVozila != null && localDate != null)
                return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getDateOfInspection()
                        .toString()
                        .equals(localDate.toString()) && tehnickiPregled1.getVozilo().getType().equals(tipVozila.name()))
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));


            //svi
            return technicalInspection.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getDateOfInspection()
                    .toString()
                    .equals(localDate.toString()) && tehnickiPregled1.getVozilo().getType().equals(tipVozila.name()) && tehnickiPregled1.getCustomer().getId() == klijent.getId())
                    .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    private ArrayList<Employee> dajUposlenike(TechnicalInspection technicalInspection) {
        try {
            ArrayList<Employee> uposlenici = new ArrayList<>();
            dajUposlenikeZaTPUpit.setInt(1, technicalInspection.getId());
            ResultSet rs = dajUposlenikeZaTPUpit.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), LocalDate.parse(rs.getString(6)), LocalDate.parse(rs.getString(7)), rs.getBoolean(8));
                uposlenici.add(employee);
            }
            return uposlenici;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        public Employee dajUposlenogSaKorisnickimImenom(String korisnickoIme) {
        try {
            System.out.println("Korisnicko ime je " + korisnickoIme);
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

    public void zapisiDatoteku(File izabrani, ObservableList<TechnicalInspection> technicalInspection) {
        if(izabrani != null) {
            try {
                PrintWriter izlaz;
                izlaz = new PrintWriter(new FileWriter(izabrani));
                for (int i = 0; i < technicalInspection.size(); i++) {
                    izlaz.println(technicalInspection.get(i).getDateOfInspection() + ":"
                            + technicalInspection.get(i).getCustomer().getFirstName() + ":"
                            + technicalInspection.get(i).getCustomer().getLastName() + ":"
                            + technicalInspection.get(i).getVozilo().getType() + ":"
                            + technicalInspection.get(i).getVozilo().getBrand() + ":"
                            + technicalInspection.get(i).getVozilo().getModel() + ":"
                            + technicalInspection.get(i).getStatusOfTechnicalInspection() + ":"
                            + technicalInspection.get(i).getEmployees());
                }
                izlaz.close();
            } catch (IOException e) {
                System.out.println("Greska prilikom ucitavanja");
            }
        }
    }
}
