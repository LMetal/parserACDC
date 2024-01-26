package AST;

import visitor.IVisitor;

/**
 * Rappresenta un nodo nell'AST che corrisponde a una dichiarazione
 */
public class NodeDecl extends NodeDecSt {
    /**
     * Identificatore della variabile dichiarata
     */
    private final NodeId id;

    /**
     * Tipo della variabile dichiarata
     */
    private final LangType type;

    /**
     * Espressione di inizializzazione della variabile (può essere null se non è presente un'inizializzazione)
     */
    private final NodeExpr init;

    /**
     * Costruisce un nuovo nodo NodeDecl con il tipo, l'identificatore e l'espressione di inizializzazione forniti
     *
     * @param ty   Il tipo della variabile
     * @param id   L'identificatore della variabile dichiarata
     * @param init L'espressione di inizializzazione della variabile (può essere null)
     */
    public NodeDecl(LangType ty, NodeId id, NodeExpr init) {
        this.id = id;
        this.type = ty;
        this.init = init;
    }

    /**
     * Ritorna una rappresentazione in stringa del nodo di dichiarazione di variabile.
     *
     * @return Una stringa nel formato "&lt;DECL: type id init&gt;".
     */
    @Override
    public String toString() {
        return "<DECL: " + type + " " + id + " " + init + ">";
    }

    /**
     * Ritorna una rappresentazione in stringa concisa del nodo di dichiarazione di variabile
     *
     * @return Una stringa nel formato "type id = init" o "type id = null" se l'inizializzazione è assente.
     */
    @Override
    public String toStringConcise() {
        if (init != null) return type + " " + id.toStringConcise() + " = " + init.toStringConcise();
        else return type + " " + id.toStringConcise() + " = null";
    }

    /**
     * Accetta un visitatore per il pattern visitor
     *
     * @param visitor Il visitatore da accettare.
     */
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Ritorna l'identificatore della variabile dichiarata
     *
     * @return L'identificatore della variabile
     */
    public NodeId getNodeId() {
        return id;
    }

    /**
     * Ritorna il nodo dell'espressione d'inizializzazione della variabile
     *
     * @return L'espressione d'inizializzazione (puo' essere null)
     */
    public NodeExpr getInit() {
        return init;
    }

    /**
     * Ritorna il tipo della variabile dichiarata.
     *
     * @return Il tipo della variabile.
     */
    public LangType getType() {
        return type;
    }
}
