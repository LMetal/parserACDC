package symbolTable;

import AST.LangType;

import java.util.HashMap;

public class SymbolTable {
    static HashMap<String, LangType> table;

    public static void init(){
        table = new HashMap<>();
    }


    /**
     * @param id NodeID name to insert in table
     * @param entry Attributes of id
     * @return true if new, false if already in use
     */
    public static boolean enter(String id, LangType entry){
        if(table.containsKey(id)) return false;

        //System.out.println("ADDING: " + id);
        table.put(id, entry);
        return true;
    }

    public static LangType lookup(String id){
        return table.get(id);
    }

    public static String toStr(){
        return table.toString();
    }

    public static int size(){
        return table.size();
    }
}
