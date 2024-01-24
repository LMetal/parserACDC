package AST;

import visitor.IVisitor;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Rappresenta il nodo root dell'AST
 */
public class NodeProgram extends NodeAST {
    /**
     * Lista di dichiarazioni e istruzioni nel programma
     */
    private final ArrayList<NodeDecSt> decSts;

    /**
     * Costruisce un nuovo nodo NodeProgram con la lista di dichiarazioni e istruzioni fornita
     *
     * @param nodes La lista di dichiarazioni e istruzioni nel programma
     */
    public NodeProgram(ArrayList<NodeDecSt> nodes) {
        this.decSts = nodes;
    }

    /**
     * Ritorna la lista di dichiarazioni e istruzioni nel programma.
     *
     * @return La lista di dichiarazioni e istruzioni.
     */
    public ArrayList<NodeDecSt> getDecStm() {
        return decSts;
    }

    /**
     * Ritorna una rappresentazione in stringa del nodo del programma.
     *
     * @return Una stringa nel formato "PROGRAM : [decSt1, decSt2, ...]".
     */
    @Override
    public String toString() {
        return "PROGRAM : " + decSts.toString();
    }

    /**
     * Ritorna una rappresentazione in stringa concisa del nodo del programma.
     *
     * @return Una stringa che elenca in modo conciso le dichiarazioni e le istruzioni separate da ";".
     */
    @Override
    public String toStringConcise() {
        return decSts.stream()
                .map(NodeDecSt::toStringConcise)
                .collect(Collectors.joining("; "));
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
}
