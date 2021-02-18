package models;

import enums.MarkaVozila;
import enums.TipVozila;

public class Vozilo {
    private int id;
    private TipVozila tipVozila;
    private MarkaVozila marka;
    private String model;
    private int godinaProizvodnje;
    private String registracija;
    private String brojsasije;
    private String boja;
    private String vrstaBoje;

    public Vozilo() {
    }

    public Vozilo(int id, String tipVozila, String marka, String model, int godinaProizvodnje, String registracija, String brojsasije, String boja, String vrstaBoje) {
        this.id = id;
        this.tipVozila = TipVozila.valueOf(tipVozila);
        this.marka = MarkaVozila.valueOf(marka);
        this.godinaProizvodnje = godinaProizvodnje;
        this.model = model;
        this.registracija = registracija;
        this.brojsasije = brojsasije;
        this.boja = boja;
        this.vrstaBoje = vrstaBoje;
    }

    public String getVrstaBoje() {
        return vrstaBoje;
    }

    public void setVrstaBoje(String vrstaBoje) {
        this.vrstaBoje = vrstaBoje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipVozila() {
        return tipVozila.toString();
    }

    public void setTipVozila(String tipVozila) {
        this.tipVozila = TipVozila.valueOf(tipVozila);
    }

    public String getMarka() {
        return marka.toString();
    }

    public void setMarka(String marka) {
        this.marka = MarkaVozila.valueOf(marka);
    }

    public int getGodinaProizvodnje() {
        return godinaProizvodnje;
    }

    public void setGodinaProizvodnje(int godinaProizvodnje) {
        this.godinaProizvodnje = godinaProizvodnje;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistracija() {
        return registracija;
    }

    public void setRegistracija(String registracija) {
        this.registracija = registracija;
    }

    public String getBrojsasije() {
        return brojsasije;
    }

    public void setBrojsasije(String brojsasije) {
        this.brojsasije = brojsasije;
    }

    public String getBoja() {
        return boja;
    }

    public void setBoja(String boja) {
        this.boja = boja;
    }

    @Override
    public String toString() {
        return tipVozila + ", " + marka + ", " + model + ", " + registracija;
    }
}
