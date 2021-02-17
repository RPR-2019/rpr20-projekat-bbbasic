package constants;

import enums.MarkaVozila;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelVozila {

    public static HashMap<MarkaVozila, ArrayList<String>> listaModelaPremaMarki = new HashMap<>()
    {{
        put(MarkaVozila.Suzuki, new ArrayList<>() {{
            add("Vitara");
            add("Ignis");
        }});
        put(MarkaVozila.Fiat, new ArrayList<>() {{
            add("Panda");
            add("Coupe");
        }});
        put(MarkaVozila.Volvo, new ArrayList<>() {{
            add("XC40");
            add("S60");
        }});
        put(MarkaVozila.Volkswagen, new ArrayList<>() {{
            add("Polo");
            add("Touareg");
        }});
    }};

}
// ModelVozila.listaModelaPremaTipu.get(KLJUC_OD_VOZILA)