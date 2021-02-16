package models;

import java.sql.Date;
import java.time.LocalDate;

public class Uposlenik {
    private int id;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    private Date datumRodjenja;
    private Date datumZaposlenja;
    private boolean pristup = false;

    public Uposlenik() {
    }

    public Uposlenik(int id, String ime, String prezime, String lozinka, String korisnickoIme, Date datumRodjenja, Date datumZaposlenja, boolean pristup) {
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

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public Date getDatumZaposlenja() {
        return datumZaposlenja;
    }

    public void setDatumZaposlenja(Date datumZaposlenja) {
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
        return ime + " " + prezime + " " + datumZaposlenja.toLocalDate();
    }
}
