package services;

public final class UserSession {

    private static UserSession instance;
    private static String korisnickoIme;
    //private static int ID;
    private static boolean privilegija;


    private UserSession(String korisnickoIme, boolean privilegija) {
        this.korisnickoIme = korisnickoIme;
        this.privilegija = privilegija;
    }


    public static UserSession getInstace(String korisnickoIme, boolean privilegija) {
        if(instance == null) {
            instance = new UserSession(korisnickoIme, privilegija);
        }
        return instance;
    }

    public static String getKorisnickoIme() {
        return korisnickoIme;
    }

    public static boolean getPrivileges() {
        return privilegija;
    }

    public static void cleanUserSession() {
        korisnickoIme = "";
        privilegija = false;
        instance = null;
    }

}