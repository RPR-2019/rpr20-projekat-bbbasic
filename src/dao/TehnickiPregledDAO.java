package dao;

import exceptions.ZakazanTermin;
import javafx.scene.layout.Border;
import models.Klijent;
import models.TehnickiPregled;
import models.Uposlenik;
import models.Vozilo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TehnickiPregledDAO extends BaseDAO{

    private PreparedStatement otkaziTehnickiUpit,voziloUpit, sviTehnickiUpit, dodajTehnickiUpit, odrediIDTehnickogUpit, izmijeniTehnickiUpit, spojiTehnickiUposlenikUpit, dajUposlenogSaKorisnickimImenomUpit, brojTehnickihZaID;

    protected void kreirajUpite() {
        try {
            sviTehnickiUpit = dbConnection.getSession().prepareStatement("SELECT * FROM tehnicki_pregled");
            dodajTehnickiUpit = dbConnection.getSession().prepareStatement("INSERT INTO tehnicki_pregled VALUES(?,?,?,?,?,?)");
            odrediIDTehnickogUpit = dbConnection.getSession().prepareStatement("SELECT MAX(id)+1 FROM tehnicki_pregled");
            voziloUpit = dbConnection.getSession().prepareStatement("SELECT * FROM vozilo WHERE id=?");
            otkaziTehnickiUpit = dbConnection.getSession().prepareStatement("UPDATE tehnicki_pregled SET status_tehnickog_pregleda=? WHERE id=?");
            //spojiTehnickiUposlenikUpit = dbConnection.getSession().prepareStatement("INSERT INTO tim_tehnicki_pregled VALUES(?,?)");
            //brojTehnickihZaID = dbConnection.getSession().prepareStatement("SELECT COUNT(*) FROM tim_tehnicki_pregled WHERE uposlenik_id=?");
            //ovdje ce puno trebat
            //izmijeniTehnickiUpit = dbConnection.getSession().prepareStatement("UPDATE uposlenik SET ime=?, prezime=?, lozinka=?, korisnicko_ime=?, datum_rodjenja=?, datum_uposlenja=? WHERE id=?");
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
                TehnickiPregled tehnickiPregled = new TehnickiPregled(rs.getInt(1),  LocalDate.parse(rs.getString(2)), dajVozilo(rs.getInt(3)), rs.getInt(4), rs.getString(5), rs.getString(6));
                rezultat.add(tehnickiPregled);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
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
            dodajTehnickiUpit.setInt(4, tehnickiPregled.getKlijentID());
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
                TehnickiPregled tehnickiPregled1 = new TehnickiPregled(rs.getInt(1),  LocalDate.parse(rs.getString(2)), dajVozilo(rs.getInt(3)), rs.getInt(4), rs.getString(5), rs.getString(6));
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

//    public void spojiTehnickiUposlenik(int tehnickiID, int uposlenikID) {
//        try {
//            spojiTehnickiUposlenikUpit.setInt(1,tehnickiID);
//            spojiTehnickiUposlenikUpit.setInt(2,uposlenikID);
//            spojiTehnickiUposlenikUpit.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void izmijeniTehnicki(TehnickiPregled tehnickiPregled) {
        System.out.println("Trebat ce mijenjat ako se stavi u kompletiran ili otkazan");
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

//    public int brojTehnickihZaKorisnika(int id) {
//        try {
//            brojTehnickihZaID.setInt(1, id);
//            ResultSet resultSet = brojTehnickihZaID.executeQuery();
//            if(resultSet.next()) {
//                return resultSet.getInt(1);
//            }
//            System.out.println("Nema nista");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
}
