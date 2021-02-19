package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.TehnickiPregled;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TimTehnickiDAO extends BaseDAO{
    private PreparedStatement sviTehnickiUpit, brojTehnickihZaID, spojiTehnickiUposlenikUpit, brojTehnickihUpit;

    @Override
    protected void kreirajUpite() {
        try {
            sviTehnickiUpit = dbConnection.getSession().prepareStatement("SELECT * FROM tehnicki_pregled");
            spojiTehnickiUposlenikUpit = dbConnection.getSession().prepareStatement("INSERT INTO tim_tehnicki_pregled VALUES(?,?)");
            brojTehnickihZaID = dbConnection.getSession().prepareStatement("SELECT COUNT(*) FROM tim_tehnicki_pregled WHERE uposlenik_id=?");
            brojTehnickihUpit = dbConnection.getSession().prepareStatement("SELECT COUNT(*) FROM tim_tehnicki_pregled JOIN tehnicki_pregled ON tim_tehnicki_pregled.tehnicki_pregled_id = tehnicki_pregled.id WHERE tim_tehnicki_pregled.uposlenik_id=? AND tehnicki_pregled.status_tehnickog_pregleda=?");
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
            ResultSet resultSet = sviTehnickiUpit.executeQuery();
            while (resultSet.next()) {
                TehnickiPregled tehnickiPregled1 = new TehnickiPregled();
                tehnickiPregled.add(tehnickiPregled1);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return tehnickiPregled;
    }
}
