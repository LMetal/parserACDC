package AST;

import visitor.IVisitor;

/**
 * Rappresenta un nodo nell'Albero della Sintassi Astratta (AST) che rappresenta una costante.
 */
public class NodeConst extends NodeExpr {
    private final String value;
    private final LangType type;

    /**
     * Costruisce un nuovo nodo NodeConst con il valore e il tipo forniti
     *
     * @param value Il valore della costante.
     * @param type  Il tipo della costante.
     */
    public NodeConst(String value, LangType type) {
        this.type = type;
        this.value = value;
    }

    /**
     * Restituisce una rappresentazione in stringa del nodo di costante.
     *
     * @return Una stringa nel formato &lt;CONST: type value&gt;
     */
    @Override
    public String toString() {
        return "<CONST: " + type + " " + value + ">";
    }

    /**
     * Restituisce una rappresentazione in stringa concisa del nodo di costante.
     *
     * @return Il valore della costante.
     */
    @Override
    public String toStringConcise() {
        return value;
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
     * Ritorna il tipo della costante.
     *
     * @return Il tipo della costante.
     */
    public LangType getType() {
        return type;
    }

    /**
     * Ritorna il valore della costante.
     *
     * @return Il valore della costante.
     */
    public String getValue() {
        return value;
    }
}
