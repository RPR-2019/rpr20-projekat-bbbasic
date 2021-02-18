package enums;

public enum VrstaTehnickogPregleda {
    Redovni (1),
    Preventivni (2),
    Vandredni (3);

    private final int vrstaTehnickogPregleda;

    VrstaTehnickogPregleda(int vrstaTehnickogPregleda) {
        this.vrstaTehnickogPregleda = vrstaTehnickogPregleda;
    }

    public int getTipVozila() {
        return vrstaTehnickogPregleda;
    }
}
