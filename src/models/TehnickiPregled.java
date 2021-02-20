package models;

import enums.MarkaVozila;
import enums.StatusTehnickogPregleda;
import enums.VrstaTehnickogPregleda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class TehnickiPregled {
    private int id;
    private LocalDate datumPregleda;
    //private int voziloID;
    private Vozilo vozilo;
    private int klijentID;
    private VrstaTehnickogPregleda vrstaTehnickogPregleda;
    private StatusTehnickogPregleda statusTehnickogPregleda;
    private ArrayList<Uposlenik> uposlenici = new ArrayList<>();

    public TehnickiPregled() {
    }


    public TehnickiPregled(int id, LocalDate datumPregleda, Vozilo vozilo, int klijentID, String vrstaTehnickogPregleda, String statusTehnickogPregleda) {
            this.id = id;
            this.datumPregleda = datumPregleda;
            this.vozilo = vozilo;
            this.klijentID = klijentID;
            this.vrstaTehnickogPregleda = VrstaTehnickogPregleda.valueOf(vrstaTehnickogPregleda);
            this.statusTehnickogPregleda = StatusTehnickogPregleda.valueOf(statusTehnickogPregleda);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDatumPregleda() {
        return datumPregleda;
    }

    public void setDatumPregleda(LocalDate datumPregleda) {
        this.datumPregleda = datumPregleda;
    }

//    public int getVoziloID() {
//        return voziloID;
//    }
//
//    public void setVoziloID(int voziloID) {
//        this.voziloID = voziloID;
//    }


    public Vozilo getVozilo() {
        return vozilo;
    }

    public void setVozilo(Vozilo vozilo) {
        this.vozilo = vozilo;
    }

    public int getKlijentID() {
        return klijentID;
    }

    public void setKlijentID(int klijentID) {
        this.klijentID = klijentID;
    }


    public void setVrstaTehnickogPregleda(VrstaTehnickogPregleda vrstaTehnickogPregleda) {
        this.vrstaTehnickogPregleda = vrstaTehnickogPregleda;
    }

    public void setStatusTehnickogPregleda(StatusTehnickogPregleda statusTehnickogPregleda) {
        this.statusTehnickogPregleda = statusTehnickogPregleda;
    }

    public String getVrstaTehnickogPregleda() {
        return vrstaTehnickogPregleda.toString();
    }

    public void setVrstaTehnickogPregleda(String vrstaTehnickogPregleda) {
        this.vrstaTehnickogPregleda = VrstaTehnickogPregleda.valueOf(vrstaTehnickogPregleda);
    }

    public String getStatusTehnickogPregleda() {
        return statusTehnickogPregleda.toString();
    }

    public void setStatusTehnickogPregleda(String statusTehnickogPregleda) {
        this.statusTehnickogPregleda = StatusTehnickogPregleda.valueOf(statusTehnickogPregleda);
    }

    public ArrayList<Uposlenik> getUposlenici() {
        return uposlenici;
    }

    public void setUposlenici(ArrayList<Uposlenik> uposlenici) {
        this.uposlenici = uposlenici;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TehnickiPregled that = (TehnickiPregled) o;
//        return voziloID == that.voziloID && Objects.equals(datumPregleda, that.datumPregleda);
//
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(datumPregleda, voziloID);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TehnickiPregled that = (TehnickiPregled) o;
        return vozilo.getId() == that.vozilo.getId() && Objects.equals(datumPregleda, that.datumPregleda);

    }

    @Override
    public int hashCode() {
        return Objects.hash(datumPregleda, vozilo.getId());
    }

    @Override
    public String toString() {
        return "TehnickiPregled{" +
                "datumPregleda=" + datumPregleda +
                ", voziloID=" + vozilo +
                ", klijentID=" + klijentID +
                ", vrstaTehnickogPregleda=" + vrstaTehnickogPregleda +
                ", statusTehnickogPregleda=" + statusTehnickogPregleda +
                '}';
    }
}
