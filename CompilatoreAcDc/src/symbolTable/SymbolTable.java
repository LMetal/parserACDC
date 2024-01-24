package symbolTable;

import AST.LangType;

import java.util.HashMap;

/**
 * Classe che implementa la symbol table, statico perche' una sola e' necessaria
 */
public class SymbolTable {
    /**
     * Rappresenta gli attributi di un id
     */
    public static class Attributes{
        /**
         * Tipo dell'id
         */
        private final LangType tipo;
        /**
         * Nome dell'id
         */
        private final String nome;
        /**
         * Registro associato all'id
         */
        char registro;

        /**
         * Costruttore dell'oggetto attribute
         *
         * @param type Tipo dell'id
         * @param name Nome dell'id
         */
        public Attributes(LangType type, String name) {
            this.tipo = type;
            this.nome = name;

        }

        /**
         * @return Il tipo dell'id
         */
        public LangType getTipo(){
            return tipo;
        }

        /**
         * @return Il nome dell'id
         */
        public String getNome() {
            return nome;
        }

        /**
         * Associa un registro a un id
         *
         * @param registro Il registro da associare all'id
         */
        public void setRegister(char registro){
            this.registro = registro;
        }

        /**
         * @return Il registro associato
         */
        public char getRegister() {
            return registro;
        }
    }

    /**
     * HashMap che associa il nome di un id a degli attributi
     */
    static HashMap<String, Attributes> table;

    /**
     * Inizializza l'HashMap
     */
    public static void init(){
        table = new HashMap<>();
    }


    /**
     * @param id Nome NodeId da inserire della SymbolTable
     * @param entry attributi dell'id
     * @return True se id e' nuovo, altrimenti false
     */
    public static boolean enter(String id, Attributes entry){
        if(table.containsKey(id)) return false;

        table.put(id, entry);
        return true;
    }

    /**
     * Ritorna gli attributi associati a un dato id, se non presente ritorna null.
     *
     * @param id Nome dell'id di cui ritornare gli attributi
     * @return un oggetto contenente gli attributi dell'id se presente, altrimenti null
     */
    public static Attributes lookup(String id){
        return table.get(id);
    }

    /**
     * @return Una rappresentazione come stringa della tabella
     */
    public static String toStr(){
        return table.toString();
    }

    /**
     * Ritorna il numero di id nella tabella
     *
     * @return Il numero di entry nella tabella
     */
    public static int size(){
        return table.size();
    }
}
