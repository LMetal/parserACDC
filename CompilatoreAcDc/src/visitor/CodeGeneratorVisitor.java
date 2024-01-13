package visitor;

import AST.*;

public class CodeGeneratorVisitor implements IVisitor{
    private String codiceDc;

    @Override
    public void visit(NodeProgram node) {

    }

    @Override
    public void visit(NodeId node) {

    }

    @Override
    public void visit(NodeDecl node) {

    }

    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        String leftCodice = codiceDc;
        node.getRight().accept(this);
        String rightCodice = codiceDc;

        /*
        if()

        codiceDc = leftCodice + rightCodice + ...
         */
    }

    @Override
    public void visit(NodeConvert nodeConvert) {

    }

    @Override
    public void visit(NodeConst nodeConst) {

    }

    @Override
    public void visit(NodeDeref nodeDeref) {

    }

    @Override
    public void visit(NodePrint nodePrint) {

    }

    @Override
    public void visit(NodeAssign nodeAssign) {

    }
}
