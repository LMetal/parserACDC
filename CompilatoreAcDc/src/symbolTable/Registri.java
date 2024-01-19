package symbolTable;

import java.util.ArrayList;
import java.util.Arrays;

public class Registri {
    static private ArrayList<Character> registri;

    public static void init() {
        registri = new ArrayList<>(Arrays.asList(
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        ));
    }

    public static Character newRegister(){
        if(registri.isEmpty()) return null;
        else return registri.remove(0);
    }

}
