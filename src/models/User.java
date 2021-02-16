package models;

import java.time.LocalDate;
import java.util.Date;

public class User {
    private int id;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    private String datumRodjenja;
    private String datumZaposlenja;
    private boolean pristup = false;

    public User(int id, String ime, String prezime, String lozinka, String korisnickoIme, String datumRodjenja, String datumZaposlenja, boolean pristup) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.datumRodjenja = datumRodjenja;
        this.datumZaposlenja = datumZaposlenja;
        this.pristup = pristup;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(String datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getDatumZaposlenja() {
        return datumZaposlenja;
    }

    public void setDatumZaposlenja(String datumZaposlenja) {
        this.datumZaposlenja = datumZaposlenja;
    }

    public boolean isPristup() {
        return pristup;
    }

    public void setPristup(boolean pristup) {
        this.pristup = pristup;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }
}
