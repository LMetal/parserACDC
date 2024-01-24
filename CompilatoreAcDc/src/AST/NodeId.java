package AST;

import visitor.IVisitor;

/**
 * Rappresenta un nodo dell'AST che rappresenta un identificatore.
 */
public class NodeId extends NodeAST {
    /**
     * Il nome dell'identificatore.
     */
    private final String name;

    /**
     * Costruisce un nuovo nodo NodeId con il nome dell'identificatore fornito.
     *
     * @param id Il nome dell'identificatore
     */
    public NodeId(String id) {
        this.name = id;
    }

    /**
     * Ritorna una rappresentazione in stringa del nodo dell'identificatore.
     *
     * @return Una stringa nel formato "&lt;ID: name&gt;"
     */
    @Override
    public String toString() {
        return "<ID: " + name + ">";
    }

    /**
     * Ritorna una rappresentazione in stringa del nodo dell'identificatore.
     *
     * @return Il nome dell'identificatore.
     */
    @Override
    public String toStringConcise() {
        return name;
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
     * Ritorna il nome dell'identificatore.
     *
     * @return Il nome dell'identificatore.
     */
    public String getName() {
        return name;
    }
}
