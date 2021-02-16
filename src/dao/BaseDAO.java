package dao;

public abstract class BaseDAO {
    protected DBConnection dbConnection;

    public BaseDAO() {
        dbConnection = DBConnection.getInstance();
        kreirajUpite();
    }

    protected abstract void kreirajUpite();
}
