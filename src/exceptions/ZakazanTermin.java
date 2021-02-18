package exceptions;

public class ZakazanTermin extends Throwable {
    public ZakazanTermin(String vec_zakazan_termin) {
        super(vec_zakazan_termin);
    }
}
