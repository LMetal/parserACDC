package AST;

import visitor.IVisitor;

/**
 * Rappresenta un nodo nell'AST che corrisponde a una variabile.
 */
public class NodeDeref extends NodeExpr {
    /**
     * Identificatore della variabile
     */
    private final NodeId id;

    /**
     * Costruisce un nuovo nodo NodeDeref con l'identificatore della variabile fornito
     *
     * @param id L'identificatore della variabile
     */
    public NodeDeref(NodeId id) {
        this.id = id;
    }

    /**
     * Ritorna una rappresentazione in stringa del nodo variabile.
     *
     * @return Una stringa corrispondente all'identificatore della variabile
     */
    @Override
    public String toString() {
        return id.toString();
    }

    /**
     * Ritorna una rappresentazione in stringa concisa del nodo di variabile.
     *
     * @return Una stringa corrispondente all'identificatore della variabile
     */
    @Override
    public String toStringConcise() {
        return id.toStringConcise();
    }

    /**
     * Accetta un visitatore
     *
     * @param visitor Il visitatore da accettare
     */
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Ritorna l'identificatore della variabil
     *
     * @return L'identificatore della variabile
     */
    public NodeId getId() {
        return id;
    }
}
