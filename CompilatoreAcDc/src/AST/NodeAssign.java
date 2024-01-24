package AST;

import visitor.IVisitor;

/**
 * Rappresenta un nodo nell'Albero della Sintassi Astratta (AST) generato dall'analisi del codice AC.
 * Questo nodo rappresenta un'istruzione di assegnazione.
 */
public class NodeAssign extends NodeStm {
    /**
     * Il nodo dell'identificatore sul lato sinistro dell'assegnazione.
     */
    private final NodeId id;

    /**
     * Il nodo dell'espressione sul lato destro dell'assegnazione.
     */
    private final NodeExpr expr;

    /**
     * Costruisce un nuovo NodeAssign con i nodi identificatore ed espressione
     *
     * @param id   Il nodo dell'identificatore.
     * @param expr Il nodo dell'espressione.
     */
    public NodeAssign(NodeId id, NodeExpr expr) {
        this.id = id;
        this.expr = expr;
    }

    /**
     * Restituisce una rappresentazione in stringa del nodo di assegnazione
     *
     * @return Una stringa nel formato &lt;ASSIGN: id expr&gt;
     */
    @Override
    public String toString() {
        return "<ASSIGN: " + id + " " + expr + ">";
    }

    /**
     * Restituisce una rappresentazione in stringa concisa del nodo di assegnazione
     *
     * @return Una stringa nel formato "id = expr"
     */
    @Override
    public String toStringConcise() {
        return id.toStringConcise() + " = " + expr.toStringConcise();
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
     * Ritorna il nodo dell'identificatore nell'assegnazione.
     *
     * @return Il nodo dell'identificatore.
     */
    public NodeId getId() {
        return id;
    }

    /**
     * Ottiene il nodo dell'espressione nell'assegnazione.
     *
     * @return Il nodo dell'espressione.
     */
    public NodeExpr getExpr() {
        return expr;
    }
}
