package models;

import java.util.Objects;

public class Klijent {
    	private int id;
    	private String ime;
    	private String prezime;
    	private String mjestoPrebivalista;
    	private String brojTelefona;

    public Klijent() {
    }

    public Klijent(int id, String ime, String prezime, String mjesto_prebivalista, String broj_telefona) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.mjestoPrebivalista = mjesto_prebivalista;
        this.brojTelefona = broj_telefona;
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

    public String getMjestoPrebivalista() {
        return mjestoPrebivalista;
    }

    public void setMjestoPrebivalista(String mjestoPrebivalista) {
        this.mjestoPrebivalista = mjestoPrebivalista;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    @Override
    public String toString() {
        return "Klijent{" +
                "ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", mjesto_prebivalista='" + mjestoPrebivalista + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Klijent klijent = (Klijent) o;
        return Objects.equals(ime, klijent.ime) &&
                Objects.equals(prezime, klijent.prezime) &&
                Objects.equals(mjestoPrebivalista, klijent.mjestoPrebivalista) &&
                Objects.equals(brojTelefona, klijent.brojTelefona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ime, prezime, mjestoPrebivalista, brojTelefona);
    }
}
