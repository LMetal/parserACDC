package AST;

/**
 * Enumerator contenente le operazioni possibili in ac
 * E' definito un metodo toString che ritorna in carattere associato all'operazione
 */
public enum LangOper {
    /**
     * Operazione addizione
     */
    PLUS,
    /**
     * Operazione sottrazione
     */
    MINUS,
    /**
     * Operazione moltiplicazione
     */
    MULTIP,
    /**
     * Opèrazione divisione
     */
    DIVISION;

    /**
     * Ritorna l'operatore in forma di singolo carattere
     *
     * @return Carattere assegnato al valore
     */
    @Override
    public String toString(){
        return switch (this) {
            case PLUS -> "+";
            case MINUS -> "-";
            case MULTIP -> "*";
            case DIVISION -> "/";
        };
    }
}
