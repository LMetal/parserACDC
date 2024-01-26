package AST;

import visitor.IVisitor;

/**
 * Rappresenta un nodo dell'AST che rappresenta un casting
 */
public class NodeConvert extends NodeExpr {
    /**
     * Il nodo di espressione da convertire
     */
    private final NodeExpr node;

    /**
     * Costruisce un nuovo nodo NodeConvert con il nodo di espressione da convertire fornito
     *
     * @param node Il nodo di espressione da convertire
     */
    public NodeConvert(NodeExpr node) {
        this.node = node;
    }

    /**
     * Ritorna una rappresentazione in stringa del nodo di conversione di tipo
     *
     * @return Una stringa nel formato "&lt;CONVERT: node&gt;".
     */
    @Override
    public String toString() {
        return "<CONVERT: " + node.toString() + ">";
    }

    /**
     * Ritorna una rappresentazione in stringa concisa del nodo di conversione di tipo.
     *
     * @return Una stringa nel formato "(float)(node)".
     */
    @Override
    public String toStringConcise() {
        return "(float)(" + node.toStringConcise() + ")";
    }

    /**
     * Accetta un visitatore per il pattern visitor
     *
     * @param visitor Il visitatore da accettare
     */
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Ritorna il nodo di espressione convertito
     *
     * @return Il nodo di espressione convertito
     */
    public NodeExpr getExpr() {
        return node;
    }
}
