package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TimTehnickiDAO extends BaseDAO{
    private PreparedStatement spojiTehnickiUposlenikUpit, brojTehnickihUpit, brojTehnickihZaID, dajUposlenikeZaTPUpit;

    @Override
    protected void kreirajUpite() {
        try {
            spojiTehnickiUposlenikUpit = dbConnection.getSession().prepareStatement("INSERT INTO tim_tehnicki_pregled VALUES(?,?)");
            brojTehnickihZaID = dbConnection.getSession().prepareStatement("SELECT COUNT(*) FROM tim_tehnicki_pregled WHERE uposlenik_id=?");
            brojTehnickihUpit = dbConnection.getSession().prepareStatement("SELECT COUNT(*) FROM tim_tehnicki_pregled JOIN tehnicki_pregled ON tim_tehnicki_pregled.tehnicki_pregled_id = tehnicki_pregled.id WHERE tim_tehnicki_pregled.uposlenik_id=? AND tehnicki_pregled.status_tehnickog_pregleda=?");
            dajUposlenikeZaTPUpit = dbConnection.getSession().prepareStatement("SELECT * FROM uposlenik JOIN tim_tehnicki_pregled ON uposlenik.id = tim_tehnicki_pregled.uposlenik_id WHERE tim_tehnicki_pregled.tehnicki_pregled_id =?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                return resultSet.getInt(1);
            }

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

}
