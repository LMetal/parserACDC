package symbolTable;

import AST.LangType;

import java.util.HashMap;

public class SymbolTable {
    public static class Attributes{
        private final LangType tipo;
        private final String nome;
        char registro;

        public Attributes(LangType type, String name) {
            this.tipo = type;
            this.nome = name;

        }

        public LangType getTipo(){
            return tipo;
        }

        public String getNome() {
            return nome;
        }
        //TODO ??
        public void setRegister(char registro){
            this.registro = registro;
        }

        public char getRegister() {
            return registro;
        }
    }
    static HashMap<String, Attributes> table;

    public static void init(){
        table = new HashMap<>();
    }


    /**
     * @param id NodeID name to insert in table
     * @param entry Attributes of id
     * @return true if new, false if already in use
     */
    public static boolean enter(String id, Attributes entry){
        if(table.containsKey(id)) return false;

        table.put(id, entry);
        return true;
    }

    public static Attributes lookup(String id){
        return table.get(id);
    }

    public static String toStr(){
        return table.toString();
    }

    public static int size(){
        return table.size();
    }
}
