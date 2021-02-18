package enums;

public enum StatusTehnickogPregleda {
    Zakazan (1),
    Kompletiran (2),
    Otkazan (3);

    private final int statusTehnickogPregleda;

    StatusTehnickogPregleda(int statusTehnickogPregleda) {
        this.statusTehnickogPregleda = statusTehnickogPregleda;
    }

    public int getTipVozila() {
        return statusTehnickogPregleda;
    }
}
