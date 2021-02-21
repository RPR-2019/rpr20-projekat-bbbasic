package dao;

import enums.TipVozila;
import exceptions.ZakazanTermin;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Border;
import models.Klijent;
import models.TehnickiPregled;
import models.Uposlenik;
import models.Vozilo;
import services.UserSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

public class TehnickiPregledDAO extends BaseDAO{

    private PreparedStatement dajKlijentaUpit, dajUposlenikeZaTPUpit, otkaziTehnickiUpit,voziloUpit, sviTehnickiUpit, dodajTehnickiUpit, odrediIDTehnickogUpit, izmijeniTehnickiUpit, spojiTehnickiUposlenikUpit, dajUposlenogSaKorisnickimImenomUpit, brojTehnickihZaID;

    protected void kreirajUpite() {
        try {
            sviTehnickiUpit = dbConnection.getSession().prepareStatement("SELECT * FROM tehnicki_pregled");
            dodajTehnickiUpit = dbConnection.getSession().prepareStatement("INSERT INTO tehnicki_pregled VALUES(?,?,?,?,?,?,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL)");
            odrediIDTehnickogUpit = dbConnection.getSession().prepareStatement("SELECT MAX(id)+1 FROM tehnicki_pregled");
            voziloUpit = dbConnection.getSession().prepareStatement("SELECT * FROM vozilo WHERE id=?");
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

    public ArrayList<TehnickiPregled> tehnicki() {
        ArrayList<TehnickiPregled> rezultat = new ArrayList();
        try {
            ResultSet rs = sviTehnickiUpit.executeQuery();
            while (rs.next()) {
                TehnickiPregled tehnickiPregled = new TehnickiPregled(rs.getInt(1),  LocalDate.parse(rs.getString(2)), dajVozilo(rs.getInt(3)), dajKlijenta(rs.getInt(4)), rs.getString(5), rs.getString(6));
                rezultat.add(tehnickiPregled);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    private Klijent dajKlijenta(int id) {
        try {
            dajKlijentaUpit.setInt(1, id);
            ResultSet rs = dajKlijentaUpit.executeQuery();
            while (rs.next()) {
                Klijent klijent = new Klijent(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                return klijent;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void dodajTehnicki(TehnickiPregled tehnickiPregled) throws ZakazanTermin {
        try {
            if(postojiZakazanTehnicki(tehnickiPregled)) return;
            ResultSet rs = odrediIDTehnickogUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            tehnickiPregled.setId(id);

            dodajTehnickiUpit.setInt(1, tehnickiPregled.getId());
            dodajTehnickiUpit.setString(2, tehnickiPregled.getDatumPregleda().toString());
            dodajTehnickiUpit.setInt(3, tehnickiPregled.getVozilo().getId());
            dodajTehnickiUpit.setInt(4, tehnickiPregled.getKlijent().getId());
            dodajTehnickiUpit.setString(5, tehnickiPregled.getVrstaTehnickogPregleda());
            dodajTehnickiUpit.setString(6, tehnickiPregled.getStatusTehnickogPregleda());


            dodajTehnickiUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean postojiZakazanTehnicki(TehnickiPregled tehnickiPregled) throws ZakazanTermin {
        ArrayList<TehnickiPregled> rezultat = new ArrayList();
        try {
            ResultSet rs = sviTehnickiUpit.executeQuery();
            while (rs.next()) {
                TehnickiPregled tehnickiPregled1 = new TehnickiPregled(rs.getInt(1),  LocalDate.parse(rs.getString(2)), dajVozilo(rs.getInt(3)), dajKlijenta(rs.getInt(4)), rs.getString(5), rs.getString(6));
                if(tehnickiPregled.equals(tehnickiPregled1)) {
                    throw new ZakazanTermin("Vec zakazan termin");
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void izmijeniTehnicki(TehnickiPregled tehnickiPregled) {
        try {
            izmijeniTehnickiUpit.setString(1,tehnickiPregled.getStatusTehnickogPregleda());
            izmijeniTehnickiUpit.setString(2,tehnickiPregled.getVrsta_motora());
            izmijeniTehnickiUpit.setString(3,tehnickiPregled.getTaktnost_motora());
            izmijeniTehnickiUpit.setString(4,tehnickiPregled.getVrsta_goriva());
            izmijeniTehnickiUpit.setString(5,tehnickiPregled.getVrsta_mjenjaca());

            izmijeniTehnickiUpit.setDouble(6,tehnickiPregled.getSirina());
            izmijeniTehnickiUpit.setDouble(7,tehnickiPregled.getDuzina());
            izmijeniTehnickiUpit.setDouble(8,tehnickiPregled.getVisina());

            izmijeniTehnickiUpit.setInt(9,tehnickiPregled.getMjesta_za_sjesti());
            izmijeniTehnickiUpit.setInt(10,tehnickiPregled.getMjesta_za_stati());
            izmijeniTehnickiUpit.setInt(11,tehnickiPregled.getMjesta_za_leci());

            izmijeniTehnickiUpit.setString(12,tehnickiPregled.getKomentar());
            izmijeniTehnickiUpit.setBoolean(13,tehnickiPregled.isIspravnost());
            izmijeniTehnickiUpit.setDouble(14,tehnickiPregled.getCijena());
            //zadnji
            izmijeniTehnickiUpit.setInt(15,tehnickiPregled.getId());

            izmijeniTehnickiUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vozilo dajVozilo(int id) {
        try {
            voziloUpit.setInt(1, id);
            ResultSet rs = voziloUpit.executeQuery();
            while (rs.next()) {
                Vozilo vozilo = new Vozilo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6),rs.getString(7),rs.getString(8), rs.getString(9));
                return vozilo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void otkaziTehnickiPregled(TehnickiPregled tehnickiPregled) {

        try {
            otkaziTehnickiUpit.setString(1,"Otkazan");
            otkaziTehnickiUpit.setInt(2, tehnickiPregled.getId());
            otkaziTehnickiUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<TehnickiPregled> pretraga(Klijent klijent, TipVozila tipVozila, LocalDate localDate) {
        ObservableList<TehnickiPregled> tehnickiPregled = FXCollections.observableArrayList();
        try {
            ResultSet rs = sviTehnickiUpit.executeQuery();
            while (rs.next()) {
                TehnickiPregled tehnickiPregled1 = new TehnickiPregled(rs.getInt(1),  LocalDate.parse(rs.getString(2)), dajVozilo(rs.getInt(3)), dajKlijenta(rs.getInt(4)), rs.getString(5), rs.getString(6));
                tehnickiPregled1.setUposlenici(dajUposlenike(tehnickiPregled1));
                if(UserSession.getPrivileges() || tehnickiPregled1.getUposlenici().contains(dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme())))
                    tehnickiPregled.add(tehnickiPregled1);
            }
            if(klijent == null && tipVozila == null && localDate == null) return tehnickiPregled;
            //klijjent
            if(klijent != null && tipVozila == null && localDate == null)
                return tehnickiPregled.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getKlijent().getId() == klijent.getId())
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
            //tipvozila
            if(klijent == null && tipVozila != null && localDate == null)
                return tehnickiPregled.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getVozilo().getTipVozila().equals(tipVozila.name()))
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
            //datum
            if(klijent == null && tipVozila == null && localDate != null)
                return tehnickiPregled.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getDatumPregleda().toString().equals(localDate.toString()))
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
            //klijent + tip vozila
            if(klijent != null && tipVozila != null && localDate == null)
                return tehnickiPregled.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getVozilo().getTipVozila().equals(tipVozila.name()) && tehnickiPregled1.getKlijent().getId() == klijent.getId())
                    .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

            //klijent + datum
            if(klijent != null && tipVozila == null && localDate != null)
                return tehnickiPregled.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getDatumPregleda()
                        .toString()
                        .equals(localDate.toString()) && tehnickiPregled1.getKlijent().getId() == klijent.getId())
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

            //tip vozila + datum
            if(klijent == null && tipVozila != null && localDate != null)
                return tehnickiPregled.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getDatumPregleda()
                        .toString()
                        .equals(localDate.toString()) && tehnickiPregled1.getVozilo().getTipVozila().equals(tipVozila.name()))
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));


            //svi
            return tehnickiPregled.stream().filter(tehnickiPregled1 -> tehnickiPregled1.getDatumPregleda()
                    .toString()
                    .equals(localDate.toString()) && tehnickiPregled1.getVozilo().getTipVozila().equals(tipVozila.name()) && tehnickiPregled1.getKlijent().getId() == klijent.getId())
                    .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    private ArrayList<Uposlenik> dajUposlenike(TehnickiPregled tehnickiPregled) {
        try {
            ArrayList<Uposlenik> uposlenici = new ArrayList<>();
            dajUposlenikeZaTPUpit.setInt(1,tehnickiPregled.getId());
            ResultSet rs = dajUposlenikeZaTPUpit.executeQuery();
            while (rs.next()) {
                Uposlenik uposlenik = new Uposlenik(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), LocalDate.parse(rs.getString(6)), LocalDate.parse(rs.getString(7)), rs.getBoolean(8));
                uposlenici.add(uposlenik);
            }
            return uposlenici;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        public Uposlenik dajUposlenogSaKorisnickimImenom(String korisnickoIme) {
        try {
            System.out.println("Korisnicko ime je " + korisnickoIme);
            dajUposlenogSaKorisnickimImenomUpit.setString(1, korisnickoIme);
            ResultSet rs = dajUposlenogSaKorisnickimImenomUpit.executeQuery();
            if(rs.next()) {
                Uposlenik uposlenik = new Uposlenik(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), LocalDate.parse(rs.getString(6)), LocalDate.parse(rs.getString(7)), rs.getBoolean(8));
                return uposlenik;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
