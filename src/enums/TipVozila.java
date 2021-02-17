package enums;

public enum  TipVozila {
    Putnički (1),
    Autobus (2),
    Teretno (3);

    private final int tipVozila;

    TipVozila(int tipVozila) {
        this.tipVozila = tipVozila;
    }

    public int getTipVozila() {
        return tipVozila;
    }

}
