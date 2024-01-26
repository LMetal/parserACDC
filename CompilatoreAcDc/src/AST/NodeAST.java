package AST;

import visitor.IVisitor;

/**
 * Classe astratta che ogni nodo nell AST estende.
 * Alcuni metodi comuni a tutti i nodi concreti sono definiti in modo astratto
 */
public abstract class NodeAST {
    /**
     * Override di toString
     *
     * @return stringa che descrive il nodo
     */
    @Override
    public abstract String toString();

    /**
     * Metodo che permette di ritornare una stringa breve che descrive il nodo omettendo alcune informazioni.
     * Utile nel testing
     *
     * @return stringa breve rappresentante il nodo
     */
    public abstract String toStringConcise();

    /**
     * Metodo accettante per pattern visitor
     *
     * @param visitor oggetto che implementa più funzioni in overloading
     */
    public abstract void accept(IVisitor visitor);
}
