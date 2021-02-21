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
    private Vozilo vozilo;
    private Klijent klijent;
    private VrstaTehnickogPregleda vrstaTehnickogPregleda;
    private StatusTehnickogPregleda statusTehnickogPregleda;
    private ArrayList<Uposlenik> uposlenici = new ArrayList<>();
    //dodano
    private String vrsta_motora, taktnost_motora, vrsta_goriva, vrsta_mjenjaca;
    private String komentar;
    private boolean ispravnost;
    private double cijena, sirina, duzina, visina;
    private int mjesta_za_sjesti, mjesta_za_stati, mjesta_za_leci;

    public TehnickiPregled() {
    }


    public TehnickiPregled(int id, LocalDate datumPregleda, Vozilo vozilo, Klijent klijent, String vrstaTehnickogPregleda, String statusTehnickogPregleda) {
            this.id = id;
            this.datumPregleda = datumPregleda;
            this.vozilo = vozilo;
            this.klijent = klijent;
            this.vrstaTehnickogPregleda = VrstaTehnickogPregleda.valueOf(vrstaTehnickogPregleda);
            this.statusTehnickogPregleda = StatusTehnickogPregleda.valueOf(statusTehnickogPregleda);
    }

    public int getMjesta_za_sjesti() {
        return mjesta_za_sjesti;
    }

    public void setMjesta_za_sjesti(int mjesta_za_sjesti) {
        this.mjesta_za_sjesti = mjesta_za_sjesti;
    }

    public int getMjesta_za_stati() {
        return mjesta_za_stati;
    }

    public void setMjesta_za_stati(int mjesta_za_stati) {
        this.mjesta_za_stati = mjesta_za_stati;
    }

    public int getMjesta_za_leci() {
        return mjesta_za_leci;
    }

    public void setMjesta_za_leci(int mjesta_za_leci) {
        this.mjesta_za_leci = mjesta_za_leci;
    }

    public double getSirina() {
        return sirina;
    }

    public void setSirina(double sirina) {
        this.sirina = sirina;
    }

    public double getDuzina() {
        return duzina;
    }

    public void setDuzina(double duzina) {
        this.duzina = duzina;
    }

    public double getVisina() {
        return visina;
    }

    public void setVisina(double visina) {
        this.visina = visina;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public boolean isIspravnost() {
        return ispravnost;
    }

    public void setIspravnost(boolean ispravnost) {
        this.ispravnost = ispravnost;
    }

    public double getCijena() {
        return cijena;
    }

    public void setCijena(double cijena) {
        this.cijena = cijena;
    }

    public String getVrsta_motora() {
        return vrsta_motora;
    }

    public void setVrsta_motora(String vrsta_motora) {
        this.vrsta_motora = vrsta_motora;
    }

    public String getTaktnost_motora() {
        return taktnost_motora;
    }

    public void setTaktnost_motora(String taktnost_motora) {
        this.taktnost_motora = taktnost_motora;
    }

    public String getVrsta_goriva() {
        return vrsta_goriva;
    }

    public void setVrsta_goriva(String vrsta_goriva) {
        this.vrsta_goriva = vrsta_goriva;
    }

    public String getVrsta_mjenjaca() {
        return vrsta_mjenjaca;
    }

    public void setVrsta_mjenjaca(String vrsta_mjenjaca) {
        this.vrsta_mjenjaca = vrsta_mjenjaca;
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


    public Vozilo getVozilo() {
        return vozilo;
    }

    public void setVozilo(Vozilo vozilo) {
        this.vozilo = vozilo;
    }

    public Klijent getKlijent() {
        return klijent;
    }

    public void setKlijent(Klijent klijent) {
        this.klijent = klijent;
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
                ", vozilo=" + vozilo +
                ", klijent=" + klijent +
                ", vrstaTehnickogPregleda=" + vrstaTehnickogPregleda +
                ", statusTehnickogPregleda=" + statusTehnickogPregleda +
                '}';
    }
}
