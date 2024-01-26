package AST;

import visitor.IVisitor;

/**
 * Rappresenta un nodo nell'AST che rappresenta un'operazione binaria.
 */
public class NodeBinOp extends NodeExpr {
    /**
     * Parte sinistra dell'operazione
     */
    private NodeExpr left;

    /**
     * Parte destra dell'operazione
     */
    private NodeExpr right;

    /**
     * Operatore dell'operazione
     */
    private final LangOper op;

    /**
     * Costruisce un nuovo nodo NodeBinOp con l'espressione sinistra, l'operatore e l'espressione destra forniti
     *
     * @param l  Il nodo dell'espressione sinistra
     * @param op L'operatore
     * @param r  Il nodo dell'espressione destra
     */
    public NodeBinOp(NodeExpr l, LangOper op, NodeExpr r) {
        this.left = l;
        this.op = op;
        this.right = r;
    }

    /**
     * Restituisce una rappresentazione in stringa del nodo di operazione binaria
     *
     * @return Una stringa nel formato &lt;BINOP: left op right&gt;
     */
    @Override
    public String toString() {
        return "<BINOP: " + left + " " + op + " " + right + ">";
    }

    /**
     * Restituisce una rappresentazione in stringa concisa del nodo di operazione binaria.
     *
     * @return Una stringa nel formato "(left op right)".
     */
    @Override
    public String toStringConcise() {
        return "(" + left.toStringConcise() + " " + op.toString() + " " + right.toStringConcise() + ")";
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
     * Ritorna il nodo della parte sinistra dell'operazione
     *
     * @return la parte sinistra dell'operazione
     */
    public NodeExpr getLeft() {
        return left;
    }

    /**
     * Ritorna il nodo della parte destra dell'operazione
     *
     * @return la parte destra dell'operazione
     */
    public NodeExpr getRight() {
        return right;
    }

    /**
     * Ritorna l'operatore binario.
     *
     * @return L'operatore binario
     */
    public LangOper getOp() {
        return op;
    }

    /**
     * Imposta il nodo dell'espressione sinistra (utilizzato nel casting)
     *
     * @param nodeConvert Il nuovo nodo dell'espressione sinistra.
     */
    public void setLeft(NodeConvert nodeConvert) {
        this.left = nodeConvert;
    }

    /**
     * Imposta il nodo dell'espressione destra (utilizzato nel casting)
     *
     * @param nodeConvert Il nuovo nodo dell'espressione destra.
     */
    public void setRight(NodeConvert nodeConvert) {
        this.right = nodeConvert;
    }
}
