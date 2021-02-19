package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.TehnickiPregled;
import models.Uposlenik;
import models.Vozilo;
import services.UserSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TimTehnickiDAO extends BaseDAO{
    private PreparedStatement dajUposlenogSaKorisnickimImenomUpit, sviTehnickiUpit, brojTehnickihZaID, spojiTehnickiUposlenikUpit, brojTehnickihUpit, dajUposlenikeZaTPUpit, voziloUpit;

    @Override
    protected void kreirajUpite() {
        try {
            dajUposlenogSaKorisnickimImenomUpit = dbConnection.getSession().prepareStatement("SELECT * FROM uposlenik WHERE korisnicko_ime=?");
            sviTehnickiUpit = dbConnection.getSession().prepareStatement("SELECT * FROM tehnicki_pregled");
            spojiTehnickiUposlenikUpit = dbConnection.getSession().prepareStatement("INSERT INTO tim_tehnicki_pregled VALUES(?,?)");
            brojTehnickihZaID = dbConnection.getSession().prepareStatement("SELECT COUNT(*) FROM tim_tehnicki_pregled WHERE uposlenik_id=?");
            brojTehnickihUpit = dbConnection.getSession().prepareStatement("SELECT COUNT(*) FROM tim_tehnicki_pregled JOIN tehnicki_pregled ON tim_tehnicki_pregled.tehnicki_pregled_id = tehnicki_pregled.id WHERE tim_tehnicki_pregled.uposlenik_id=? AND tehnicki_pregled.status_tehnickog_pregleda=?");
            dajUposlenikeZaTPUpit = dbConnection.getSession().prepareStatement("SELECT * FROM uposlenik JOIN tim_tehnicki_pregled ON uposlenik.id = tim_tehnicki_pregled.uposlenik_id WHERE tim_tehnicki_pregled.tehnicki_pregled_id =?");
            voziloUpit = dbConnection.getSession().prepareStatement("SELECT * FROM vozilo WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //funkcije
    public int brojTehnickihZaKorisnika(int id) {
        try {
            brojTehnickihZaID.setInt(1, id);
            ResultSet resultSet = brojTehnickihZaID.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
            //System.out.println("Nema nista");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //broj kompletiranih
    public int brojKompletiranihPregleda(int uposleniID) {
        try {
            brojTehnickihUpit.setInt(1, uposleniID);
            brojTehnickihUpit.setString(2, "Kompletiran");
            ResultSet resultSet = brojTehnickihUpit.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
            //System.out.println("Nema nista");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //broj zakazanih
    public int brojZakazanihPregleda(int uposleniID) {
        try {
            brojTehnickihUpit.setInt(1, uposleniID);
            brojTehnickihUpit.setString(2, "Zakazan");
            ResultSet resultSet = brojTehnickihUpit.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
            //System.out.println("Nema nista");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //broj otkazanih
    public int brojOtkazanihPregleda(int uposleniID) {
        try {
            brojTehnickihUpit.setInt(1, uposleniID);
            brojTehnickihUpit.setString(2, "Otkazan");
            ResultSet resultSet = brojTehnickihUpit.executeQuery();
            if(resultSet.next()) {
                System.out.println("Broj je " + resultSet.getInt(1));
                return resultSet.getInt(1);
            }
            //System.out.println("Nema nista");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void spojiTehnickiUposlenik(int tehnickiID, int uposlenikID) {
        try {
            spojiTehnickiUposlenikUpit.setInt(1,tehnickiID);
            spojiTehnickiUposlenikUpit.setInt(2,uposlenikID);
            spojiTehnickiUposlenikUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<TehnickiPregled> sviTehnicki() {
        ObservableList<TehnickiPregled> tehnickiPregled = FXCollections.observableArrayList();
        try {
            ResultSet rs = sviTehnickiUpit.executeQuery();
            while (rs.next()) {
                TehnickiPregled tehnickiPregled1 = new TehnickiPregled(rs.getInt(1),  LocalDate.parse(rs.getString(2)), dajVozilo(rs.getInt(3)), rs.getInt(4), rs.getString(5), rs.getString(6));
                tehnickiPregled1.setUposlenici(dajUposlenike(tehnickiPregled1));
                System.out.println("Tehnicki je " + tehnickiPregled1);
                if(UserSession.getPrivileges() || tehnickiPregled1.getUposlenici().contains(dajUposlenogSaKorisnickimImenom(UserSession.getKorisnickoIme())))
                    tehnickiPregled.add(tehnickiPregled1);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        System.out.println("TP "  + tehnickiPregled.size());
        return tehnickiPregled;
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

    public Uposlenik dajUposlenogSaKorisnickimImenom(String korisnickoIme) {
        try {
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
