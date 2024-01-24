package AST;

import visitor.IVisitor;

/**
 * Rappresenta un nodo nell'AST che corrisponde a un'istruzione di stampa
 */
public class NodePrint extends NodeStm {
    /**
     * Identificatore della variabile da stampare
     */
    private final NodeId id;

    /**
     * Costruisce un nuovo nodo NodePrint con l'identificatore della variabile da stampare fornito
     *
     * @param node L'identificatore della variabile da stampare
     */
    public NodePrint(NodeId node) {
        this.id = node;
    }

    /**
     * Ritorna una rappresentazione in stringa del nodo di istruzione di stampa.
     *
     * @return Una stringa nel formato "&lt;PRINT: id&gt;".
     */
    @Override
    public String toString() {
        return "<PRINT: " + id.toString() + ">";
    }

    /**
     * Ritorna una rappresentazione in stringa concisa del nodo di istruzione di stampa.
     *
     * @return Una stringa nel formato "PRINT id".
     */
    @Override
    public String toStringConcise() {
        return "PRINT" + id.toStringConcise();
    }

    /**
     * Accetta un visitatore
     *
     * @param visitor Il visitatore da accettare.
     */
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Ritorna l'identificatore della variabile da stampare
     *
     * @return L'identificatore della variabile da stampare.
     */
    public NodeId getId() {
        return this.id;
    }
}

