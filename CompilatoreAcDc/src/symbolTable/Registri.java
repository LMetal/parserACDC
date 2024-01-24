package symbolTable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe statica che descrive i 52 registri presenti in DC
 */
public class Registri {
    /**
     * Lista dei registri disponibili
     */
    static private ArrayList<Character> registri;

    /**
     * Inizializza i registri disponibili
     */
    public static void init() {
        registri = new ArrayList<>(Arrays.asList(
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        ));
    }

    /**
     * Rimuove il primo elemento nella lista e lo ritorna
     *
     * @return Il primo registro disponibile
     */
    public static Character newRegister(){
        if(registri.isEmpty()) return null;
        else return registri.remove(0);
    }

}
