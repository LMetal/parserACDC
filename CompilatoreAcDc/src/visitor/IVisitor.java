package visitor;

import AST.*;

/**
 * Interfaccia per un visitatore che implementa le operazioni sull'AST
 */
public interface IVisitor {
    /**
     * Metodo per visitare un nodo Program nell'AST.
     *
     * @param node Il nodo Program da visitare.
     */
    void visit(NodeProgram node);

    /**
     * Metodo per visitare un nodo Id nell'AST.
     *
     * @param node Il nodo Id da visitare.
     */
    void visit(NodeId node);

    /**
     * Metodo per visitare un nodo Declaration nell'AST.
     *
     * @param node Il nodo Declaration da visitare.
     */
    void visit(NodeDecl node);

    /**
     * Metodo per visitare un nodo Binary Operation nell'AST.
     *
     * @param node Il nodo Binary Operation da visitare.
     */
    void visit(NodeBinOp node);

    /**
     * Metodo per visitare un nodo Convert nell'AST.
     *
     * @param nodeConvert Il nodo Convert da visitare.
     */
    void visit(NodeConvert nodeConvert);

    /**
     * Metodo per visitare un nodo Constant nell'AST.
     *
     * @param nodeConst Il nodo Constant da visitare.
     */
    void visit(NodeConst nodeConst);

    /**
     * Metodo per visitare un nodo Dereference nell'AST.
     *
     * @param nodeDeref Il nodo Dereference da visitare.
     */
    void visit(NodeDeref nodeDeref);

    /**
     * Metodo per visitare un nodo Print nell'AST.
     *
     * @param nodePrint Il nodo Print da visitare.
     */
    void visit(NodePrint nodePrint);

    /**
     * Metodo per visitare un nodo Assign nell'AST.
     *
     * @param nodeAssign Il nodo Assign da visitare.
     */
    void visit(NodeAssign nodeAssign);
}

