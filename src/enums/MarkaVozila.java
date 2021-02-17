package enums;

public enum MarkaVozila {
    Suzuki (1),
    Fiat (2),
    Volvo (3),
    Volkswagen(4);

    private final int markaVozila;

    MarkaVozila(int markaVozila) {
        this.markaVozila = markaVozila;
    }

    public int getModelVozila() {
        return markaVozila;
    }
}