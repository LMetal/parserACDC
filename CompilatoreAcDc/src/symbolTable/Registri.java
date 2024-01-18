package symbolTable;

import java.util.ArrayList;

public class Registri {
    static private ArrayList<Character> registri;

    public static void init(){
        registri = new ArrayList<>();
        registri.add('a');
        registri.add('b');
        registri.add('c');
        registri.add('d');
    }

    public static char newRegister(){
        return registri.remove(0);
    }

}
