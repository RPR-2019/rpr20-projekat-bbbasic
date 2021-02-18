package services;

public final class UserSession {

    private static UserSession instance;
    private static String korisnickoIme;
    private static int ID;
    private static boolean privilegija;


    private UserSession(int ID, boolean privilegija) {
        this.ID = ID;
        this.privilegija = privilegija;
    }


    public static UserSession getInstace(int ID, boolean privilegija) {
        if(instance == null) {
            instance = new UserSession(ID, privilegija);
        }
        return instance;
    }

    public static int getID() {
        return ID;
    }

    public static boolean getPrivileges() {
        return privilegija;
    }

    public void cleanUserSession() {
        ID = -1;// or null
        privilegija = false;// or null
    }

}